//package com.spring.miniposbackend.model.admin;
//
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import org.hibernate.annotations.ColumnDefault;
//import org.hibernate.annotations.DynamicUpdate;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.spring.miniposbackend.model.AuditModel;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "promotions")
//@Setter @Getter
//@DynamicUpdate
//public class Promotion extends AuditModel{
//	
//	private static final long serialVersionUID = 1L;
//	@Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Integer id;
//	
//	@Column(name = "name", nullable = false,length = 128)
//    private String name;
//	
//	@Column(name = "name_kh", nullable = false,length = 128)
//    private String nameKh;
//	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "corporate_id",nullable = false)
//    @JsonIgnore
//    private Corporate corporate;
//	
//	@Column(name = "enable", nullable = false)
//    @ColumnDefault("true")
//    private boolean enable = true;
//}
