package com.clearlife.toppingtrackerredisservice.controller;

import com.clearlife.toppingtrackerredisservice.entity.Topping;
import com.clearlife.toppingtrackerredisservice.repository.UserToppingDto;
import com.clearlife.toppingtrackerredisservice.service.ToppingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/redisapi")
public class ToppingController {
    private final ToppingService myservice;

    public ToppingController(ToppingService myservice) {
        this.myservice = myservice;
    }

    @PostMapping("/saveUserToppings")
    public ResponseEntity<UserToppingDto> addUserToppings(@RequestBody UserToppingDto request) {
        myservice.addUserToppings(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    @GetMapping("/getTotalCount/{toppingName}")
    public int getTotalCountForTopping(@PathVariable String toppingName) {
        return myservice.getToppingByName(toppingName).getTotalCount();
    }

    @GetMapping("/uniqueUserCount/{toppingName}")
    public int getUniqueUserCountForTopping(@PathVariable String toppingName) {
        return myservice.getToppingByName(toppingName).getUniqueUsers().size();
    }

    @GetMapping("/mostPopularToppings")
    public List<String> getMostPopularToppings() {
        List<String> ret = new ArrayList<>();
        List<Topping> toppings = myservice.getAllToppings();
        int maxval = 0;
        for (Topping topping : toppings) {
            if (topping.getTotalCount() > maxval) {
                maxval = topping.getTotalCount();
            }
        }
        for (Topping topping : toppings) {
            if (topping.getTotalCount() == maxval) {
                ret.add(topping.getName());
            }
        }
        return ret;
    }

    @GetMapping("/leastPopularToppings")
    public List<String> getLeastPopularToppings() {
        List<String> ret = new ArrayList<>();
        List<Topping> toppings = myservice.getAllToppings();
        int minval = Integer.MAX_VALUE;
        for (Topping topping : toppings) {
            if (topping.getTotalCount() < minval) {
                minval = topping.getTotalCount();
            }
        }
        for (Topping topping : toppings) {
            if (topping.getTotalCount() == minval) {
                ret.add(topping.getName());
            }
        }
        return ret;
    }
}
