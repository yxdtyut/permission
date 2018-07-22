package com.yxdtyut.service.impl;

import com.yxdtyut.beans.RequestHolder;
import com.yxdtyut.common.CodeMsg;
import com.yxdtyut.dao.SysAclModuleMapper;
import com.yxdtyut.exception.PermissionException;
import com.yxdtyut.model.SysAclModule;
import com.yxdtyut.model.SysDept;
import com.yxdtyut.param.SysAclModuleVo;
import com.yxdtyut.service.SysAclModuleService;
import com.yxdtyut.utils.BeanValidator;
import com.yxdtyut.utils.IpUtil;
import com.yxdtyut.utils.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午3:49 2018/7/20
 */
@Service
public class SysAclModuleServiceImpl implements SysAclModuleService {

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Override
    public int save(SysAclModuleVo sysAclModuleVo) {
        BeanValidator.check(sysAclModuleVo);
        if (checkExist(sysAclModuleVo.getParentId(),sysAclModuleVo.getName(),sysAclModuleVo.getId())) {
            throw new PermissionException(CodeMsg.ACL_MODULE_NAME_REPETITION);
        }
        SysAclModule sysAclModule = SysAclModule.builder().name(sysAclModuleVo.getName()).parentId(sysAclModuleVo.getParentId())
                .status(sysAclModuleVo.getStatus()).seq(sysAclModuleVo.getSeq()).remark(sysAclModuleVo.getRemark()).build();
        sysAclModule.setLevel(LevelUtil.calculateLevel(getParentLevel(sysAclModule.getParentId()),sysAclModule.getParentId()));
        sysAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclModule.setOperateTime(new Date());
        return sysAclModuleMapper.insertSelective(sysAclModule);
    }

    @Override
    public void update(SysAclModuleVo sysAclModuleVo) {
        BeanValidator.check(sysAclModuleVo);
        if (checkExist(sysAclModuleVo.getParentId(),sysAclModuleVo.getName(),sysAclModuleVo.getId())) {
            throw new PermissionException(CodeMsg.ACL_MODULE_NAME_REPETITION);
        }
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(sysAclModuleVo.getId());
        if (before == null) {
            throw new PermissionException(CodeMsg.ACL_MODULE_NOT_EXIST);
        }
        SysAclModule after = SysAclModule.builder().id(sysAclModuleVo.getId()).name(sysAclModuleVo.getName()).parentId(sysAclModuleVo.getParentId())
                .status(sysAclModuleVo.getStatus()).seq(sysAclModuleVo.getSeq()).remark(sysAclModuleVo.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getParentLevel(after.getParentId()),after.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        updateAclModuleAndChildAclModule(before,after);
    }

    @Transactional
    void updateAclModuleAndChildAclModule(SysAclModule before, SysAclModule after) {
        if (!before.getLevel().equals(after.getLevel())) {
            List<SysAclModule> sysAclModuleList = sysAclModuleMapper.findSysAclModuleListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(sysAclModuleList)) {
                sysAclModuleList.stream().forEach((x) -> {
                    String newLevel = after.getLevel() + x.getLevel().substring(before.getLevel().length());
                    x.setLevel(newLevel);
                });
                sysAclModuleMapper.batchUpdateDept(sysAclModuleList);
            }
        }
        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }

    private boolean checkExist(Integer parentId, String name, Integer id) {
        return sysAclModuleMapper.getCountByParentIdAndName(parentId,name,id) > 0;
    }

    /**
     * @Author : yangxudong
     * @Description :  获取上级权限模块的级别
     * @param parentId
     * @Date : 下午4:20 2018/7/18
     */
    public String getParentLevel(Integer parentId) {
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(parentId);
        if (sysAclModule == null) {
            return null;
        }
        return sysAclModule.getLevel();
    }

}
