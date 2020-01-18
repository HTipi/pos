package com.spring.miniposbackend.model.admin;

import com.spring.miniposbackend.model.AuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "social")
@EqualsAndHashCode(callSuper = false)
@Data
@DynamicUpdate
public class Social extends AuditModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "image", nullable = true)
    private String image;

    @Column(name = "order", nullable = false)
    private Integer order;

    @Column(name = "enable", nullable = false)
    @ColumnDefault("true")
    private Boolean enable;

    public Date getUpdatedAt() {
        return updatedAt;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
}
