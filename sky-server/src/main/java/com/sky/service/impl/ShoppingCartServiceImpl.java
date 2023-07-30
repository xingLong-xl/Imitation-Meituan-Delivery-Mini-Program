package com.sky.service.impl;


import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    public static final Integer number = 1;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        // 判断当前加入到购物车中的商品是否已经存在了
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartlist = shoppingCartMapper.list(shoppingCart);
        // 如果已经存在了，只需要将数量加一
        if (shoppingCartlist != null && shoppingCartlist.size() > 0){
            ShoppingCart cart = shoppingCartlist.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
        }else {
            // 如果不存在，需要加入一条购物车数据
            if(shoppingCartlist != null && shoppingCartlist.size() == 0){
                // 判断本次添加到购物车的是菜品还是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();
                Long dishId = shoppingCartDTO.getDishId();
                if(setmealId != null){
                    Setmeal setmeal = setmealMapper.getById(setmealId);
                    shoppingCart.setAmount(setmeal.getPrice());
                    shoppingCart.setImage(setmeal.getImage());
                    shoppingCart.setName(setmeal.getName());
                }
                if(dishId != null){
                    Dish dish = dishMapper.getById(dishId);
                    shoppingCart.setName(dish.getName());
                    shoppingCart.setAmount(dish.getPrice());
                    shoppingCart.setImage(dish.getImage());
                }
                shoppingCart.setCreateTime(LocalDateTime.now());
                shoppingCart.setNumber(number);
                shoppingCartMapper.addShoppingCart(shoppingCart);
            }
        }
    }

    /**
     * 查看购物车
     * @return
     */
    @Override
    public List<ShoppingCart> query() {
        List<ShoppingCart> list = shoppingCartMapper.selectAll();
        return list;
    }

    /**
     * 清空购物车
     */
    @Override
    public void deleteAll() {
        shoppingCartMapper.deleteAll();
    }


    /**
     * 删除购物车中一个商品
     * @param shoppingCartDTO
     */
    @Override
    public void deleteShopping(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart cart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, cart);
        List<ShoppingCart> list = shoppingCartMapper.list(cart);
        if (list != null && list.size() > 0){
            ShoppingCart shoppingCart = list.get(0);
            Integer numbers = shoppingCart.getNumber() - number;
            if(numbers == 0){
                shoppingCartMapper.deleteById(shoppingCart);
            }else {
                shoppingCart.setNumber(numbers);
                shoppingCartMapper.updateNumberById(shoppingCart);
            }
        }
    }
}
