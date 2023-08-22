package com.lingfenglong.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingfenglong.common.collections.ObjectHashMap;
import com.lingfenglong.common.jwt.JwtHelper;
import com.lingfenglong.common.result.Result;
import com.lingfenglong.common.result.ResultCode;
import com.lingfenglong.common.result.ResponseUtil;
import com.lingfenglong.security.custom.CustomUser;
import com.lingfenglong.vo.system.LoginVo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    public TokenLoginFilter(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);

        // 指定登陆接口及提交方式
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("admin/system/index/login", "POST"));
    }

    /**
     * 登录认证的方法
     * @param request from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     * redirect as part of a multi-stage authentication process (such as OIDC).
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 获取用户名和密码
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);

            // 获取 authentication 对象
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());

            // 验证用户名与密码
            return this.getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 认证成功的方法
     * @param request
     * @param response
     * @param chain
     * @param authResult the object returned from the <tt>attemptAuthentication</tt>
     * method.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        // 获取当前用户
        CustomUser customUser = (CustomUser) authResult.getPrincipal();

        // 生成Token字符串
        String token = JwtHelper.createToken(customUser.getSysUser().getId(), customUser.getUsername());

        // 返回数据
        ObjectHashMap map = new ObjectHashMap();
        map.put("token", token);
        ResponseUtil.out(response, Result.ok(map));
    }

    /**
     * 认证失败的方法
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response, Result.build(null, ResultCode.LOGIN_ERROR));
    }
}
