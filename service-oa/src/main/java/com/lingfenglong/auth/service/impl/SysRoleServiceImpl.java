package com.lingfenglong.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.auth.mapper.SysRoleMapper;
import com.lingfenglong.auth.service.SysRoleService;
import com.lingfenglong.auth.service.SysUserRoleService;
import com.lingfenglong.common.collections.ObjectHashMap;
import com.lingfenglong.model.system.SysRole;
import com.lingfenglong.model.system.SysUserRole;
import com.lingfenglong.vo.system.AssignRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    private final SysUserRoleService sysUserRoleService;

    @Autowired
    public SysRoleServiceImpl(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }

    @Transactional
    @Override
    public ObjectHashMap findRoleDataByUserId(Long userId) {
        // 查询所有的角色数据
        List<SysRole> allRoleList = baseMapper.selectList(null);

        // 查询用户已有的角色数据
        List<SysUserRole> existUserRoleList = sysUserRoleService.list(
                new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, userId)
        );

        // 转化为id列表
        List<Long> existUserRoleIdList = existUserRoleList.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());

        List<SysRole> assignRoleList = allRoleList.stream()
                .filter(sysRole -> existUserRoleIdList.contains(sysRole.getId()))
                .collect(Collectors.toList());

        ObjectHashMap res = new ObjectHashMap();
        res.put("allRoleList", allRoleList);
        res.put("assignRoleList", assignRoleList);
        log.info("res = " + res);
        return res;
    }

    @Transactional
    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        Long userId = assignRoleVo.getUserId();

        // 删除所有id的角色
        sysUserRoleService.remove(
                new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, userId)
        );

        // 重新分配
        assignRoleVo.getRoleIdList()
                .forEach(roleId -> sysUserRoleService.save(new SysUserRole(roleId, userId)));
    }
}
