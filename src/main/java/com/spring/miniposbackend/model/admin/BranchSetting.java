package com.spring.miniposbackend.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import lombok.Getter;
import lombok.Setter;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "setting_id", nullable = false)
    @JsonIgnore
    Setting setting;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    Branch branch;

    @Column(name = "value", nullable = false,length = 32)
    private String settingValue;
    

    public String getSettingCode() {
        return setting.getCode();
    }
    public String getSettingName() {
        return setting.getName();
    }
    public Integer getSequenceNumber() {
        return setting.getSequence();
    }

}
