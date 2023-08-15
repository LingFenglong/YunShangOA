package com.lingfenglong.auth.controller;


import com.lingfenglong.auth.service.SysMenuService;
import com.lingfenglong.common.result.Result;
import com.lingfenglong.model.system.SysMenu;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author lingfenglong
 * @since 2023-08-15
 */
@RestController
@Tag(name = "菜单管理接口")
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    private final SysMenuService sysMenuService;

    @Autowired
    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @Operation(summary = "获取菜单")
    @GetMapping("findNodes")
    public Result<List<SysMenu>> findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @Operation(summary = "新增菜单")
    @PostMapping("save")
    public Result<?> save(@RequestBody SysMenu permission) {
        sysMenuService.save(permission);
        return Result.ok(null);
    }

    @Operation(summary = "修改菜单")
    @PutMapping("update")
    public Result<?> updateById(@RequestBody SysMenu permission) {
        sysMenuService.updateById(permission);
        return Result.ok(null);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("remove/{id}")
    public Result<?> remove(@PathVariable Long id) {
        return Result.bool(sysMenuService.removeMenuById(id));
    }
}
