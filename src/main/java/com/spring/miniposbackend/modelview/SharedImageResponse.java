package com.spring.miniposbackend.modelview;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharedImageResponse {
	private UUID id;
	private String name;
	private String type;
	private byte[] image;

	public SharedImageResponse(UUID id, String name, String type, byte[] image) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.image = image;
	}
}
