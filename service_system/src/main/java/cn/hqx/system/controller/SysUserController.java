package cn.hqx.system.controller;


import cn.hqx.common.result.Result;
import cn.hqx.model.system.SysUser;
import cn.hqx.model.vo.AssignRoleVo;
import cn.hqx.model.vo.SysUserQueryVo;
import cn.hqx.system.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author hqx
 * @since 2024-01-31
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Resource
    private SysUserService userService;


    // 用户条件分页查询
    @ApiOperation("用户列表")
    @GetMapping("/{current}/{size}")
    public Result<IPage<SysUser>> listUser(@PathVariable Long current, @PathVariable Long size, SysUserQueryVo sysUserQueryVo) {
        IPage<SysUser> page = new Page<>(current, size);
        IPage<SysUser> pageModel =  userService.selectPage(page, sysUserQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(("添加用户"))
    @PostMapping("/save")
    public Result<Boolean> saveUser(@RequestBody SysUser sysUser) {
        boolean isSuccess = userService.save(sysUser);
        if (isSuccess) {
            return Result.ok(true);
        } else {
            return Result.fail(false);
        }
    }

    @ApiOperation(("根据id查询"))
    @GetMapping("/getUser/{id}")
    public Result<SysUser> getUser(@PathVariable Long id) {
        SysUser user = userService.getById(id);
        if (user == null) {
            return Result.fail();
        }
        return Result.ok(user);
    }

    @ApiOperation(("修改用户"))
    @PostMapping("/update")
    public Result<Boolean> updateUser(@RequestBody SysUser sysUser) {
        boolean isSuccess = userService.updateById(sysUser);
        if (isSuccess) {
            return Result.ok(true);
        } else {
            return Result.fail(false);
        }
    }

    @ApiOperation(("删除用户"))
    @DeleteMapping("/remove/{id}")
    public Result<Boolean> removeUser(@PathVariable Long id) {
        boolean isSuccess = userService.removeById(id);
        if (isSuccess) {
            return Result.ok(true);
        } else {
            return Result.fail(false);
        }
    }

    @ApiOperation("更改用户状态")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result<Boolean> updateStatus(@PathVariable Long id, @PathVariable Boolean status) {
        Boolean isSuccess = userService.updateStatus(id, status);
        if (isSuccess) {
            return Result.ok(true);
        } else {
            return Result.fail(false);
        }
    }



}

