package com.yxdtyut.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yxdtyut.beans.RequestHolder;
import com.yxdtyut.dao.SysRoleMapper;
import com.yxdtyut.dao.SysRoleUserMapper;
import com.yxdtyut.dao.SysUserMapper;
import com.yxdtyut.model.SysRole;
import com.yxdtyut.model.SysRoleUser;
import com.yxdtyut.model.SysUser;
import com.yxdtyut.service.SysRoleUserService;
import com.yxdtyut.utils.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 上午10:10 2018/7/23
 */
@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysUser> getSysUserListByRoleId(int roleId) {
        List<Integer> userIds = sysRoleUserMapper.findUserIdsByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIds)) {
            return Lists.newArrayList();
        }
        List<SysUser> sysUsers = sysUserMapper.findSysUserListByUserIds(userIds);
        return sysUsers;
    }

    @Override
    public void changeRoleUser(int roleId, List<Integer> userList) {
        List<Integer> originUserIds = sysRoleUserMapper.findUserIdsByRoleId(roleId);
        if (userList.size() == originUserIds.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIds);
            Set<Integer> userIdSet = Sets.newHashSet(userList);
            originUserIdSet.remove(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet)) {
                return;
            }
        }
        updateRoleUserByRoleIdAndUserList(roleId,userList);
    }

    @Override
    public List<SysRole> findSysRoleListByUserId(int userId) {
        List<Integer> roleIds = sysRoleUserMapper.selectRoleIdsByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }
        List<SysRole> sysRoles = sysRoleMapper.selectSysRoleListByRoleIds(roleIds);
        return sysRoles;
    }

    @Override
    public List<SysUser> findUserListByRoleList(List<SysRole> sysRoles) {
        if (CollectionUtils.isEmpty(sysRoles)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIds = sysRoles.stream().map(sysRole -> sysRole.getId()).collect(Collectors.toList());
        List<Integer> userIds = sysRoleUserMapper.findUserIdsByRoleIdList(roleIds);
        return sysUserMapper.findSysUserListByUserIds(userIds);
    }

    @Transactional
    void updateRoleUserByRoleIdAndUserList(int roleId, List<Integer> userList) {
        sysRoleUserMapper.deleteByRoleId(roleId);
        List<SysRoleUser> sysRoleUsers = Lists.newArrayList();
        userList.stream().forEach((x) -> {
            SysRoleUser sysRoleUser = SysRoleUser.builder().roleId(roleId).userId(x)
                    .operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                    .operateTime(new Date()).build();
            sysRoleUsers.add(sysRoleUser);
        });
        sysRoleUserMapper.batchInsert(sysRoleUsers);
    }
}
