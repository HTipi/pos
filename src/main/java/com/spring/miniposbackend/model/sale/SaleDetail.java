//package com.spring.miniposbackend.model.sale;
//
//import java.util.Date;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.ColumnDefault;
//import org.hibernate.annotations.DynamicUpdate;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.spring.miniposbackend.model.AuditModel;
//import com.spring.miniposbackend.model.admin.Branch;
//import com.spring.miniposbackend.model.admin.Item;
//import com.spring.miniposbackend.model.admin.User;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "items")
//@Setter @Getter
//@DynamicUpdate
//public class SaleDetail extends AuditModel {
//
//	private static final long serialVersionUID = 1L;
//	
//	@Id
//    @GeneratedValue(strategy= GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Long id;
//	
//	@Column(name = "value_date", nullable = false)
//    private Date valueDate;
//	
//	@Column(name = "price", nullable = false, length = 10, precision = 2)
//    private Float price;
//	
//	@Column(name = "price", nullable = false)
//    private Integer quantity;
//	
//	@Column(name = "discount", nullable = false, length = 10, precision = 2)
//    private Float discount;
//	
//	@Column(name = "total", nullable = false, length = 10, precision = 2)
//    private Float total;
//	
//	@Column(name = "reverse", nullable = false)
//    @ColumnDefault("false")
//    private boolean reverse;
//	
//	@Column(name = "reverse_date")
//    private Date reverseDate;
//	
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "sale_id", nullable = false)
//    @JsonIgnore
//    private Sale sale;
//	
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "branch_id", nullable = false)
//    @JsonIgnore
//    private Branch branch;
//	
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", nullable = false)
//    @JsonIgnore
//    private User user;
//	
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "item_id", nullable = false)
//    @JsonIgnore
//    private Item item;
//	
//	public String getItemCode() {
//		return item.getCode();
//	}
//	
//	public String getItemName() {
//		return item.getName();
//	}
//	
//	public String getItemNameKh() {
//		return item.getNameKh();
//	}
//}
