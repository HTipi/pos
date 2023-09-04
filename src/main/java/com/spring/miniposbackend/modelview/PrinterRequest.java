package com.spring.miniposbackend.modelview;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PrinterRequest {
	
	private String ip;
	private String code;
	private boolean paymentPrinter;
	private String name;
	private boolean separatePrinter;
	private String type;

}
