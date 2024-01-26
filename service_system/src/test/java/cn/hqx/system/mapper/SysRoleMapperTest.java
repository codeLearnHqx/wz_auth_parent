package cn.hqx.system.mapper;


import cn.hqx.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class SysRoleMapperTest {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Test
    void testFindAll() {
        List<SysRole> sysRoleList = sysRoleMapper.selectList(null);
        sysRoleList.forEach(System.out::println);
    }

}
