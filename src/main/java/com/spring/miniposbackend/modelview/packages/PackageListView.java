package com.spring.miniposbackend.modelview.packages;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PackageListView {
	   private long photoId;
	   private String name;
	   private String nameKh;
	   private double price;
	   private double discount;
	   private double qty;
	   private Long itemBranchId;
	   private Date expiryDate;


	}


