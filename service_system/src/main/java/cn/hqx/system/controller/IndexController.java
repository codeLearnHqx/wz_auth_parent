package cn.hqx.system.controller;

import cn.hqx.common.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户登录接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    // login
    @PostMapping("/login")
    public Result<Map<String, Object>> login() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", "admin-token");
        return Result.ok(map);
    }


    @GetMapping("/info")
    public Result<HashMap<String, Object>> info() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("introduction", "I am a super administrator");
        map.put("name", "Super Admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

}
