package kitchenpos.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import kitchenpos.order.domain.Order;

public class OrderResponse {
	private Long id;
	private Long orderTableId;
	private String orderStatus;
	private LocalDateTime orderedTime;
	private List<OrderLineItemResponse> orderLineItems;

	public OrderResponse() {
	}

	public OrderResponse(Long id, Long orderTableId, String orderStatus, LocalDateTime orderedTime,
		List<OrderLineItemResponse> orderLineItems) {
		this.id = id;
		this.orderTableId = orderTableId;
		this.orderStatus = orderStatus;
		this.orderedTime = orderedTime;
		this.orderLineItems = orderLineItems;
	}

	public Long getId() {
		return id;
	}

	public Long getOrderTableId() {
		return orderTableId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public LocalDateTime getOrderedTime() {
		return orderedTime;
	}

	public List<OrderLineItemResponse> getOrderLineItems() {
		return orderLineItems;
	}

	public static OrderResponse of(Order order) {
		List<OrderLineItemResponse> orderLineItemResponses = order.getOrderLineItems().stream()
			.map(OrderLineItemResponse::of)
			.collect(Collectors.toList());

		return new OrderResponse(order.getId()
			, order.getOrderTableId()
			, order.getOrderStatusName()
			, order.getOrderedTime()
			, orderLineItemResponses);
	}
}
