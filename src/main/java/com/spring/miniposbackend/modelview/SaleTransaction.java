package com.spring.miniposbackend.modelview;


import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

@Setter @Getter
public class SaleTransaction{


    private Long id;
	

    private Date valueDate;
	

    private BigDecimal price;
	

    private Short quantity;
	

    private Double discount;

    private Double total;

    private boolean reverse;

    private Date reverseDate;


    private String receiptNumber;
	

    private String branchName;
	

    private String userName;

    private String itemName;
	

}
