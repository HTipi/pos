package com.spring.miniposbackend.modelview.packages;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PackageView {
	
	private long packgeId;
	private long saleId;
	private long photoId;
	private String packageName;
	private String packageKh;
	private List<PackageListView> packages;
	

}
