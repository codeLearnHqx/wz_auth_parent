package cn.hqx.system.mapper;

import cn.hqx.model.system.SysUser;
import cn.hqx.model.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author hqx
 * @since 2024-01-31
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 用户条件分页查询
     */
    IPage<SysUser> selectPage(IPage<SysUser> page, @Param("vo") SysUserQueryVo sysUserQueryVo);

}
