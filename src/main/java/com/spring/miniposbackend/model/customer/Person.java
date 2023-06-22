package com.spring.miniposbackend.model.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import com.spring.miniposbackend.model.admin.Sex;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "persons")
@Getter
@Setter
@DynamicUpdate
public class Person extends AuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "firstname", nullable = true, length = 128)
	private String firstName;

	@Column(name = "name", nullable = false, length = 128)
	private String name;

	@Column(name = "name_kh", nullable = false, length = 128)
	private String nameKh;

	@Column(name = "date_of_birth", nullable = true)
	private Date dateOfBirth;

	@NotNull(message = "First PhoneNumber cannot be empty")
	@Column(name = "primary_phone", nullable = false)
	@Length(max = 10, message = "PhoneNumber should be atleast 10 number long")
	private String primaryPhone;

	@Column(name = "secondary_phone")
	@Length(max = 10, message = "PhoneNumber should be atleast 10 number long")
	private String secondaryPhone;

	@Column(name = "photo", length = 64, nullable = true)
	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sex_id", nullable = true)
	@JsonIgnore
	private Sex sex;

	@Column(name = "enable", nullable = false)
	private boolean enable = true;

	public int getSexId() {

		return sex.getId();
	}

}