package com.spring.miniposbackend.modelview;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddOnItem {
	private String type;
	private List<Long> subItems;
}
