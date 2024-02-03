package cn.hqx.system.service.impl;

import cn.hqx.model.system.SysUser;
import cn.hqx.model.vo.AssignRoleVo;
import cn.hqx.model.vo.SysUserQueryVo;
import cn.hqx.system.mapper.SysUserMapper;
import cn.hqx.system.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author hqx
 * @since 2024-01-31
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public IPage<SysUser> selectPage(IPage<SysUser> page, SysUserQueryVo sysUserQueryVo) {
        return baseMapper.selectPage(page, sysUserQueryVo);
    }

    @Override
    public Boolean updateStatus(Long id, Boolean status) {
        // 根据用户id查询数据
        SysUser sysUser = baseMapper.selectById(id);
        // 设置用户的新状态
        sysUser.setStatus(status);
        int result = baseMapper.updateById(sysUser);
        return result == 1;
    }
}
