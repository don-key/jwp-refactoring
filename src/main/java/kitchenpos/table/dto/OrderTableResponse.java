package kitchenpos.table.dto;

import kitchenpos.table.domain.OrderTable;

import java.util.Objects;

public class OrderTableResponse {

    private Long id;
    private Long tableGroupId;
    private int numberOfGuests;
    private boolean empty;

    private OrderTableResponse(Long id, int numberOfGuests, boolean empty) {
        this.id = id;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    private OrderTableResponse(Long id, Long tableGroupId, int numberOfGuests, boolean empty) {
        this.id = id;
        this.tableGroupId = tableGroupId;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public static OrderTableResponse from(OrderTable orderTable) {
        if (Objects.isNull(orderTable.getTableGroup())) {
            return new OrderTableResponse(orderTable.getId(), orderTable.getNumberOfGuests(), orderTable.isEmpty());

        }
        return new OrderTableResponse(orderTable.getId(),
                orderTable.getTableGroup().getId(),
                orderTable.getNumberOfGuests(),
                orderTable.isEmpty());
    }

    public Long getId() {
        return id;
    }

    public Long getTableGroupId() {
        return tableGroupId;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }
}
