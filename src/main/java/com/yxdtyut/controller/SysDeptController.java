package com.yxdtyut.controller;

import com.yxdtyut.common.Result;
import com.yxdtyut.dto.DeptLevelDTO;
import com.yxdtyut.param.SysDeptVo;
import com.yxdtyut.service.SysDeptService;
import com.yxdtyut.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author : yangxudong
 * @Description :   部门controller
 * @Date : 下午4:03 2018/7/18
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysTreeService sysTreeService;

    @RequestMapping("/dept.page")
    public ModelAndView page() {
        return new ModelAndView("dept");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public Result saveDept(SysDeptVo sysDeptVo) {
        sysDeptService.save(sysDeptVo);
        return Result.success(sysDeptVo);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public Result updateDept(SysDeptVo sysDeptVo) {
        sysDeptService.update(sysDeptVo);
        return Result.success(sysDeptVo);
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public Result treeDept() {
        List<DeptLevelDTO> deptLevelDTOS = sysTreeService.deptTree();
        return Result.success(deptLevelDTOS);
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public Result deleteDept(@RequestParam int id) {
        log.info("删除部门，id:{}",id);
        sysDeptService.deleteDept(id);
        log.info("删除部门完毕，id:{}",id);
        return Result.success(id);
    }
}
