package com.yxdtyut.service;

import com.yxdtyut.param.SysDeptVo;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午4:08 2018/7/18
 */

public interface SysDeptService {
    public int save(SysDeptVo sysDeptVo);

    void update(SysDeptVo sysDeptVo);
}
