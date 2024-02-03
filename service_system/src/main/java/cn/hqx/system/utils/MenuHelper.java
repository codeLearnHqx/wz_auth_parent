package cn.hqx.system.utils;

import cn.hqx.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 菜单树构建
 * @Create by hqx
 * @Date 2024/2/3 21:27
 */
public class MenuHelper {

    /**
     * 构建菜单树
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenus) {
        // 创建集合封装最终数据
        ArrayList<SysMenu> trees = new ArrayList<>();
        // 遍历所有菜单
        sysMenus.forEach(sysMenu -> {
            // 找到递归入口
            if (sysMenu.getParentId() == 0) {
                // 查找比对菜单是否有子节点，有则加入当前菜单的children集合中
                trees.add(findChildren(sysMenu, sysMenus));
            }
        });
        return trees;
    }

    /**
     * 从根节点进行递归查询，查询子节点，判断 id == parentId 是否相同，如果是子节点，就进行数据的封装
     */
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
        sysMenu.setChildren(new ArrayList<>());
        // 当前菜单id
        Long id = Long.parseLong(sysMenu.getId());
        // 遍历
        treeNodes.forEach(treeNode -> {
            // 获取所有菜单id
            Long parentId = treeNode.getParentId();
            // 比对
            if (id.equals(parentId)) {
                // 查找比对菜单是否有子节点，有则加入当前菜单的children集合中
                sysMenu.getChildren().add(findChildren(treeNode, treeNodes));
            }

        });
        return sysMenu;
    }

}
