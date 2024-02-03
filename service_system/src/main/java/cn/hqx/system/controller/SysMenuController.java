package cn.hqx.system.controller;


import cn.hqx.common.result.Result;
import cn.hqx.model.system.SysMenu;
import cn.hqx.model.vo.AssignMenuVo;
import cn.hqx.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author hqx
 * @since 2024-02-03
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;


    @ApiOperation("给角色分配菜单")
    @PostMapping("/doAssign")
    public Result<Boolean> doAssign(@RequestBody AssignMenuVo assignMenuVo) {
        Boolean isSuccess = sysMenuService.doAssign(assignMenuVo);
        if (isSuccess) {
            return Result.ok(true);
        } else {
            return Result.fail(false);
        }

    }


    // 根据角色分配菜单
    @ApiOperation("根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result<List<SysMenu>> toAssign(@PathVariable Long roleId) {
        List<SysMenu> list = sysMenuService.findMenuByRoleId(roleId);
        return Result.ok(list);
    }

    // 菜单列表（树形）
    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public Result<List<SysMenu>> findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @ApiOperation("添加菜单")
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SysMenu sysMenu) {
        boolean isSuccess = sysMenuService.save(sysMenu);
        if (isSuccess) {
            return Result.ok(true);
        } else {
            return Result.fail(false);
        }
    }

    @ApiOperation(("根据id查询"))
    @GetMapping("/findNode/{id}")
    public Result<SysMenu> findNode(@PathVariable Long id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        if (sysMenu == null) {
            return Result.fail();
        }
        return Result.ok(sysMenu);
    }

    @ApiOperation(("修改菜单"))
    @PutMapping("/update")
    public Result<Boolean> updateMenu(@RequestBody SysMenu sysMenu) {
        boolean isSuccess = sysMenuService.updateById(sysMenu);
        if (isSuccess) {
            return Result.ok(true);
        } else {
            return Result.fail(false);
        }
    }

    @ApiOperation(("删除菜单"))
    @DeleteMapping("/remove/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        boolean isSuccess = sysMenuService.removeMenuById(id);
        if (isSuccess) {
            return Result.ok(true);
        } else {
            return Result.fail(false);
        }
    }
}

