package com.restaurant.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product extends AbstractClass {

    private static final long serialVersionUID = -2174786072818503524L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    private String name;
    private String description;
    private String slug;
    private String menuType;
    private Double price;
    private Double discount;
    private String discountType;
    private String status;
//    @JsonIgnore
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private Set<Cart> cartProducts = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<CartItems> cartItems = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", slug='" + slug + '\'' +
                ", menuType='" + menuType + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", discountType='" + discountType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
