package cn.hqx.system.service.impl;


import cn.hqx.model.system.SysRole;
import cn.hqx.system.mapper.SysRoleMapper;
import cn.hqx.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
