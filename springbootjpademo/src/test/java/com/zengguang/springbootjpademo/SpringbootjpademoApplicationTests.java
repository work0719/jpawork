package com.zengguang.springbootjpademo;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zengguang.springbootjpademo.po.User;
import com.zengguang.springbootjpademo.service.DeptService;
import com.zengguang.springbootjpademo.service.UserService;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootjpademoApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(SpringbootjpademoApplicationTests.class);
    @Inject
    UserService userService;
    @Inject
    private ObjectMapper objectMapper;
    @Inject
    DeptService deptService;



    @Test
    public void saveUser(){
        User user=new User();
        user.setName("hahah");
        user.setFlag(1);
        userService.saveUser(user);
    }

    @Test
    public void findAllUser(){
         userService.findAllUser();
    }
    @Test
    public void findByFlagAndNameOrderByUserIndexAsc(){
       List<User> userList = userService.findByFlagAndNameOrderByUserIndexAsc();
        System.out.println(userList.size());
    }
    @Test
    public void findByFlagAndParentIsNullOrderByDeptIndexAsc(){
        userService.findByFlagAndParentIsNullOrderByDeptIndexAsc();
    }

    @Test
    public void testPageQuery(){
        userService.testPageQuery();
    }

    //dept分页
    @Test
    public void testPage() {
        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"username\", \"value\":\"1\"}]}}";
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

    //user分页
    @Test
    public void testUserPage() {
        String map = "{\"page\" : 1,\"pageSize\" : 4, \"filter\":{ \"filters\":[{ \"field\" : \"username\", \"value\":\"1\"}]}}";
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
