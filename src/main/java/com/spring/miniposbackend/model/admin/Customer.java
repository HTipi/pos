//package com.spring.miniposbackend.model.admin;
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
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.spring.miniposbackend.model.AuditModel;
//
//import lombok.Getter;
//import lombok.Setter;
//
//
//@Entity
//@Table(name = "customers")
//@Getter @Setter
//public class Customer extends AuditModel {
//
//
//	private static final long serialVersionUID = 1L;
//
//	@Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private int id;
//
//    @Column(name = "name", nullable = false, length = 128)
//    private String name;
//
//    @Column(name = "name_kh", nullable = false)
//    private String nameKh;
//    
//    @Column(name = "sex", nullable = false,length = 1)
//    private String sex;
//    
//    @Column(name = "primary_phone", nullable = false)
//    private String primaryPhone;
//    
//    @Column(name = "secondary_phone", nullable = true)
//    private String secondaryPhone;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "corporate_id",nullable = false)
//    @JsonIgnore
//    private Corporate corporate; 
//    
//    @Column(name = "enable", nullable = false)
//    private boolean enable = true;
//}