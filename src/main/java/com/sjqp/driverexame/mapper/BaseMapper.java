package com.sjqp.driverexame.mapper;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Marker;
import tk.mybatis.mapper.common.RowBoundsMapper;
import tk.mybatis.mapper.common.SqlServerMapper;
import tk.mybatis.mapper.common.base.BaseDeleteMapper;
import tk.mybatis.mapper.common.base.BaseSelectMapper;
import tk.mybatis.mapper.common.base.BaseUpdateMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author qinpeng on 2018/11/26.
 * mapper接口
 */
public interface BaseMapper<T> extends BaseSelectMapper<T>, InsertListMapper<T>, BaseUpdateMapper<T>, BaseDeleteMapper<T>, SqlServerMapper<T>,
        ConditionMapper<T>, RowBoundsMapper<T>, Marker {

}
