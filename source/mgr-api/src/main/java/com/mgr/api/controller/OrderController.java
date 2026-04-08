package com.mgr.api.controller;

import com.mgr.api.constant.MgrConstant;
import com.mgr.api.dto.ApiMessageDto;
import com.mgr.api.dto.ErrorCode;
import com.mgr.api.dto.ResponseListDto;
import com.mgr.api.dto.order.OrderDto;
import com.mgr.api.exception.BadRequestException;
import com.mgr.api.exception.NotFoundException;
import com.mgr.api.form.order.CheckoutForm;
import com.mgr.api.mapper.OrderMapper;
import com.mgr.api.model.*;
import com.mgr.api.model.criteria.OrderCriteria;
import com.mgr.api.repository.*;
import com.mgr.api.repository.address.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class OrderController extends ABasicController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping(value = "/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ORD_C')")
    @Transactional
    public ApiMessageDto<String> checkout(@Valid @RequestBody CheckoutForm form, BindingResult bindingResult) {
        Long accountId = getCurrentUser();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found", ErrorCode.ACCOUNT_ERROR_NOT_FOUND));

        Cart cart = cartRepository.findByAccount(account)
                .orElseThrow(() -> new BadRequestException("Cart is empty", ErrorCode.PRODUCT_ERROR_NOT_FOUND));

        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Cart is empty", ErrorCode.PRODUCT_ERROR_NOT_FOUND);
        }

        Address address = addressRepository.findById(form.getAddressId())
                .orElseThrow(() -> new NotFoundException("Address not found", ErrorCode.USER_ERROR_NOT_FOUND));

        // Create Order
        Order order = new Order();
        order.setAccount(account);
        order.setAddress(address);
        order.setPaymentMethod(form.getPaymentMethod());
        order.setStatus(MgrConstant.STATUS_PENDING);

        double total = 0;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            
            // Stock deduction
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new BadRequestException("Product " + product.getName() + " out of stock", ErrorCode.PRODUCT_ERROR_NOT_FOUND);
            }
            product.setStock(product.getStock() - cartItem.getQuantity());
            
            order.getItems().add(orderItem);
            total += orderItem.getPrice() * orderItem.getQuantity();
        }

        order.setTotalPrice(total);
        orderRepository.save(order);

        // Clear Cart
        cart.getItems().clear();
        cartRepository.save(cart);

        return makeSuccessResponse(null, "Order placed successfully");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ORD_M')")
    public ApiMessageDto<ResponseListDto<OrderDto>> list(OrderCriteria orderCriteria, Pageable pageable) {
        Page<Order> page = orderRepository.findAll(orderCriteria.getSpecification(), pageable);
        ResponseListDto<OrderDto> listDto = new ResponseListDto<OrderDto>(
                orderMapper.fromEntityListToDtoList(page.getContent()),
                Long.valueOf(page.getTotalElements()),
                Integer.valueOf(page.getTotalPages())
        );
        return makeSuccessResponse(listDto, "Get list order success");
    }

    @GetMapping(value = "/my-orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ORD_L')")
    public ApiMessageDto<ResponseListDto<OrderDto>> listMyOrders(Pageable pageable) {
        OrderCriteria criteria = new OrderCriteria();
        criteria.setAccountId(getCurrentUser());
        
        Page<Order> page = orderRepository.findAll(criteria.getSpecification(), pageable);
        ResponseListDto<OrderDto> listDto = new ResponseListDto(
                orderMapper.fromEntityListToDtoList(page.getContent()),
                page.getTotalElements(),
                page.getTotalPages()
        );
        return makeSuccessResponse(listDto, "Get orders success");
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<OrderDto> getOrder(@PathVariable("id") Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found", ErrorCode.PRODUCT_ERROR_NOT_FOUND));

        if (!order.getAccount().getId().equals(getCurrentUser()) && !isSuperAdmin()) {
            throw new NotFoundException("Order not found", ErrorCode.PRODUCT_ERROR_NOT_FOUND);
        }

        return makeSuccessResponse(orderMapper.fromEntityToOrderDto(order), "Get order success");
    }
}
