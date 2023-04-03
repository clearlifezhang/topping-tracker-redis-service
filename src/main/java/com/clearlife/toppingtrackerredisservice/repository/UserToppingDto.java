package com.clearlife.toppingtrackerredisservice.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserToppingDto implements Serializable {
    private String useremail;
    private Set<String> toppingNames;
}
