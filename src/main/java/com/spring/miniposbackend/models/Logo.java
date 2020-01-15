package com.spring.miniposbackend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;

@Entity
@Table(name = "logos")
@EqualsAndHashCode
@Data
public class Logo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "coporate_id")
    private Corporate corporate;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade =CascadeType.ALL)
    @JoinColumn(name = "branch_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    @Column(name = "image", nullable = false)
    private String image;

}
