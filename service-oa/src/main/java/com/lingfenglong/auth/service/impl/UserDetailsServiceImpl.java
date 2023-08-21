package com.lingfenglong.auth.service.impl;

import com.lingfenglong.auth.service.SysUserService;
import com.lingfenglong.common.config.exception.MyException;
import com.lingfenglong.model.system.SysUser;
import com.lingfenglong.security.custom.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SysUserService sysUserService;

    @Autowired
    public UserDetailsServiceImpl(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        } else if (user.getStatus() == 0) {
            throw new MyException(201, "账号已被停用");
        }

        return new CustomUser(user, Collections.emptyList());
    }
}
