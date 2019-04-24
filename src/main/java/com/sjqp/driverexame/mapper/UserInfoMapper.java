package com.sjqp.driverexame.mapper;

import com.sjqp.driverexame.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author qinpeng
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 获取所有用户信息
     * @return
     */
    @Select("select id,username from user_info order by id")
    List<UserInfo> getUserInfo();



}
