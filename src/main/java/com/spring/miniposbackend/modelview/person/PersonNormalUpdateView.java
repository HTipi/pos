package com.spring.miniposbackend.modelview.person;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonNormalUpdateView {
		
	private String firstName;
	private String lastName;
	private String nameKh;
	private int sexId;
	private Date dateOfBirth;

}