package com.lingfenglong.auth;

import com.lingfenglong.auth.mapper.SysRoleMapper;
import com.lingfenglong.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestMpDemo1 {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    // 查询所有
    @Test
    void getAll() {
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        sysRoles.forEach(System.out::println);
    }

    // 插入对象
    @Test
    void add() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("ZhangSanSan");
        sysRole.setRoleCode("role");
        sysRole.setDescription("角色管理员");

        int result = sysRoleMapper.insert(sysRole);
        System.out.println("result = " + result);
        System.out.println("result = " + sysRole.getId());
    }

    @Test
    void update() {
        SysRole sysRole = sysRoleMapper.selectById(10);
        sysRole.setRoleName("lisi");
        int result = sysRoleMapper.updateById(sysRole);
        System.out.println("result = " + result);
    }

    @Test
    void deleteById() {
        int result = sysRoleMapper.deleteById(10);
        System.out.println("result = " + result);
    }

    @Test
    void deleteBatchIds() {
        int result = sysRoleMapper.deleteBatchIds(List.of(1, 2));
        System.out.println("result = " + result);
    }
}
