package kitchenpos.application;

import kitchenpos.domain.Menu;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderLineItem;
import kitchenpos.domain.OrderLineItems;
import kitchenpos.domain.OrderRepository;
import kitchenpos.dto.OrderLineItemRequest;
import kitchenpos.dto.OrderRequest;
import kitchenpos.dto.OrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuService menuService;

    public OrderService(
            final OrderRepository orderRepository,
            final MenuService menuService
    ) {
        this.orderRepository = orderRepository;
        this.menuService = menuService;
    }

    @Transactional
    public OrderResponse create(final OrderRequest orderRequest) {
        validateExistsOrderLineItems(orderRequest);
        final Order savedOrder = Order.of(orderRequest.getOrderTableId(), findOrderLineItems(orderRequest.getOrderLineItems()));
        return OrderResponse.from(orderRepository.save(savedOrder));
    }

    public List<OrderResponse> list() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse changeOrderStatus(final Long orderId, final OrderRequest orderRequest) {
        final Order savedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
        savedOrder.changeOrderStatus(orderRequest.getOrderStatus());
        return OrderResponse.from(savedOrder);
    }

    private void validateExistsOrderLineItems(OrderRequest orderRequest) {
        List<Long> menuIds = orderRequest.getMenuIds();
        if (orderRequest.getOrderLineItems().size() != menuService.countByIdIn(menuIds)) {
            throw new IllegalArgumentException("등록하려는 주문 항목의 매뉴가 등록되어있지 않습니다.");
        }
    }

     private OrderLineItems findOrderLineItems(List<OrderLineItemRequest> orderLineItemRequests) {
        return OrderLineItems.from(orderLineItemRequests.stream()
                .map(orderLineItemRequest -> {
                    Menu menu = menuService.findMenuById(orderLineItemRequest.getMenuId());
                    return OrderLineItem.of(menu, orderLineItemRequest.getQuantity());
                })
                .collect(Collectors.toList()));
    }
}
