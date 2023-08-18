package com.lingfenglong.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.auth.mapper.SysMenuMapper;
import com.lingfenglong.auth.service.SysMenuService;
import com.lingfenglong.auth.service.SysRoleMenuService;
import com.lingfenglong.auth.utils.MenuHelper;
import com.lingfenglong.common.config.exception.MyException;
import com.lingfenglong.common.result.Result;
import com.lingfenglong.model.system.SysMenu;
import com.lingfenglong.model.system.SysRoleMenu;
import com.lingfenglong.vo.system.AssignMenuVo;
import com.lingfenglong.vo.system.MetaVo;
import com.lingfenglong.vo.system.RouterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author lingfenglong
 * @since 2023-08-15
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleMenuService sysRoleMenuService;

    @Autowired
    public SysMenuServiceImpl(SysRoleMenuService sysRoleMenuService) {
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Override
    public List<SysMenu> findNodes() {
        // 查询所有菜单数据
        List<SysMenu> sysMenuList = baseMapper.selectList(null);

        // 构建出树形结构
        return MenuHelper.buildTree(sysMenuList);
    }

    @Override
    public boolean removeMenuById(Long id) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getId, id);

        if (baseMapper.selectCount(wrapper) == 0) {
            throw new MyException(201, "不能删除菜单");
        } else {
            baseMapper.delete(wrapper);
            return true;
        }
    }

    @Override
    public Result<List<SysMenu>> findMenuByRoleId(Long roleId) {
        List<Long> selectedMenuIdList = sysRoleMenuService
                .list(new LambdaQueryWrapper<SysRoleMenu>()
                        .eq(SysRoleMenu::getRoleId, roleId))
                .stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());

        List<SysMenu> sysMenuList = baseMapper
                .selectList(new LambdaQueryWrapper<SysMenu>()
                        .eq(SysMenu::getStatus, 1))
                .stream()
                .peek(sysMenu -> sysMenu.setSelect(selectedMenuIdList.contains(sysMenu.getId())))
                .collect(Collectors.toList());

        return Result.ok(MenuHelper.buildTree(sysMenuList));
    }

    @Override
    public void doAssign(AssignMenuVo assignMenuVo) {
        Long roleId = assignMenuVo.getRoleId();

        // 根据角色id删除
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        sysRoleMenuService.remove(wrapper);

        // 分配属性
        assignMenuVo.getMenuIdList().forEach(menuId ->
                sysRoleMenuService.save(new SysRoleMenu(roleId, menuId)));
    }

    @Override
    public List<RouterVo> findUserMenuByUserId(Long userId) {
        List<SysMenu> sysMenuList;
        if (userId == 1) {
            // 管理员，查询所有
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getStatus, 1)
                    .orderByAsc(SysMenu::getSortValue);
            sysMenuList = baseMapper.selectList(wrapper);
        } else {
            // 不是管理员
            sysMenuList = baseMapper.findMenuListByUserId(userId);
        }

        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);

        return buildRouter(sysMenuTreeList);
    }

    private List<RouterVo> buildRouter(List<SysMenu> sysMenuTreeList) {
        return sysMenuTreeList.stream()
                .map(sysMenu -> {
                    RouterVo routerVo = new RouterVo();
                    routerVo.setHidden(false);
                    routerVo.setAlwaysShow(sysMenu.getType() == 0);
                    routerVo.setPath(getRouterPath(sysMenu));
                    routerVo.setComponent(sysMenu.getComponent());
                    routerVo.setMeta(new MetaVo(sysMenu.getName(), sysMenu.getIcon()));

                    List<SysMenu> children = sysMenu.getChildren();
                    if (!children.isEmpty() && sysMenu.getType() <= getTypeNum(2)) {
                        routerVo.setChildren(buildRouter(children));
                    } else {
                        routerVo.setChildren(null);
                    }
                    return routerVo;
                })
                .collect(Collectors.toList());
    }

    private Integer getTypeNum(int i) {
        return i - 2;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = "/" + menu.getPath();
        if(menu.getParentId().intValue() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }

    @Override
    public List<String> findUserPermsByUserId(Long userId) {
        if (userId == 1) {
            return baseMapper.selectList(
                    new LambdaQueryWrapper<SysMenu>()
                            .select(SysMenu::getType, SysMenu::getStatus, SysMenu::getPerms)
                            .eq(SysMenu::getType, 2)
                            .eq(SysMenu::getStatus, 1)
                    )
                    .stream()
                    .map(SysMenu::getPerms)
                    .collect(Collectors.toList());
        }
        return baseMapper.findPermsListByUserId(userId);
    }
}
