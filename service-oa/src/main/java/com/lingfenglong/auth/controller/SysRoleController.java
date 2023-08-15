package com.lingfenglong.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingfenglong.auth.service.SysRoleService;
import com.lingfenglong.common.collections.ObjectHashMap;
import com.lingfenglong.common.collections.ParameterMap;
import com.lingfenglong.common.result.Result;
import com.lingfenglong.model.system.SysRole;
import com.lingfenglong.vo.system.AssginRoleVo;
import com.lingfenglong.vo.system.SysRoleQueryVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色管理", description = "角色管理的接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Operation(summary = "获取用户所有角色")
    @GetMapping("/toAssign/{userId}")
    public Result<ObjectHashMap> toAssign(@PathVariable Long userId) {
        ObjectHashMap map = sysRoleService.findRoleDataByUserId(userId);
        return Result.ok(map);
    }

    @Operation(summary = "用户分配角色")
    @PostMapping("/doAssign")
    public Result<?> doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok(null);
    }

    @Operation(summary = "查询所有角色", description = "查询所有角色，返回json")
    @GetMapping("/findAll")
    public Result<List<SysRole>> findAll() {
        return Result.ok(sysRoleService.list());
    }

    /**
     *
     * @param pageNum 当前页
     * @param pageSize 每页的记录数
     * @param sysRoleQueryVo 系统角色查询页
     * @return Result<Page<SysRole>> 分页的角色json
     */
    @Operation(summary = "条件分页查询", description = "通过条件分页查询角色，返回json")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result<Page<SysRole>> pageQuery(@PathVariable("pageNum") Long pageNum,
                                           @PathVariable("pageSize") Long pageSize,
                                           SysRoleQueryVo sysRoleQueryVo) {
        Page<SysRole> page = new Page<>(pageNum, pageSize);
        String roleName = sysRoleQueryVo.getRoleName();
        System.out.println("roleName = " + roleName);
        
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(roleName), SysRole::getRoleName, roleName);
        sysRoleService.page(page, wrapper);

        return Result.ok(page);
    }

    @Operation(summary = "添加角色", description = "添加角色")
    @PostMapping("save")
    public Result<?> save(@RequestBody SysRole sysRole) {
        return Result.bool(sysRoleService.save(sysRole));
    }

    @Operation(summary = "根据id查询角色", description = "根据id查询角色")
    @GetMapping("/get/{id}")
    public Result<SysRole> get(@PathVariable("id") Long id) {
        return Result.ok(sysRoleService.getById(id));
    }

    @Operation(summary = "修改角色", description = "修改角色")
    @PutMapping("/update")
    public Result<?> update(@RequestBody SysRole sysRole) {
        return Result.bool(sysRoleService.updateById(sysRole));
    }

    @Operation(summary = "根据id删除角色", description = "根据id删除角色")
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable("id") Long id) {
        return Result.bool(sysRoleService.removeById(id));
    }

    @Operation(summary = "根据ids批量删除角色", description = "根据ids批量删除角色")
    @DeleteMapping("/batchRemove")
    public Result<?> remove(@RequestBody List<Long> ids) {
        return Result.bool(sysRoleService.removeBatchByIds(ids));
    }

}