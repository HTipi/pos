package com.spring.miniposbackend.modelview.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryDetail {
	private Long id;
	private String name;
	private Double total;

	public SummaryDetail() {
		id = null;
		name = "";
		total = null;
	}

	public SummaryDetail(Long id, String name, Double dailySaleAmout) {
		this.id = id;
		this.name = name;
		this.total = dailySaleAmout;
	}
}
