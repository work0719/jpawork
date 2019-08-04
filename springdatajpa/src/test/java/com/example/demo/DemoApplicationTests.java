package com.example.demo;

import com.example.demo.domain.Dept;
import com.example.demo.domain.User;
import com.example.demo.service.DeptService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    private static Logger LOGGER =  LoggerFactory.getLogger(DeptService.class);
    @Inject
    DeptService deptService;
    @Inject
    UserService userService;
    @Inject
    private ObjectMapper objectMapper;
    @Ignore
    @Test
    public void Test01() {
        Dept dept = new Dept();
        dept.setDeptName("曾光");
        dept.setFlag(1);
        deptService.saveDept(dept);
        List<Dept> list = new ArrayList<>();
        System.out.println(list);
    }
    @Ignore
    @Test
    public void Test02(){

        Dept dept = new Dept();
         deptService.findAllDept(dept);

    }
    @Test
    public void Test03(){
        deptService.oneFindByFlagAndParentIsNullOrderByDeptIndexAsc();
    }
    @Ignore
    @Test
    public void test() {
        List<Dept> list = deptService.AAA(0);
        System.out.println(list.toString());
    }

    /*@Ignore
    @Test
    public void test1() {
        for (int i = 0; i < 9; i++) {
            Dept dept = new Dept();
            dept.setDeptName("机构" + i);
            dept.setFlag(1);
            deptService.saveDept(dept);
        }

        List<Dept> list = deptService.findAllDepts();
        System.out.println(list.size());
    }
*/
    /*@Ignore
    @Test
    public void test2() {
        for (int i = 0; i < 9; i++) {
            User user = new User();
            user.setUserName("用户" + i);
            user.setFlag(1);
            userService.saveUser(user);
        }

        List<User> list = userService.findAllUsers();
        System.out.println(list.size());
    }*/

    /*@Ignore
    @Test
    public void test3() {
        User user = userService.findByName("asd");
        // User user = userService.findUserById(1);
        // Dept dept = deptService.findDeptById(1);
        // List<Dept> depts = new ArrayList<Dept>();
        // depts.add(dept);
        // System.out.println(depts.size());
        // user.setDepts(depts);
        // user.getDepts().remove(0);
        userService.saveUser(user);
        System.out.println(user.getName() + "----user");
    }*/

    @Ignore
    @Test
    public void test4() {
        Dept dept = deptService.findDeptById(1);
        System.out.println(dept + "----dept");
    }

    @PersistenceContext
    EntityManager em;

    @Ignore
    @Test
    public void test5() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> c = cb.createQuery(User.class);
        Root<User> user = c.from(User.class);

        CriteriaQuery query = c.select(user).where(cb.equal(user.get("name"), "asd"));

        TypedQuery query1 = em.createQuery(query);

        List<User> list = query1.getResultList();

        System.out.println(query1.getResultList());

    }
    //	@Ignore
    @Test
    public void testPage() {
        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"deptName\", \"value\":\"机构8\"}]}}";
        Map searchParameters = new HashMap();
        try {
            searchParameters = objectMapper.readValue(map, new TypeReference<Map>() {
            });
        } catch (JsonParseException e) {
            LOGGER.error("JsonParseException{}:", e.getMessage());
        } catch (JsonMappingException e) {
            LOGGER.error("JsonMappingException{}:", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("IOException{}:", e.getMessage());
        }

        Map mapDept = deptService.getPage(searchParameters);


        List<Dept> list= (List<Dept>) mapDept.get("depts");
        for (Dept a:list
             ) {
            System.out.println(a.getDeptName());
        }
        System.out.println(mapDept.get("total"));

        System.out.println(mapDept.get("depts"));
    }
    @Test
    public void testPage1() {
        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"loginName\", \"value\":\"123456\"}]}}";
        Map searchParameters = new HashMap();
        try {
            searchParameters = objectMapper.readValue(map, new TypeReference<Map>() {
            });
        } catch (JsonParseException e) {
            LOGGER.error("JsonParseException{}:", e.getMessage());
        } catch (JsonMappingException e) {
            LOGGER.error("JsonMappingException{}:", e.getMessage());
        } catch (IOException e) {
            LOGGER.error("IOException{}:", e.getMessage());
        }

        Map mapUser = userService.getPage(searchParameters);


        List<User> list= (List<User>) mapUser.get("users");
        for (User a:list
        ) {
            System.out.println(a.getLoginName());
        }
        System.out.println(mapUser.get("total"));

        System.out.println(mapUser.get("users"));
    }

}
