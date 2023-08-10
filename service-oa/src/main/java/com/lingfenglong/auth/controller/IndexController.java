package com.lingfenglong.auth.controller;

import com.lingfenglong.common.collections.ParameterMap;
import com.lingfenglong.common.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "登录控制器")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    // login
    @PostMapping("/login")
    public Result<ParameterMap> login() {
        // {code: 20000, data: {token: "admin-token"}}
        ParameterMap data = new ParameterMap();
        data.add("token", "admin-token");
        return Result.ok(data);
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
