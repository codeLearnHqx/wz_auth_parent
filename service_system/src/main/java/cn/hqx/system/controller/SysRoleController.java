package cn.hqx.system.controller;

import cn.hqx.common.result.Result;
import cn.hqx.model.system.SysRole;
import cn.hqx.model.vo.SysRoleQueryVo;
import cn.hqx.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @Create by hqx
 * @Date 2024/1/25 16:50
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;


    // 逻辑删除接口
    @ApiOperation("逻辑删除接口")
    @DeleteMapping("/remove/{id}")
    public Result<Boolean> removeRole(@PathVariable Long id) {
        boolean isSuccess = sysRoleService.removeById(id);
        if (isSuccess) {
            return Result.ok(true);
        } else {
            return Result.fail(false);
        }
    }

    // 查询所有记录
    @ApiOperation("查询所有记录")
    @GetMapping("/findAll")
    public Result<List<SysRole>> findAllRole() {
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    // 条件分页查询
    @ApiOperation("条件分页查询")
    @GetMapping("/page/{current}/{size}")
    public Result<IPage<SysRole>> findPageQueryRole(@PathVariable Long current,
                                                    @PathVariable Long size,
                                                    SysRoleQueryVo sysRoleQueryVo) {
        // 构建分页器
        IPage<SysRole> page = new Page<>(current, size);

        IPage<SysRole> pageModel = sysRoleService.selectPage(page, sysRoleQueryVo);

        return Result.ok(pageModel);
    }

}
