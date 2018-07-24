package com.yxdtyut.service.impl;

import com.google.common.collect.Lists;
import com.yxdtyut.beans.CacheKeyConstants;
import com.yxdtyut.beans.RequestHolder;
import com.yxdtyut.dao.SysAclMapper;
import com.yxdtyut.dao.SysRoleAclMapper;
import com.yxdtyut.dao.SysRoleUserMapper;
import com.yxdtyut.model.SysAcl;
import com.yxdtyut.model.SysUser;
import com.yxdtyut.service.SysCacheService;
import com.yxdtyut.service.SysCoreService;
import com.yxdtyut.utils.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午5:56 2018/7/22
 */
@Service
public class SysCoreServiceImpl implements SysCoreService {

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;


    @Autowired
    private SysCacheService sysCacheService;

    @Override
    public List<SysAcl> getUserAclList(int userId) {
        if (superUser()) {
            return sysAclMapper.findAll();
        }
        //通过userId查询该用户的roleid集合
        List<Integer> roleIds = sysRoleUserMapper.selectRoleIdsByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }
        List<Integer> sysAclIds = sysRoleAclMapper.selectAclIdsByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(sysAclIds)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.findSysAclListByIdList(sysAclIds);
    }

    @Override
    public List<SysAcl> getCurrentUserAclList() {
        Integer id = RequestHolder.getCurrentUser().getId();
        return getUserAclList(id);
    }

    @Override
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> sysAclIds = sysRoleAclMapper.selectAclIdsByRoleIds(Lists.newArrayList(roleId));
        if (CollectionUtils.isEmpty(sysAclIds)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.findSysAclListByIdList(sysAclIds);
    }

    @Override
    public boolean hasUrlAcl(String url) {
        if (superUser()) {
            return true;
        }
        List<SysAcl> sysAcls = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(sysAcls)) {
            return true;
        }
        List<SysAcl> currentUserAclList = getCurrentUserAclListFromCache();
        List<Integer> userAclIdList = currentUserAclList.stream().map(sysAcl -> sysAcl.getId()).collect(Collectors.toList());
        boolean hasValidAcl = false;
        // 规则：只要有一个权限点有权限，那么我们就认为有访问权限
        for(SysAcl sysAcl: sysAcls) {
            if (sysAcl == null || sysAcl.getStatus() != 1) {
                continue;
            }
            hasValidAcl = true;
            if (userAclIdList.contains(sysAcl.getId())) {
                return true;
            }
        }
        if (!hasValidAcl) {
            return true;
        }
        return false;
    }

    private boolean superUser() {
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getUsername().equals("admin")) {
            return true;
        }
        return false;
    }

    public List<SysAcl> getCurrentUserAclListFromCache() {
        int userId = RequestHolder.getCurrentUser().getId();
        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf(userId));
        if (StringUtils.isBlank(cacheValue)) {
            List<SysAcl> aclList = getCurrentUserAclList();
            if (CollectionUtils.isNotEmpty(aclList)) {
                sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600, CacheKeyConstants.USER_ACLS, String.valueOf(userId));
            }
            return aclList;
        }
        return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAcl>>() {
        });
    }
}
