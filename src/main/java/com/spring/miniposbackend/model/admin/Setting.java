package com.spring.miniposbackend.model.admin;

import com.spring.miniposbackend.model.AuditModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "settings")
@Setter
@Getter
@DynamicUpdate
public class Setting extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false,length = 32)
    private String code;

    @Column(name = "name", nullable = false,length = 128)
    private String name;
    
    @Column(name = "type", nullable = true,length = 128)
    private String type;
    
    @Column(name = "default_value", nullable = false,length = 128)
    private String defaultValue;

    @Column(name = "sequence", nullable = false)
    private Integer sequence=1;
    

    @Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;


}
