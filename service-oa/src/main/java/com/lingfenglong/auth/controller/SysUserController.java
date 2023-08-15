package com.lingfenglong.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lingfenglong.auth.service.SysUserService;
import com.lingfenglong.common.result.Result;
import com.lingfenglong.model.system.SysUser;
import com.lingfenglong.vo.system.SysUserQueryVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lingfenglong
 * @since 2023-08-11
 */
@RestController
@Tag(name = "用户管理", description = "用户管理的接口")
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    // 更改状态
    @Operation(summary = "更改用户状态")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result<?> updateStatus(@PathVariable("id") Long id,
                                  @PathVariable("status") Integer status) {
        sysUserService.updateStatus(id, status);
        return Result.ok(null);
    }

    @Operation(summary = "查询所有用户", description = "查询所有用户")
    @GetMapping("/findAll")
    public Result<List<SysUser>> findAll() {
        return Result.ok(sysUserService.list(null));
    }

    // 用户条件分页查询
    @Operation(summary = "用户的条件分页查询")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result<Page<SysUser>> pageQuery(@PathVariable("pageNum") Long pageNum,
                                           @PathVariable("pageSize") Long pageSize,
                                           SysUserQueryVo sysUserQueryVo) {
        Page<SysUser> page = new Page<>(pageNum, pageSize);
        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(username), SysUser::getUsername, username)
                .ge(StringUtils.isNotBlank(createTimeBegin), SysUser::getCreateTime, createTimeBegin)
                .gt(StringUtils.isNotBlank(createTimeEnd), SysUser::getCreateTime, createTimeEnd);

        sysUserService.page(page, wrapper);
        return Result.ok(page);
    }

    @Operation(summary = "添加用户", description = "添加用户")
    @PostMapping("save")
    public Result<?> save(@RequestBody SysUser sysUser) {
        return Result.bool(sysUserService.save(sysUser));
    }

    @Operation(summary = "根据id查询用户", description = "根据id查询用户")
    @GetMapping("/get/{id}")
    public Result<SysUser> get(@PathVariable("id") Long id) {
        return Result.ok(sysUserService.getById(id));
    }

    @Operation(summary = "修改用户", description = "修改用户")
    @PutMapping("/update")
    public Result<?> update(@RequestBody SysUser sysUser) {
        return Result.bool(sysUserService.updateById(sysUser));
    }

    @Operation(summary = "根据id删除用户", description = "根据id删除用户")
    @DeleteMapping("/remove/{id}")
    public Result<?> remove(@PathVariable("id") Long id) {
        return Result.bool(sysUserService.removeById(id));
    }

    @Operation(summary = "根据ids批量删除用户", description = "根据ids批量删除用户")
    @DeleteMapping("/batchRemove")
    public Result<?> remove(@RequestBody List<Long> ids) {
        return Result.bool(sysUserService.removeBatchByIds(ids));
    }
}