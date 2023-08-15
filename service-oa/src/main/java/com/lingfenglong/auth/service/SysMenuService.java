package com.lingfenglong.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.model.system.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author lingfenglong
 * @since 2023-08-15
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    void removeMenuById(Long id);
}
