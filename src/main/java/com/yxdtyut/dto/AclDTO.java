package com.yxdtyut.dto;

import com.yxdtyut.model.SysAcl;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午6:27 2018/7/22
 */
@Data
public class AclDTO extends SysAcl {
    private boolean checked = false;
    private boolean hasAcl = false;

    public static AclDTO adapt(SysAcl sysAcl) {
        AclDTO aclDTO = new AclDTO();
        BeanUtils.copyProperties(sysAcl,aclDTO);
        return aclDTO;
    }
}
