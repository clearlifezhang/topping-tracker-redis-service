package com.clearlife.toppingtrackerredisservice.service;

import com.clearlife.toppingtrackerredisservice.entity.Topping;
import com.clearlife.toppingtrackerredisservice.repository.UserToppingDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ToppingService {

    private final RedisTemplate<String, Object> redisTemplate;

    public ToppingService(@Qualifier("myRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addUserToppings(UserToppingDto userToppingDto) {
        Set<String> toppingNames = userToppingDto.getToppingNames();
        for (String toppingname : toppingNames) {
            if (redisTemplate.hasKey(toppingname)) {
                Topping topping = (Topping) redisTemplate.opsForValue().get(toppingname);
                topping.updateMetrics(userToppingDto.getUseremail());
                redisTemplate.opsForValue().set(toppingname, topping);
            } else {
                Topping newTopping = new Topping(toppingname);
                newTopping.updateMetrics(userToppingDto.getUseremail());
                redisTemplate.opsForValue().set(toppingname, newTopping);
            }
        }
    }

    public List<Topping> getAllToppings() {
        Set<String> keys = redisTemplate.keys("*");
        List<Topping> res = new ArrayList<>();
        for (String ky : keys) {
            Topping topping = (Topping) redisTemplate.opsForValue().get(ky);
            res.add(topping);
        }
        return res;
    }

    public Topping getToppingByName(String toppingName) {
        Topping topping = (Topping) redisTemplate.opsForValue().get(toppingName);
        return topping;
    }
}
