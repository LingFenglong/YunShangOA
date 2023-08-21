package com.lingfenglong.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.auth.mapper.SysUserMapper;
import com.lingfenglong.auth.service.SysUserService;
import com.lingfenglong.model.system.SysUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lingfenglong
 * @since 2023-08-11
 */
@Transactional
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    // 更改用户状态
    @Override
    public void updateStatus(Long id, Integer status) {
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getId, id)
                .set(SysUser::getStatus, status);
        baseMapper.update(null, wrapper);
    }

    @Override
    public SysUser getUserByUsername(String username) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<SysUser>()
                        .like(SysUser::getUsername, username)
        );
    }
}
