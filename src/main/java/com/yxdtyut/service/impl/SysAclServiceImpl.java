package com.yxdtyut.service.impl;

import com.yxdtyut.beans.PageQuery;
import com.yxdtyut.beans.PageResult;
import com.yxdtyut.beans.RequestHolder;
import com.yxdtyut.common.CodeMsg;
import com.yxdtyut.dao.SysAclMapper;
import com.yxdtyut.exception.PermissionException;
import com.yxdtyut.model.SysAcl;
import com.yxdtyut.param.SysAclVo;
import com.yxdtyut.service.SysAclService;
import com.yxdtyut.utils.BeanValidator;
import com.yxdtyut.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午6:20 2018/7/21
 */
@Service
public class SysAclServiceImpl implements SysAclService {

    @Autowired
    private SysAclMapper sysAclMapper;

    @Override
    public int saveAcl(SysAclVo sysAclVo) {
        BeanValidator.check(sysAclVo);
        if (checkExist(sysAclVo.getAclModuleId(),sysAclVo.getName(),sysAclVo.getId())) {
            throw new PermissionException(CodeMsg.ACL_NAME_REPETITION);
        }
        SysAcl sysAcl = SysAcl.builder().name(sysAclVo.getName()).aclModuleId(sysAclVo.getAclModuleId())
                .url(sysAclVo.getUrl()).type(sysAclVo.getType())
                .status(sysAclVo.getStatus()).seq(sysAclVo.getSeq())
                .remark(sysAclVo.getRemark()).build();
        sysAcl.setCode(generatorCode());
        sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAcl.setOperateTime(new Date());
        return sysAclMapper.insertSelective(sysAcl);
    }

    private String generatorCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()) + "_" + (int)(Math.random()*100);
    }

    private boolean checkExist(Integer aclModuleId, String name, Integer id) {
        return sysAclMapper.getCountByAclModuleIdAndName(aclModuleId,name,id) > 0;
    }

    @Override
    public void updateAcl(SysAclVo sysAclVo) {
        BeanValidator.check(sysAclVo);
        if (checkExist(sysAclVo.getAclModuleId(),sysAclVo.getName(),sysAclVo.getId())) {
            throw new PermissionException(CodeMsg.ACL_NAME_REPETITION);
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(sysAclVo.getId());
        if (before == null) {
            throw new PermissionException(CodeMsg.ACL_NOT_EXIST);
        }
        SysAcl after = SysAcl.builder().id(sysAclVo.getId()).name(sysAclVo.getName()).aclModuleId(sysAclVo.getAclModuleId())
                .url(sysAclVo.getUrl()).type(sysAclVo.getType())
                .status(sysAclVo.getStatus()).seq(sysAclVo.getSeq())
                .remark(sysAclVo.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysAclMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public PageResult pageAclByAclModuleId(int aclModuleId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysAclMapper.getCountByAclModuleId(aclModuleId);
        if (count > 0) {
            List<SysAcl> aclList = sysAclMapper.selectAclListByAclModuleId(aclModuleId, pageQuery);
            return PageResult.<SysAcl>builder().total(count).data(aclList).build();
        }
        return PageResult.<SysAcl>builder().build();
    }
}
