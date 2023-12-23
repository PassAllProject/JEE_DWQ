package com.dwq.mapper;

import com.dwq.entity.dto.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO dwq_orders (user_id, total_price) VALUES (#{userId}, #{totalPrice})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    int insertOrder(Order order);

    @Delete("DELETE FROM dwq_orders WHERE order_id = #{id}")
    int deleteOrder(@Param("id") int id);

    @Update("UPDATE dwq_orders SET user_id = #{userId}, total_price = #{totalPrice} WHERE order_id = #{orderId}")
    int updateOrder(Order order);

    @Select("SELECT * FROM dwq_orders WHERE order_id = #{id}")
    List<Order> selectOrderById(@Param("id") int id);

    @Select("SELECT * FROM dwq_orders")
    List<Order> selectAllOrders();
}
