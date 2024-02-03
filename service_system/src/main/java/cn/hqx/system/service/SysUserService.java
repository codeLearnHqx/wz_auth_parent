package cn.hqx.system.service;

import cn.hqx.model.system.SysUser;
import cn.hqx.model.vo.AssignRoleVo;
import cn.hqx.model.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author hqx
 * @since 2024-01-31
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 条件分页查询
     */
    IPage<SysUser> selectPage(IPage<SysUser> page, SysUserQueryVo sysUserQueryVo);

    /**
     * 更改用户状态
     */
    Boolean updateStatus(Long id, Boolean status);


}
