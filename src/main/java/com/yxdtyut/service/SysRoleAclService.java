package com.yxdtyut.service;

import java.util.List; /**
 * @Author : yangxudong
 * @Description :
 * @Date : 上午10:10 2018/7/23
 */

public interface SysRoleAclService {
    void changeRoleAcls(int roleId, List<Integer> aclList);
}
