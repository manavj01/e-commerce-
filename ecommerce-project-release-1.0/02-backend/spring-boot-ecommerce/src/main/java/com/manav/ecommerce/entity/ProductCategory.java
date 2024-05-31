package com.manav.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Locked;
import lombok.Setter;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.Set;

@Entity
@Table(name = "product_category")
@Getter
@Setter
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Product> products;

}
