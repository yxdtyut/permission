package com.yxdtyut.service.impl;

import com.yxdtyut.beans.RequestHolder;
import com.yxdtyut.common.CodeMsg;
import com.yxdtyut.dao.SysDeptMapper;
import com.yxdtyut.exception.PermissionException;
import com.yxdtyut.model.SysDept;
import com.yxdtyut.param.SysDeptVo;
import com.yxdtyut.service.SysDeptService;
import com.yxdtyut.utils.BeanValidator;
import com.yxdtyut.utils.IpUtil;
import com.yxdtyut.utils.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.dgc.Lease;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Author : yangxudong
 * @Description :   部门service实现类
 * @Date : 下午4:08 2018/7/18
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public int save(SysDeptVo sysDeptVo){
        BeanValidator.check(sysDeptVo);
        if (checkExist(sysDeptVo.getParentId(),sysDeptVo.getName(),sysDeptVo.getId())) {
            throw new PermissionException(CodeMsg.DEPT_NAME_REPETITION);
        }
        SysDept sysDept = SysDept.builder().name(sysDeptVo.getName()).parentId(sysDeptVo.getParentId())
                .seq(sysDeptVo.getSeq()).remark(sysDeptVo.getRemark()).build();
        sysDept.setLevel(LevelUtil.calculateLevel(getParentLevel(sysDept.getParentId()),sysDept.getParentId()));
        sysDept.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysDept.setOperateTime(new Date());
        return sysDeptMapper.insertSelective(sysDept);
    }

    @Override
    public void update(SysDeptVo sysDeptVo) {
        BeanValidator.check(sysDeptVo);
        SysDept beforeDept = sysDeptMapper.selectByPrimaryKey(sysDeptVo.getId());
        if (beforeDept == null) {
            throw new PermissionException(CodeMsg.DEPT_NOT_EXIST);
        }
        if (checkExist(sysDeptVo.getParentId(),sysDeptVo.getName(),sysDeptVo.getId())) {
            throw new PermissionException(CodeMsg.DEPT_NAME_REPETITION);
        }
        SysDept afterDept = SysDept.builder().id(sysDeptVo.getId()).name(sysDeptVo.getName()).parentId(sysDeptVo.getParentId())
                .seq(sysDeptVo.getSeq()).remark(sysDeptVo.getRemark()).build();
        afterDept.setLevel(LevelUtil.calculateLevel(getParentLevel(afterDept.getParentId()),afterDept.getParentId()));
        afterDept.setOperator(RequestHolder.getCurrentUser().getUsername());
        afterDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        afterDept.setOperateTime(new Date());
        updateDeptAndChildDept(beforeDept,afterDept);
    }

    /**
     * @Author : yangxudong
     * @Description : 修改部门和部门子集
     * @param beforeDept
     * @param afterDept
     * @Date : 上午10:23 2018/7/19
     */
    @Transactional
    void updateDeptAndChildDept(SysDept beforeDept, SysDept afterDept) {
        if (!beforeDept.getLevel().equals(afterDept.getLevel())) {
            List<SysDept> sysDeptList = sysDeptMapper.findSysDeptListByLevel(beforeDept.getLevel());
            if (CollectionUtils.isNotEmpty(sysDeptList)) {
                sysDeptList.stream().forEach((x) -> {
                    String newLevel = afterDept.getLevel() + x.getLevel().substring(beforeDept.getLevel().length());
                    x.setLevel(newLevel);
                });
                sysDeptMapper.batchUpdateDept(sysDeptList);
            }
        }
        sysDeptMapper.updateByPrimaryKeySelective(afterDept);
    }

    /**
     * @Author : yangxudong
     * @Description : 判断同一层级下部门名称是否重复
     * @param parentId  上级部门id
     * @param name 部门名称
     * @param id   部门id
     * @Date : 下午4:23 2018/7/18
     */
    public boolean checkExist(Integer parentId, String name, Integer id) {
        return sysDeptMapper.getCountByParentIdAndName(parentId,name,id) > 0;
    }

    /**
     * @Author : yangxudong
     * @Description :  获取上级部门的级别
     * @param parentId
     * @Date : 下午4:20 2018/7/18
     */
    public String getParentLevel(Integer parentId) {
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(parentId);
        if (sysDept == null) {
            return null;
        }
        return sysDept.getLevel();
    }
}
