package com.duan.springboot_jpa;

import com.duan.springboot_jpa.pojo.Dept;
import com.duan.springboot_jpa.pojo.User;
import com.duan.springboot_jpa.service.DeptService;
import com.duan.springboot_jpa.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJpaApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(SpringbootJpaApplicationTests.class);

    @Autowired
    UserService userService;

    @Autowired
    DeptService deptService;

    @Autowired
    private ObjectMapper objectMapper;



    /**
     * 插入 Dept 数据
     */
    @Ignore
    @Test
    public void textDept1() {
        for (int i=1;i<=10;i++){
            Dept dept = new Dept();
            dept.setId(i++);
            dept.setFlag(i++);
            deptService.saveDept(dept);
        }
    }

    /**
     *查询 Dept 全部数据
     */
    @Ignore
    @Test
    public void textDept2() {
        deptService.selectDept();
    }

    /**
     * 查询 Dept (根据条件查询)
     */
    @Ignore
    @Test
    public void textDept3() {
        Dept dept = deptService.selectById(1);
        System.out.println(dept+"======");
    }

    /**
     * 查询(根据条件查询)
     */
    @Ignore
    @Test
    public void textDept4(){
        deptService.selectByDeptIndex();
    }

    /**
     * 分页查询
     */
    @Ignore
    @Test
    public void testDeptPage() {
        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"deptName\", \"value\":\"机构8\"}]}}";
        Map searchParameters = new HashMap();
        try {
            searchParameters = objectMapper.readValue(map, new TypeReference<Map>() {
            });
        } catch (JsonParseException e) {
            log.error("JsonParseException{}:", e.getMessage());
        } catch (JsonMappingException e) {
            log.error("JsonMappingException{}:", e.getMessage());
        } catch (IOException e) {
            log.error("IOException{}:", e.getMessage());
        }

        Map mapDept = deptService.getPage(searchParameters);


        System.out.println(mapDept.get("total"));

        System.out.println(mapDept.get("depts"));
    }





    /**
     * 插入 User 数据
     */
    @Ignore
    @Test
    public void text2() {
        User user = new User();
        //user.setId(1);
        user.setName("yyy");
        user.setPassword("1212");
        userService.saveUser(user);
    }

    /**
     * 分页查询
     */
    @Ignore
    @Test
    public void testUserPage() {
        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"deptName\", \"value\":\"机构8\"}]}}";
        Map searchParameters = new HashMap();
        try {
            searchParameters = objectMapper.readValue(map, new TypeReference<Map>() {
            });
        } catch (JsonParseException e) {
            log.error("JsonParseException{}:", e.getMessage());
        } catch (JsonMappingException e) {
            log.error("JsonMappingException{}:", e.getMessage());
        } catch (IOException e) {
            log.error("IOException{}:", e.getMessage());
        }

        Map mapDept = userService.getPage(searchParameters);


        System.out.println(mapDept.get("total"));

        System.out.println(mapDept.get("depts"));
    }
}
