package com.restaurant.restaurant.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.restaurant.entity.*;
import com.restaurant.restaurant.exception.CartEmptyException;
import com.restaurant.restaurant.exception.ResourceNotFoundException;
import com.restaurant.restaurant.payload.request.OrderRequest;
import com.restaurant.restaurant.payload.response.OrderDetailResponse;
import com.restaurant.restaurant.payload.response.OrderResponse;
import com.restaurant.restaurant.repository.AddressRepository;
import com.restaurant.restaurant.repository.CartRepository;
import com.restaurant.restaurant.repository.OrderRepository;
import com.restaurant.restaurant.repository.ProductRepository;
import com.restaurant.restaurant.service.OrderService;
import com.restaurant.restaurant.utils.Helper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    Helper helper;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired

    OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ResponseEntity placeOrder(OrderRequest orderRequest) throws JsonProcessingException {
        User user = helper.getUser();
        ShoppingCart cartProducts = cartRepository.findShoppingCartByUser(user);
        Double totalAmount = 0.00 ;
        Order order = new Order();
        ObjectMapper mapper = new ObjectMapper();
      //  order = orderRepository.findById(Long.valueOf(11)).get();
        if(cartProducts != null)
        {
            for(int i =0 ; i< cartProducts.getCartItems().size(); i++){
                int tempIndex = i;
                Product product = productRepository.findById(cartProducts.getCartItems().get(i).getProduct().getId()).orElseThrow(
                        () ->new ResourceNotFoundException("Product", "product_id", cartProducts.getCartItems().get(tempIndex).getProduct().getId()));

                Double  price   = helper.calculateProductPrice(product, cartProducts.getCartItems().get(i).getQuantity());
                totalAmount     += price;
                String productDetail = mapper.writeValueAsString(product);

                 // Setting Order details Items.
                OrderDetail orderDetails = new OrderDetail(null,product,productDetail,cartProducts.getCartItems().get(i).getQuantity(),product.getPrice(),price);
                order.addOrderDetail(orderDetails);

            }
            System.out.println(mapper.writeValueAsString(addressRepository.findById(orderRequest.getAddressId())));
            order.setUser(user);
            order.setOrderDate(helper.getCurrentDate());
            order.setOrderAmount(totalAmount);
            order.setAddressDetail(mapper.writeValueAsString(addressRepository.findById(orderRequest.getAddressId()).get()));
            order.setPaymentMethod(orderRequest.getPaymntMethod());
            order.setCreatedAt(Instant.now());
            if(orderRequest.getCouponId() == null){
                order.setTotalAmount(totalAmount);
            }
            orderRepository.save(order);

            System.out.println(order);
            if (modelMapper.getTypeMap(Order.class,OrderResponse.class) == null){

                modelMapper.createTypeMap(Order.class, OrderResponse.class).setConverter(orderResponseConverter);
            }

            OrderResponse orderResponse = this.modelMapper.map(order, OrderResponse.class);
            cartRepository.deleteById(cartProducts.getId());
            return  new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        }
        else{
            throw new CartEmptyException("Cart is empty.");
        }



    }

    public Converter<Order,OrderResponse> orderResponseConverter = new Converter<Order, OrderResponse>() {
        @Override
        public OrderResponse convert(MappingContext<Order, OrderResponse> mappingContext) {
            OrderResponse orderResponse = new OrderResponse();
            Address address = null;
            Product product;
            orderResponse.setOrderAmount(mappingContext.getSource().getOrderAmount());
            orderResponse.setTotalAmount(mappingContext.getSource().getTotalAmount());
            orderResponse.setOrderDate(mappingContext.getSource().getOrderDate());
            orderResponse.setOrderNo(mappingContext.getSource().getOrderNo());
            orderResponse.setId(mappingContext.getSource().getId());
            orderResponse.setPaymentMethod(mappingContext.getSource().getPaymentMethod());
            orderResponse.setAddress((Address) convertObject(mappingContext.getSource().getAddressDetail(),Address.class));
            orderResponse.setDiscountAmt(mappingContext.getSource().getDiscountAmt());
            orderResponse.setOrderDetails(mappingContext.getSource().getOrderDetails().stream().map(orderDetail ->
                    new OrderDetailResponse(orderDetail.getId(), (Product) convertObject(
                            orderDetail.getProductDetail(),Product.class),
                            orderDetail.getQuantity(),
                            orderDetail.getPrice(),
                            orderDetail.getTotal())
            ).collect(Collectors.toList()));

            return orderResponse;
        }
    };

    public Object convertObject(String jsonProduct, Class<?> objectClass) {
        ObjectMapper objMapper = new ObjectMapper();
        Object object;
        try {
            object = objMapper.readValue(jsonProduct, objectClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return object;
    }

}
