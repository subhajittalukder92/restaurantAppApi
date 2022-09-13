package com.restaurant.restaurant.service.impl;

import com.restaurant.restaurant.entity.CartItems;
import com.restaurant.restaurant.entity.Product;
import com.restaurant.restaurant.entity.ShoppingCart;

import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.exception.ResourceNotFoundException;
import com.restaurant.restaurant.payload.response.CartProductReponse;
import com.restaurant.restaurant.payload.response.CartResponse;
import com.restaurant.restaurant.repository.CartItemRepository;
import com.restaurant.restaurant.repository.CartRepository;
import com.restaurant.restaurant.repository.ProductRepository;
import com.restaurant.restaurant.service.CartService;
import com.restaurant.restaurant.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.chrono.ChronoZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository ;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Helper helper;
    @Override
    public ResponseEntity<?> findShoppingCartByUserId(User user) {

      ShoppingCart cartProducts = cartRepository.findShoppingCartByUser(user);
        List cartItems = new ArrayList<>();
        if(cartProducts != null){

            final Double[] totalPrice = {0.00};
            cartProducts.getCartItems().forEach(cartItem -> {
                Product product = productRepository.findById(cartItem.getProduct().getId()).orElseThrow(() ->new ResourceNotFoundException("Product", "product_id", cartItem.getId()));

                Double  price   = helper.calculateProductPrice(product, cartItem.getQuantity());
                totalPrice[0] += price;
                CartProductReponse cartProductReponse = new CartProductReponse(product, cartItem.getQuantity(), price);
                System.out.println(cartItems);
                cartItems.add(cartProductReponse);

            });
            CartResponse cartResponse = new CartResponse(cartItems, totalPrice[0]);
            return  new ResponseEntity<>(cartResponse, HttpStatus.OK) ;
        }

        return  new ResponseEntity<>(new CartResponse(cartItems, 0.00), HttpStatus.OK) ;

    }

    @Override
    public ResponseEntity<?> addItem(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->new ResourceNotFoundException("Product", "product_id", productId));
        User user       = helper.getUser();
        ShoppingCart cart = cartRepository.findShoppingCartByUser(user);

        if(cart != null){
            Boolean productExist = false;
            List<CartItems> cartItems = cart.getCartItems();

            for(int i =0 ; i< cartItems.size();i++){
                if(cartItems.get(i).getProduct().equals(product)){
                    System.out.println("Here: "+ cartItems.get(i).getQuantity() + 1);
                    cartItems.get(i).setQuantity(cartItems.get(i).getQuantity() + 1);
                    cartRepository.save(cart);
                    productExist = true;
                    break;
                }
            }
            if(!productExist){
                cartItems.add(new CartItems(null,cart,product,1)) ;
                cartRepository.save(cart);
            }
            System.out.println(cart);



        }else{
            CartItems cartItem = new CartItems();
            ShoppingCart newShoppping = new ShoppingCart();
            newShoppping.setUser(user);
            newShoppping.setCreatedAt(Instant.now());

            cartItem.setShoppingCart(newShoppping);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);

            newShoppping.addItems(cartItem);

            System.out.println("New Cart-"+newShoppping);
            cartRepository.save(newShoppping);

        }
        return findShoppingCartByUserId(user);
    }

    @Override
    public ResponseEntity<?> removeItem(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->new ResourceNotFoundException("Product", "product_id", productId));
        User user       = helper.getUser();
        ShoppingCart cart = cartRepository.findShoppingCartByUser(user);

        if(cart != null){
            Boolean productExist = false;
            List<CartItems> cartItems = cart.getCartItems();

            for(int i =0 ; i< cartItems.size();i++){
                if(cartItems.get(i).getProduct().equals(product)){
                    if(cartItems.get(i).getQuantity() - 1 > 0){
                        cartItems.get(i).setQuantity(cartItems.get(i).getQuantity() - 1);
                        cartRepository.save(cart);

                    }else{
                        cart.getCartItems().remove(cartItems.get(i));
                       // cartItems.remove();

                        if(cartItems.size() == 0){
                            cartRepository.delete(cartRepository.findById(cart.getId()).get());
                        }else{

                            cartRepository.save(cart);
                            System.out.println("Cart==== "+cart);
                        }
                    }
                    productExist = true;
                    break;


                }
            }
            if(!productExist){
              throw  new ResourceNotFoundException("Cart", "product_id", productId);
            }
            System.out.println(cart);



        }
        return findShoppingCartByUserId(user);
    }
}
