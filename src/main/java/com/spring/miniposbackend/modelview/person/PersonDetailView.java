package com.spring.miniposbackend.modelview.person;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.customer.Person;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonDetailView{

	@JsonIgnore
	private Person person;
	private byte[] image;

	
	public PersonDetailView(Person person,byte[] image) {
		this.person = person;
		this.image = image;
	}
	
	public PersonDetailView() {
		// TODO Auto-generated constructor stub
	}

	
	public String getLastName() {
		if(person == null)
			return "";
		return person.getName();
	}
	public String getNameKh() {
		if(person == null)
			return "";
		return person.getNameKh();
	}
	public int getSexId()
	{	if(person == null)
		return 0;
		return person.getSexId();
	}
	public Date getDateOfBirth() {
		if(person == null)
			return null;
		return person.getDateOfBirth();
	}
	
	public String getPrimaryphone() {
		if(person == null)
			return null;
		return person.getPrimaryPhone();
	}
	
	public String getSecondaryphone() {
		if(person == null)
			return null;
		return person.getSecondaryPhone();
	}
	
	public byte[] getImage() {
		return image;
		
	}
	
	public String getFirstName() {
		if(person == null)
			return "";
		return person.getFirstName();
	}
	
	

}
