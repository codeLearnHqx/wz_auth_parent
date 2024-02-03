package cn.hqx.system.service.impl;


import cn.hqx.model.system.SysRole;
import cn.hqx.model.system.SysUserRole;
import cn.hqx.model.vo.AssignRoleVo;
import cn.hqx.model.vo.SysRoleQueryVo;
import cn.hqx.system.exception.BusinessException;
import cn.hqx.system.mapper.SysRoleMapper;
import cn.hqx.system.service.SysRoleService;
import cn.hqx.system.service.SysUserRoleService;
import cn.hqx.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public IPage<SysRole> selectPage(IPage<SysRole> page, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> pageModel =  baseMapper.selectPage(page, sysRoleQueryVo);
        return pageModel;
    }

    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {
        // 获取所有角色
        List<SysRole> roles = baseMapper.selectList(null);
        // 根据用户id进行查询已经分配角色
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(userId), SysUserRole::getUserId, userId);
        List<SysUserRole> userRoles = sysUserRoleService.list(wrapper);
        // 从userRoles中获取所有角色id
        List<String> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        HashMap<String, Object> map = new HashMap<>();
        map.put("allRoles", roles); // 所有角色
        map.put("userRoleIds", roleIds); // 用户分配角色id集合
        return map;
    }

    @Transactional
    @Override
    public Boolean doAssign(AssignRoleVo assignRoleVo) {
        String userId = assignRoleVo.getUserId();
        List<String> roleIdList = assignRoleVo.getRoleIdList();
        if (!StringUtils.hasText(userId)) {
            throw new BusinessException(201, "参数错误");
        }
        // 根据用户id删除原来的用户角色
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleService.remove(wrapper);
        // 获取所有角色id，添加到角色用户关系表中
        List<SysUserRole> userRoleList = roleIdList.stream().map(roleId -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            return sysUserRole;
        }).collect(Collectors.toList());
        boolean result = sysUserRoleService.saveBatch(userRoleList);
        return result;
    }
}
