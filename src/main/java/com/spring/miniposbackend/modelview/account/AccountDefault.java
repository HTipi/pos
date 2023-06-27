package com.spring.miniposbackend.modelview.account;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountDefault {

	private String branchName;
	private String corporateName;
	private String address;
	private int branchId;
	private int corporateId;
	private byte[] logo;
	
}
