package com.restaurant.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ShoppingCart extends AbstractClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_cart_id")
    private List<CartItems> cartItems = new ArrayList<>();

    public void add(CartItems items){
        this.cartItems.add(items);
    }
    public void addItems(CartItems items) {
        this.cartItems.add(items);
    }
    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ",user="+user+
                ", cartItems=" + cartItems +
                '}';
    }

}
