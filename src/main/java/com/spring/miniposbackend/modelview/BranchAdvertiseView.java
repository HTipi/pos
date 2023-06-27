package com.spring.miniposbackend.modelview;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BranchAdvertiseView {

 private Long id;
 private String name;
 private Integer sortOrder;
 private boolean enable;
 private Optional<Integer> sortorder;
 
}