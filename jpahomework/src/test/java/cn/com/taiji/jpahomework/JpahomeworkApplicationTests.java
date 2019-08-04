package cn.com.taiji.jpahomework;

import cn.com.taiji.jpahomework.domain.People;
import cn.com.taiji.jpahomework.service.PeopleService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpahomeworkApplicationTests {
    private static Logger LOGGER = LoggerFactory.getLogger(PeopleService.class);
    @Inject
    PeopleService peopleService;
    @Inject
    private ObjectMapper objectMapper;

    //添加
    @Test
    public void Test01() {
        People people = new People();
        people.setPeopleName("曾光");
        people.setFlag(1);
        peopleService.savePeople(people);
        List<People> list = new ArrayList<>();
        System.out.println(list);
    }

    //查询全部
    @Test
    public void Test02() {
        List<People> list = peopleService.findAllPeople();
        System.out.println(list.size());

    }

    //查询未删除的
    @Test
    public void Test03() {
        LOGGER.info("所有未删除的信息" + peopleService.findByFlag());
    }
    @Test
    //分页信息
    public void Test04() {
        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"peopleName\", \"value\":\"曾光\"}]}}";
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
        Map mapPeople = peopleService.getPage(searchParameters);
        List<People> list = (List<People>) mapPeople.get("peoples");
        for (People a : list
        ) {
            System.out.println(a.getPeopleName());
        }
        System.out.println(mapPeople.get("total"));

        System.out.println(mapPeople.get("peoples"));
    }
    @Test
    //伪删除
    public void Test05() {
        peopleService.delete(1);
    }
    @Test
    //更新
    public void update() {
        peopleService.update("111");
    }


}
