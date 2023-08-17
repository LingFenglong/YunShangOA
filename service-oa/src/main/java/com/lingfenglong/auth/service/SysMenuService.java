package com.lingfenglong.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.common.result.Result;
import com.lingfenglong.model.system.SysMenu;
import com.lingfenglong.vo.system.AssignMenuVo;
import com.lingfenglong.vo.system.RouterVo;

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

    boolean removeMenuById(Long id);

    Result<List<SysMenu>> findMenuByRoleId(Long roleId);

    void doAssign(AssignMenuVo assignMenuVo);

    List<RouterVo> findUserMenuByUserId(Long userId);

    List<String> findUserPermsByUserId(Long userId);
}
