package com.mgr.api.mapper;

import com.mgr.api.dto.order.OrderDto;
import com.mgr.api.dto.order.OrderItemDto;
import com.mgr.api.model.Order;
import com.mgr.api.model.OrderItem;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {ProductMapper.class, AddressMapper.class})
public interface OrderMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "product", target = "product")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "price", target = "price")
    OrderItemDto fromEntityToOrderItemDto(OrderItem item);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "paymentMethod", target = "paymentMethod")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "items", target = "items")
    @Mapping(target = "createdDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(order.getCreatedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    @Mapping(target = "modifiedDate", expression = "java(com.mgr.api.utils.DateUtils.formatDate(order.getModifiedDate(), com.mgr.api.constant.MgrConstant.DATE_TIME_FORMAT))")
    OrderDto fromEntityToOrderDto(Order order);

    @IterableMapping(elementTargetType = OrderDto.class)
    List<OrderDto> fromEntityListToDtoList(List<Order> list);
}
