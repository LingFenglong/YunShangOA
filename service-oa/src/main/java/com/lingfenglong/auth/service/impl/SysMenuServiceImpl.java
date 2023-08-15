package com.lingfenglong.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingfenglong.auth.mapper.SysMenuMapper;
import com.lingfenglong.auth.service.SysMenuService;
import com.lingfenglong.auth.utils.MenuHelper;
import com.lingfenglong.model.system.SysMenu;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<SysMenu> findNodes() {
        // 查询所有菜单数据
        List<SysMenu> sysMenuList = baseMapper.selectList(null);

        // 构建出树形结构
        return MenuHelper.buildTree(sysMenuList);
    }

    @Override
    public void removeMenuById(Long id) {
        LambdaUpdateWrapper<SysMenu> wrapper = new LambdaUpdateWrapper<>();

        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        sysMenuList.stream()
                .filter()
    }
}
