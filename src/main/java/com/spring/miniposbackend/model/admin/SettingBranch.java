package com.spring.miniposbackend.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.miniposbackend.model.AuditModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "setting_branches")
@Setter
@Getter
@DynamicUpdate
public class SettingBranch extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "setting_value", nullable = false,length = 32)
    private String settingValue;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "setting_id", nullable = false)
    @JsonIgnore
    Setting setting;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    Branch branch;

    @Column(name = "enable", nullable = false)
    @ColumnDefault("false")
    private boolean enable;

    public String getSetting_name() {
        return setting.getName();
    }
    public Integer getSequenceNum() {
        return setting.getSequence();
    }

}
