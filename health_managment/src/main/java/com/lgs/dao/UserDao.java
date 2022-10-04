package com.lgs.dao;

import com.lgs.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public interface UserDao {
    void insert(User user);//插入数据
    void update(@Param("user")User user, @Param("userName")String userName);//更新数据
    User selectByUsername(String username);//通过username查询单条数据
    List<User> selectAll();//查询所有的数据
}
