package com.spring.miniposbackend.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "setting")
@Setter
@Getter
@DynamicUpdate
public class Setting extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false,length = 32)
    private String code;

    @Column(name = "name", nullable = false,length = 128)
    private String name;

    @Column(name = "description", nullable = false,length = 128)
    private String description;

    @Column(name = "value_type", nullable = false,length = 128)
    private String valueType;

    @Column(name = "sequence", nullable = false)
    private Integer sequence=1;

    @Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;


}
