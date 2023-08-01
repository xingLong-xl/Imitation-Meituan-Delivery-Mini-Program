package com.sky.mapper;


import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * 动态条件查询
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 根据Id修改商品数量
     * @param cart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart cart);

    /**
     * 插入一条购物车数据
     * @param shoppingCart
     */
    @Insert("INSERT INTO shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time)" +
            " VALUES (#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{createTime})")
    void addShoppingCart(ShoppingCart shoppingCart);

    /**
     * 查看购物车
     * @return
     */
    @Select("select * from shopping_cart")
    List<ShoppingCart> selectAll();

    /**
     * 清空购物车
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteAll(Long userId);

    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(ShoppingCart shoppingCart);
}
