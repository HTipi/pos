package com.spring.miniposbackend.modelview.transaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GivePointRequest {
	private String remark;
	private short point;
}
