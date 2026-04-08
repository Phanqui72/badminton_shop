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
import com.mgr.api.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

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

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ORD_C')")
    @Transactional
    public ApiMessageDto<String> checkout(@Valid @RequestBody CheckoutForm form, BindingResult bindingResult, HttpServletRequest request) throws IOException {
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

        //VN Pay
        if (form.getPaymentMethod() == 2) {
            String paymentUrl = paymentService.createPayment(request, (long) total, order.getId().toString());
            return makeSuccessResponse(paymentUrl, "Please redirect to VNPAY for payment");
        }

        return makeSuccessResponse(null, "Order placed successfully");
    }
    @GetMapping(value = "/vnpay-callback", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> vnpayCallback(HttpServletRequest request) {
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String orderIdStr = request.getParameter("vnp_TxnRef");

        if (orderIdStr != null && !orderIdStr.isEmpty()) {
            Long orderId = Long.parseLong(orderIdStr);
            Order order = orderRepository.findById(orderId).orElse(null);

            if (order != null) {
                if ("00".equals(vnp_ResponseCode)) {
                    order.setStatus(1); // Giả sử 1 là đã thanh toán
                    orderRepository.save(order);
                    return makeSuccessResponse(null, "Payment success");
                } else {
                    order.setStatus(-1); // Giả sử -1 là thất bại/hủy
                    orderRepository.save(order);

                    // SỬA: Thay makeErrorResponse bằng cách khởi tạo DTO trực tiếp
                    // hoặc dùng phương thức phù hợp của BaseController
                    ApiMessageDto<String> response = new ApiMessageDto<>();
                    response.setResult(false);
                    response.setMessage("Payment failed with code: " + vnp_ResponseCode);
                    return response;
                }
            }
        }
        // SỬA tương tự cho lỗi không tìm thấy Order
        ApiMessageDto<String> response = new ApiMessageDto<>();
        response.setResult(false);
        response.setMessage("Order not found");
        return response;
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
