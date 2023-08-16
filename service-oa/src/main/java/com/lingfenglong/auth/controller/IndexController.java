package com.lingfenglong.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lingfenglong.auth.service.SysUserService;
import com.lingfenglong.common.collections.ParameterMap;
import com.lingfenglong.common.config.exception.MyException;
import com.lingfenglong.common.jwt.JwtHelper;
import com.lingfenglong.common.result.Result;
import com.lingfenglong.model.system.SysUser;
import com.lingfenglong.vo.system.LoginVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;


@Tag(name = "登录控制器")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    private final SysUserService sysUserService;

    @Autowired
    public IndexController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    // login
    @PostMapping("/login")
    public Result<ParameterMap> login(@RequestBody LoginVo loginVo) {
        // {code: 20000, data: {token: "admin-token"}}
//        ParameterMap data = new ParameterMap();
//        data.add("token", "admin-token");

        // 应该在前端进行加密
        String password = new String(DigestUtils.md5Digest(loginVo.getPassword().getBytes()));

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, loginVo.getUsername());
        SysUser user = sysUserService.getOne(wrapper);

        if (user == null) {
            throw new MyException(201, "用户不存在");
        } else if (!user.getPassword().equals(password)) {
            throw new MyException(201, "账户或密码错误");
        } else if (user.getStatus() == 0) {
            throw new MyException(201, "账户已被禁用");
        }

        String token = JwtHelper.createToken(user.getId(), user.getUsername());
        ParameterMap parameter = new ParameterMap();
        parameter.add("token", token);
        return Result.ok(parameter);
    }

    // info
    @GetMapping("/info")
    public Result<ParameterMap> info() {
        ParameterMap data = new ParameterMap();
        data.add("roles", "[admin]");
        data.add("name", "admin");
        data.add("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");

        return Result.ok(data);
    }

    // logout
    @GetMapping("logout")
    public Result<ParameterMap> logout() {
        ParameterMap data = new ParameterMap();

        return Result.ok(data);
    }
}
