package cn.hqx.system.service;

import cn.hqx.model.system.SysRole;
import cn.hqx.model.vo.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysRoleService extends IService<SysRole> {

    /**
     * 条件分页查询
     * @param page 分页器对象
     * @param sysRoleQueryVo 查询内容对象
     * @return IPage<SysRole> 封装好的分页器对象
     */
    IPage<SysRole> selectPage(IPage<SysRole> page, SysRoleQueryVo sysRoleQueryVo);
}
