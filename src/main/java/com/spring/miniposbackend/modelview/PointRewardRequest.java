package com.spring.miniposbackend.modelview;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PointRewardRequest {
	private List<Long> itemBranchId;
    private Short point;
    private Short reward;
}
