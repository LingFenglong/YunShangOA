package com.lingfenglong.auth.utils;

import com.lingfenglong.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    // 使用递归构建菜单
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        List<SysMenu> res = new ArrayList<>();

        sysMenuList.stream()
                .filter(sysMenu -> sysMenu.getParentId() == 0)
                .forEach(sysMenu -> {
                    res.add(sysMenu);
                    sysMenu.setChildren(new ArrayList<>());
                    buildTreeHelper(sysMenuList, sysMenu.getChildren(), sysMenu.getId());
                });
        return res;
    }

    private static void buildTreeHelper(List<SysMenu> sysMenuList, List<SysMenu> children, Long parentId) {
        sysMenuList.stream()
            .filter(sysMenu -> sysMenu.getParentId().equals(parentId))
            .forEach(sysMenu -> {
                children.add(sysMenu);
                sysMenu.setChildren(new ArrayList<>());
                buildTreeHelper(sysMenuList, sysMenu.getChildren(), sysMenu.getId());
            });
    }
}
