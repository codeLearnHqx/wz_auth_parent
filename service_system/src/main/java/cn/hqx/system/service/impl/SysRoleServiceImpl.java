package cn.hqx.system.service.impl;


import cn.hqx.model.system.SysRole;
import cn.hqx.model.vo.SysRoleQueryVo;
import cn.hqx.system.mapper.SysRoleMapper;
import cn.hqx.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Override
    public IPage<SysRole> selectPage(IPage<SysRole> page, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> pageModel =  baseMapper.selectPage(page, sysRoleQueryVo);
        return pageModel;
    }
}
