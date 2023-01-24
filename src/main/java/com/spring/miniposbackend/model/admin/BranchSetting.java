package com.spring.miniposbackend.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "branch_settings", uniqueConstraints = @UniqueConstraint(columnNames = {"setting_id","branch_id"}))
@Setter
@Getter
@DynamicUpdate
public class BranchSetting extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setting_id", nullable = false)
    @JsonIgnore
    Setting setting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    Branch branch;

    @Column(name = "value", nullable = false)
    private String settingValue;
    
	@Column(name = "enable", nullable = false)
    @ColumnDefault("true")
    private boolean enable;

    public String getSettingCode() {
        return setting.getCode();
    }
    public String getSettingName() {
        return setting.getName();
    }
    public Integer getSequenceNumber() {
        return setting.getSequence();
    }
    public String getDefaultValue() {
    	return setting.getDefaultValue();
    }
    public String getSettingType() {
    	return setting.getType();
    }

}
