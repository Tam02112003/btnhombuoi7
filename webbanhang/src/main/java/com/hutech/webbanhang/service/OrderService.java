package com.hutech.webbanhang.service;

import com.hutech.webbanhang.model.CartItem;
import com.hutech.webbanhang.model.Order;
import com.hutech.webbanhang.model.OrderDetail;
import com.hutech.webbanhang.repository.OrderDetailRepository;
import com.hutech.webbanhang.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartService cartService; // Assuming you have a CartService
    @Transactional
    public Order createOrder(String customerName,String customerAddress, String customerPhone, String customerNote , List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerAddress(customerAddress);
        order.setCustomerPhone(customerPhone);
        order.setCustomerNote(customerNote);
        order = orderRepository.save(order);
        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }
        // Optionally clear the cart after order placement
        cartService.clearCart();
        return order;
    }
}