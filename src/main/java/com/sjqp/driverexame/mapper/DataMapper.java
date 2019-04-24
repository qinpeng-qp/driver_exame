package com.sjqp.driverexame.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author qinpeng
 * 统计数据mapper层
 */
@Mapper
public interface DataMapper {

    /**
     * 获取用户数
     * @return
     */
    @Select("select count(id) from user_info")
    Integer getUserCount();


    /**
     * 获取题目数
     * @return
     */
    @Select("SELECT sum(total) as totalCount from(\n" +
            "\n" +
            "\tSELECT count(id) AS total FROM simulated_exercise\n" +
            "\t\n" +
            "\tUNION\n" +
            "\tSELECT count(id) AS total FROM error_exercise\n" +
            "\t\n" +
            "\tUNION\n" +
            "\tSELECT count(id) AS total FROM real_exercise\n" +
            "\n" +
            ") as temp\n")
    Integer getQuestionCount();


}
