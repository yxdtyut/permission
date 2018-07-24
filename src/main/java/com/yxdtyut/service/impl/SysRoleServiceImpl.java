package com.yxdtyut.service.impl;

import com.google.common.collect.Lists;
import com.yxdtyut.beans.RequestHolder;
import com.yxdtyut.common.CodeMsg;
import com.yxdtyut.dao.SysRoleAclMapper;
import com.yxdtyut.dao.SysRoleMapper;
import com.yxdtyut.exception.PermissionException;
import com.yxdtyut.model.SysRole;
import com.yxdtyut.param.SysRoleVo;
import com.yxdtyut.service.SysRoleService;
import com.yxdtyut.utils.BeanValidator;
import com.yxdtyut.utils.IpUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午3:40 2018/7/22
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Override
    public int saveRole(SysRoleVo sysRoleVo) {
        BeanValidator.check(sysRoleVo);
        if (checkExist(sysRoleVo.getName(),sysRoleVo.getId())) {
            throw new PermissionException(CodeMsg.ROLE__REPETITION);
        }
        SysRole sysRole = SysRole.builder().name(sysRoleVo.getName()).type(sysRoleVo.getType())
                .status(sysRoleVo.getStatus()).remark(sysRoleVo.getRemark()).build();
        sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysRole.setOperateTime(new Date());
        return sysRoleMapper.insertSelective(sysRole);
    }

    private boolean checkExist(String name, Integer id) {
        return sysRoleMapper.getCountByName(name,id) > 0;
    }

    @Override
    public void updateRole(SysRoleVo sysRoleVo) {
        BeanValidator.check(sysRoleVo);
        if (checkExist(sysRoleVo.getName(),sysRoleVo.getId())) {
            throw new PermissionException(CodeMsg.ROLE__REPETITION);
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(sysRoleVo.getId());
        if (before == null) {
            throw new PermissionException(CodeMsg.ROLE_NOT_EXIST);
        }
        SysRole after = SysRole.builder().id(sysRoleVo.getId()).name(sysRoleVo.getName()).type(sysRoleVo.getType())
                .status(sysRoleVo.getStatus()).remark(sysRoleVo.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public List<SysRole> roleList() {
        return sysRoleMapper.roleList();
    }

    @Override
    public List<SysRole> findSysRoleListByAclId(int aclId) {
        List<Integer> roleIds = sysRoleAclMapper.findRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.selectSysRoleListByRoleIds(roleIds);
    }
}
