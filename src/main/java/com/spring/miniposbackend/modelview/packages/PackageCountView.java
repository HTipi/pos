package com.spring.miniposbackend.modelview.packages;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PackageCountView {
	
	private long packageId;
	private long saleId;
	
	public PackageCountView(long packageId,long saleId) {
		this.packageId = packageId;
		this.saleId = saleId;
	}

}
