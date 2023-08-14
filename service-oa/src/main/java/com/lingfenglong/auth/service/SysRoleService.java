package com.lingfenglong.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingfenglong.common.collections.ObjectHashMap;
import com.lingfenglong.model.system.SysRole;
import com.lingfenglong.vo.system.AssginRoleVo;

public interface SysRoleService extends IService<SysRole> {
    // 获取用户所有角色
    ObjectHashMap findRoleDataByUserId(Long userId);

    // 为用户分配角色
    void doAssign(AssginRoleVo assginRoleVo);
}
