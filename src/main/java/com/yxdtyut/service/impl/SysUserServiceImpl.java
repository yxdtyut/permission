package com.yxdtyut.service.impl;

import com.yxdtyut.beans.Mail;
import com.yxdtyut.beans.PageQuery;
import com.yxdtyut.beans.PageResult;
import com.yxdtyut.beans.RequestHolder;
import com.yxdtyut.common.CodeMsg;
import com.yxdtyut.dao.SysUserMapper;
import com.yxdtyut.exception.PermissionException;
import com.yxdtyut.model.SysUser;
import com.yxdtyut.param.SysUserVo;
import com.yxdtyut.service.SysUserService;
import com.yxdtyut.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author : yangxudong
 * @Description :   用户service
 * @Date : 下午3:21 2018/7/19
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public int save(SysUserVo sysUserVo) {
        BeanValidator.check(sysUserVo);
        if (checkEmailExist(sysUserVo.getMail(),sysUserVo.getId())) {
            throw new PermissionException(CodeMsg.USER_EMAIL_EXIST);
        }
        if (checkTelephoneExist(sysUserVo.getTelephone(),sysUserVo.getId())) {
            throw new PermissionException(CodeMsg.USER_TELEPHONE_EXIST);
        }
        String password = PasswordUtil.randomPassword();
        log.info("发送邮件开始:{}" ,sysUserVo.getMail());
        Set set = new HashSet();
        set.add(sysUserVo.getMail());
        MailUtil.send(Mail.builder().message(password).subject("用户登陆密码").receivers(set).build());
        log.info("发送邮件结束:{}" ,sysUserVo.getMail());
        password = MD5Util.encrypt(password);
        SysUser user = SysUser.builder().username(sysUserVo.getUsername()).telephone(sysUserVo.getTelephone())
                .mail(sysUserVo.getMail()).deptId(sysUserVo.getDeptId()).password(password).status(sysUserVo.getStatus())
                .remark(sysUserVo.getRemark()).build();
        user.setOperator(RequestHolder.getCurrentUser().getUsername());
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());
        return sysUserMapper.insertSelective(user);
    }

    @Override
    public int update(SysUserVo sysUserVo) {
        BeanValidator.check(sysUserVo);
        if (checkEmailExist(sysUserVo.getMail(),sysUserVo.getId())) {
            throw new PermissionException(CodeMsg.USER_EMAIL_EXIST);
        }
        if (checkTelephoneExist(sysUserVo.getTelephone(),sysUserVo.getId())) {
            throw new PermissionException(CodeMsg.USER_TELEPHONE_EXIST);
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(sysUserVo.getId());
        if (before == null) {
            throw new PermissionException(CodeMsg.USER_NOT_EXIST);
        }
        SysUser after = SysUser.builder().id(sysUserVo.getId()).username(sysUserVo.getUsername()).telephone(sysUserVo.getTelephone())
                .mail(sysUserVo.getMail()).deptId(sysUserVo.getDeptId()).status(sysUserVo.getStatus())
                .remark(sysUserVo.getRemark()).build();
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        return sysUserMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public SysUser findSysUserByKeyWord(String keyword) {
        return sysUserMapper.findSysUserByKeyWord(keyword);
    }

    @Override
    public PageResult<SysUser> getPageResultByDeptId(int deptId, PageQuery page) {
        BeanValidator.check(page);
        int count = sysUserMapper.findCountByDeptId(deptId);
        if (count > 0) {
            List<SysUser> userList = sysUserMapper.findSysUserListByDeptId(deptId, page);
            return PageResult.<SysUser>builder().total(count).data(userList).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    private boolean checkEmailExist(String mail, Integer id) {
        return sysUserMapper.findCountByEmail(mail,id) > 0;
    }

    private boolean checkTelephoneExist(String telephone, Integer id) {
        return sysUserMapper.findCountByTelephone(telephone,id) > 0;
    }
}
