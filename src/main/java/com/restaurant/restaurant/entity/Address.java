package com.restaurant.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurant.restaurant.payload.response.OrderResponse;
import lombok.*;
import org.modelmapper.spi.DestinationSetter;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address extends AbstractClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String road;
    private String landmark;
    private String postOffice;
    private String district;
    private String pin;
    private String contactNo;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", road='" + road + '\'' +
                ", landmark='" + landmark + '\'' +
                ", postOffice='" + postOffice + '\'' +
                ", district='" + district + '\'' +
                ", pin='" + pin + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }
}
