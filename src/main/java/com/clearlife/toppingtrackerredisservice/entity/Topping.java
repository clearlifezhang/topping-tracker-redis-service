package com.clearlife.toppingtrackerredisservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Topping {
    private String name;
    private int totalCount;
    private Set<String> uniqueUsers;

    public Topping(String name) {
        this.name = name;
        this.totalCount = 0;
        this.uniqueUsers = new HashSet<>();
    }

    public void updateMetrics(String userEmail) {
        totalCount++;
        uniqueUsers.add(userEmail);
    }
}
