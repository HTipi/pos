package com.spring.miniposbackend.modelview;


import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter @Getter
public class SaleTransaction{


    private Long id;
	

    private Date valueDate;
	

    private Double price;
	

    private Short quantity;
	

    private Double discount;

    private Double total;

    private boolean reverse;

    private Date reverseDate;


    private String receiptNumber;
	

    private String branchName;
	

    private String userName;

    private String itemName;
    
    private Long itemId;
    
    private String seatName;
	

}
