package com.lingfenglong.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingfenglong.model.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lingfenglong
 * @since 2023-08-11
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
