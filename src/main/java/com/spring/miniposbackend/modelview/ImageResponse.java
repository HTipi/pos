package com.spring.miniposbackend.modelview;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImageResponse {
	private Long id;
	private byte[] image;
	private Short version;
	
	public ImageResponse(Long id, byte[] image, Short version) {
		this.id=id;
		this.image=image;
		this.version=version;
	}
}
