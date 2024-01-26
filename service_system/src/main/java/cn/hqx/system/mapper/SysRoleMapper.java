package cn.hqx.system.mapper;

import cn.hqx.model.system.SysRole;
import cn.hqx.model.vo.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 条件分页查询
     */
    public IPage<SysRole> selectPage(@Param("page") IPage<SysRole> page, @Param("sysRoleQueryVo") SysRoleQueryVo sysRoleQueryVo);
}
