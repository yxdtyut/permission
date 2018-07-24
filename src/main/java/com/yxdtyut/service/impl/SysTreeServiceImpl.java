package com.yxdtyut.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.yxdtyut.dao.SysAclMapper;
import com.yxdtyut.dao.SysAclModuleMapper;
import com.yxdtyut.dao.SysDeptMapper;
import com.yxdtyut.dto.AclDTO;
import com.yxdtyut.dto.AclModuleLevelDTO;
import com.yxdtyut.dto.DeptLevelDTO;
import com.yxdtyut.model.SysAcl;
import com.yxdtyut.model.SysAclModule;
import com.yxdtyut.model.SysDept;
import com.yxdtyut.param.SysAclModuleVo;
import com.yxdtyut.service.SysCoreService;
import com.yxdtyut.service.SysTreeService;
import com.yxdtyut.utils.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MultiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : yangxudong
 * @Description :
 * @Date : 下午6:04 2018/7/18
 */
@Service
public class SysTreeServiceImpl implements SysTreeService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SysCoreService sysCoreService;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Override
    public List<DeptLevelDTO> deptTree() {
        List<SysDept> sysDepts = sysDeptMapper.findSysDeptList();
        if (CollectionUtils.isEmpty(sysDepts)) {
            return Lists.newArrayList();
        }
        List<DeptLevelDTO> deptLevelDTOS = new ArrayList<>();
        sysDepts.stream().forEach((x) -> {
            DeptLevelDTO deptLevelDTO = DeptLevelDTO.adapt(x);
            deptLevelDTOS.add(deptLevelDTO);
        });
        return deptListToTree(deptLevelDTOS);
    }

    @Override
    public List<AclModuleLevelDTO> aclModuleTree() {
        List<SysAclModule> sysAclModules = sysAclModuleMapper.findSysAclModuleList();
        List<AclModuleLevelDTO> aclModuleLevelDTOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysAclModules)) {
            sysAclModules.stream().forEach((x) -> {
                aclModuleLevelDTOS.add(AclModuleLevelDTO.adapt(x));
            });
        }
        return sysModuleListToTree(aclModuleLevelDTOS);
    }

    @Override
    public List<AclModuleLevelDTO> roleTree(int roleId) {
        //获取当前用户所拥有的权限
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
        //获取该角色对应的权限
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);
        //获取所有的权限
        List<AclDTO> allAclDTOList = Lists.newArrayList();
        List<SysAcl> allAclList = sysAclMapper.findAll();
        Set<Integer> userAclIdSet = userAclList.stream().map((x) -> x.getId()).collect(Collectors.toSet());
        Set<Integer> roleAclIdSet = roleAclList.stream().map((x) -> x.getId()).collect(Collectors.toSet());
        allAclList.stream().forEach((x) -> {
            AclDTO aclDTO = AclDTO.adapt(x);
            if (userAclIdSet.contains(x.getId())) {
                aclDTO.setHasAcl(true);
            }
            if (roleAclIdSet.contains(x.getId())) {
                aclDTO.setChecked(true);
            }
            allAclDTOList.add(aclDTO);
        });
        return aclListToTree(allAclDTOList);
    }

    @Override
    public List<AclModuleLevelDTO> userTree(int userId) {
        List<SysAcl> userAclList = sysCoreService.getUserAclList(userId);
        List<AclDTO> allAclDTOList = Lists.newArrayList();
        userAclList.stream().forEach((x) -> {
            AclDTO aclDTO = AclDTO.adapt(x);
            aclDTO.setChecked(true);
            aclDTO.setHasAcl(true);
            allAclDTOList.add(aclDTO);
        });
        return aclListToTree(allAclDTOList);
    }

    private List<AclModuleLevelDTO> aclListToTree(List<AclDTO> allAclDTOList) {
        if (CollectionUtils.isEmpty(allAclDTOList)) {
            return Lists.newArrayList();
        }
        Multimap multimap = ArrayListMultimap.create();
        List<AclModuleLevelDTO> aclModuleLevelDTOS = aclModuleTree();
        allAclDTOList.stream().forEach((x) -> {
            if (1 == x.getStatus()) {
                multimap.put(x.getAclModuleId(), x);
            }
        });
        bindAclsWithOrder(aclModuleLevelDTOS, multimap);
        return aclModuleLevelDTOS;
    }

    private void bindAclsWithOrder(List<AclModuleLevelDTO> aclModuleLevelDTOS, Multimap multimap) {
        if (CollectionUtils.isEmpty(aclModuleLevelDTOS)) {
            return;
        }
        aclModuleLevelDTOS.stream().forEach((x) -> {
            List<AclDTO> aclDTOS = (List<AclDTO>) multimap.get(x.getId());
            if (CollectionUtils.isNotEmpty(aclDTOS)) {
                Collections.sort(aclDTOS,aclDTOComparator);
                x.setAclList(aclDTOS);
            }
            bindAclsWithOrder(x.getAclModuleList(),multimap);
        });
    }

    private List<AclModuleLevelDTO> sysModuleListToTree(List<AclModuleLevelDTO> aclModuleLevelDTOS) {
        Multimap multimap = ArrayListMultimap.create();
        List<AclModuleLevelDTO> rootList = new ArrayList<>();
        aclModuleLevelDTOS.stream().forEach((x) -> {
            multimap.put(x.getLevel(), x);
            if (LevelUtil.ROOT.equals(x.getLevel())) {
                rootList.add(x);
            }
        });
        Collections.sort(rootList,aclModuleLevelDTOComparator);
        transformSysAclModuleTree(rootList,LevelUtil.ROOT,multimap);
        return rootList;
    }

    private void transformSysAclModuleTree(List<AclModuleLevelDTO> rootList, String level, Multimap multimap) {
        rootList.stream().forEach((x) -> {
            String nextLevel = LevelUtil.calculateLevel(level, x.getId());
            List<AclModuleLevelDTO> aclModuleLevelDTOS = (List<AclModuleLevelDTO>) multimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(aclModuleLevelDTOS)) {
                Collections.sort(aclModuleLevelDTOS, aclModuleLevelDTOComparator);
                x.setAclModuleList(aclModuleLevelDTOS);
                transformSysAclModuleTree(aclModuleLevelDTOS,nextLevel,multimap);
            }
        });
    }

    private List<DeptLevelDTO> deptListToTree(List<DeptLevelDTO> deptLevelDTOS) {
        // level -> [dept1, dept2, ...] Map<String, List<Object>>
        Multimap<String, DeptLevelDTO> levelDTOMultimap = ArrayListMultimap.create();
        List<DeptLevelDTO> rootList = new ArrayList<>();
        deptLevelDTOS.stream().forEach((x) -> {
            levelDTOMultimap.put(x.getLevel(), x);
            if (LevelUtil.ROOT.equals(x.getLevel())) {
                rootList.add(x);
            }
        });
        // 按照seq从小到大排序
        Collections.sort(rootList,deptLevelDTOComparator);
        // 递归生成树
        transformDeptTree(rootList, LevelUtil.ROOT, levelDTOMultimap);
        return rootList;
    }

    // level:0, 0, all 0->0.1,0.2
    // level:0.1
    // level:0.2
    private void transformDeptTree(List<DeptLevelDTO> rootList, String level, Multimap<String, DeptLevelDTO> levelDTOMultimap) {
        rootList.stream().forEach((x) -> {
            //获取当前元素下一层
            String nextLevel = LevelUtil.calculateLevel(level, x.getId());
            //处理下一层
            List<DeptLevelDTO> nextDeptLevels = (List<DeptLevelDTO>) levelDTOMultimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(nextDeptLevels)) {
                //排序
                Collections.sort(nextDeptLevels,deptLevelDTOComparator);
                //设置下一层部门
                x.setDeptList(nextDeptLevels);
                //处理下一层
                transformDeptTree(nextDeptLevels,nextLevel,levelDTOMultimap);
            }
        });
    }


    public Comparator<DeptLevelDTO> deptLevelDTOComparator = new Comparator<DeptLevelDTO>() {
        @Override
        public int compare(DeptLevelDTO o1, DeptLevelDTO o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    public Comparator<AclModuleLevelDTO> aclModuleLevelDTOComparator = new Comparator<AclModuleLevelDTO>() {
        @Override
        public int compare(AclModuleLevelDTO o1, AclModuleLevelDTO o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };

    public Comparator<AclDTO> aclDTOComparator = new Comparator<AclDTO>() {
        @Override
        public int compare(AclDTO o1, AclDTO o2) {
            return o1.getSeq() - o2.getSeq();
        }
    };
}
