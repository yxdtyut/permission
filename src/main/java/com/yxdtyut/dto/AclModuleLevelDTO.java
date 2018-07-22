package com.yxdtyut.dto;

import com.google.common.collect.Lists;
import com.yxdtyut.model.SysAclModule;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午12:27 2018/7/21
 */
@Data
public class AclModuleLevelDTO extends SysAclModule {
    private List<AclModuleLevelDTO> aclModuleList = Lists.newArrayList();

    public static AclModuleLevelDTO adapt(SysAclModule sysAclModule) {
        AclModuleLevelDTO aclModuleLevelDTO = new AclModuleLevelDTO();
        BeanUtils.copyProperties(sysAclModule,aclModuleLevelDTO);
        return aclModuleLevelDTO;
    }
}
