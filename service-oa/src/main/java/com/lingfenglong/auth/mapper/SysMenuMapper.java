package com.lingfenglong.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingfenglong.model.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author lingfenglong
 * @since 2023-08-15
 */

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findMenuListByUserId(@Param("userId") Long userId);

    List<String> findPermsListByUserId(@Param("userId") Long userId);
}
