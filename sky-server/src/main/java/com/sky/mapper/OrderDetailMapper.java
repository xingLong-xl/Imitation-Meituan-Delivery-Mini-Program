package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    /**
     * 批量插入订单详细数据
     * @param orderDetailList
     */
    void insertList(List<OrderDetail> orderDetailList);
}
