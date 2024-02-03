package cn.hqx.system.service.impl;

import cn.hqx.model.system.SysMenu;
import cn.hqx.model.system.SysRoleMenu;
import cn.hqx.model.vo.AssignMenuVo;
import cn.hqx.system.exception.BusinessException;
import cn.hqx.system.mapper.SysMenuMapper;
import cn.hqx.system.mapper.SysRoleMenuMapper;
import cn.hqx.system.service.SysMenuService;
import cn.hqx.system.service.SysRoleMenuService;
import cn.hqx.system.utils.MenuHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author hqx
 * @since 2024-02-03
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenu> findNodes() {
        // 获取所有菜单
        List<SysMenu> sysMenus = baseMapper.selectList(null);
        // 所有菜单数据转换成要求数据格式
        List<SysMenu> resultList = MenuHelper.buildTree(sysMenus);
        return resultList;
    }

    @Override
    public boolean removeMenuById(Long id) {
        // 查询当前删除菜单下面是否有子菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId, id);
        Integer count = baseMapper.selectCount(wrapper);
        if (count != null && count > 0) {
            throw new BusinessException(201, "请求先删除子菜单");
        }
        // 删除操作
        int result = baseMapper.deleteById(id);
        return result == 1;
    }

    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {
        // 获取所有菜单 status = 1（true） 时可用
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getStatus, true);
        List<SysMenu> menuList = baseMapper.selectList(wrapper);
        // 根据角色id查询 角色分配过的菜单列表
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRoleMenuLambdaQueryWrapper.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> roleMenuList = sysRoleMenuService.list(sysRoleMenuLambdaQueryWrapper);
        // 从上一步的查询列表中获取角色分配的所有菜单id
        List<String> menuIds = roleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        // 数据处理: isSelect 如果菜单选中 true，否则 false
        menuList.forEach(sysMenu -> {
            // menuList中存在与menuIds中相同的id就设置为true
            sysMenu.setSelect(menuIds.contains(sysMenu.getId()));
        });

        // 转换成树形结构，为了最终显示
        List<SysMenu> sysMenus = MenuHelper.buildTree(menuList);
        return sysMenus;
    }


    @Transactional
    @Override
    public Boolean doAssign(AssignMenuVo assignMenuVo) {
        if (assignMenuVo == null || assignMenuVo.getRoleId() == null) {
            throw new BusinessException(201, "请求参数错误");
        }
        // 根据角色id删除菜单权限
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRoleMenuLambdaQueryWrapper.eq(SysRoleMenu::getRoleId, assignMenuVo.getRoleId());
        boolean remove = sysRoleMenuService.remove(sysRoleMenuLambdaQueryWrapper);
        if (assignMenuVo.getMenuIdList() != null && assignMenuVo.getMenuIdList().size() != 0) {
            // 遍历菜单id，设置新的角色权限
            List<String> menuIdList = assignMenuVo.getMenuIdList();
            List<SysRoleMenu> roleMenuList = menuIdList.stream().map(menuId -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
                return sysRoleMenu;
            }).collect(Collectors.toList());
            // 批量保存
            boolean result = sysRoleMenuService.saveBatch(roleMenuList);
            return result;
        }
        return remove;

    }
}
