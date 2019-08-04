package com.duan.springboot_jpa.pojo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * ClassName: UserRepository
 *
 * @Description: ${todo}
 * @Author dph
 * @CreatDate ${date}
 */
public interface UserRepository extends JpaRepository<User, Integer>,
        JpaSpecificationExecutor<User> {

}
