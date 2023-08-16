package com.lingfenglong.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import com.lingfenglong.vo.system.AssignRoleVo;
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
}
