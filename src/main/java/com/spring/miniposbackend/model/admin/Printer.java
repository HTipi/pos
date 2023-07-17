package com.spring.miniposbackend.model.admin;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "printers")
@Getter
@Setter
public class Printer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "code", nullable = false,length = 32)
    private String code;
	
	@Column(name = "ip", nullable = false,length = 14)
    private String ip;
	
	@Column(name = "name", nullable = true,length = 14)
    private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", nullable = false)
	@JsonIgnore
	Branch branch;
	
	@JsonIgnore
	@OneToMany(mappedBy = "printer", fetch = FetchType.EAGER)
	List<PrinterItemType> printerItemTypes;

	@Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;
	
	@Column(name = "paymentPrinter", nullable = false)
    @ColumnDefault("false")
	private boolean paymentPrinter;
	
	@Column(name = "separatePrinter", nullable = true)
    @ColumnDefault("false")
	private boolean separatePrinter;
	
	@Column(name = "type", nullable = true, length = 1)
    private String type;
	
	public List<Integer> getItemTypes(){
		if(printerItemTypes == null)
			return new ArrayList<Integer>();
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < printerItemTypes.size(); i++) {
			tmp.add(printerItemTypes.get(i).getItemTypeId());
		}
		return tmp;
	}
}
