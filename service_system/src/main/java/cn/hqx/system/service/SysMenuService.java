package cn.hqx.system.service;


import cn.hqx.model.system.SysMenu;
import cn.hqx.model.vo.AssignMenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author hqx
 * @since 2024-02-03
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单列表
     */
    List<SysMenu> findNodes();

    /**
     * 删除菜单
     */
    boolean removeMenuById(Long id);

    /**
     * 根据角色获取菜单
     */
    List<SysMenu> findMenuByRoleId(Long roleId);

    /**
     * 给角色分配菜单
     */
    Boolean doAssign(AssignMenuVo assignMenuVo);
}
