package com.yxdtyut.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yxdtyut.beans.RequestHolder;
import com.yxdtyut.dao.SysRoleAclMapper;
import com.yxdtyut.model.SysRoleAcl;
import com.yxdtyut.service.SysRoleAclService;
import com.yxdtyut.utils.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 上午10:10 2018/7/23
 */
@Service
public class SysRoleAclServiceImpl implements SysRoleAclService {

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Override
    public void changeRoleAcls(int roleId, List<Integer> aclList) {
        List<Integer> aclIdList = sysRoleAclMapper.selectAclIdsByRoleIds(Lists.newArrayList(roleId));
        if (aclIdList.size() == aclList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(aclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclList);
            originAclIdSet.remove(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }
        updateAclsByRoleIdAndAclList(roleId,aclList);
    }

    @Transactional
    void updateAclsByRoleIdAndAclList(int roleId, List<Integer> aclList) {
        sysRoleAclMapper.deleteByRoleId(roleId);
        List<SysRoleAcl> sysRoleAcls = Lists.newArrayList();
        aclList.stream().forEach((x) -> {
            SysRoleAcl sysRoleAcl = SysRoleAcl.builder().aclId(x).roleId(roleId)
                    .operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                    .operateTime(new Date()).build();
            sysRoleAcls.add(sysRoleAcl);
        });
        sysRoleAclMapper.batchInsert(sysRoleAcls);
    }
}
