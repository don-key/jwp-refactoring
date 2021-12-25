package kitchenpos.order.domain;

public class OrderTable {
    private Long id;
    private Long tableGroupId;
    private int numberOfGuests;
    private boolean empty;

    public OrderTable() {
    }

    public OrderTable(int numberOfGuests, boolean empty) {
        validateNumberOfGuests(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    private void validateNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("방문한 손님 수는 0명 이상이어야 합니다.");
        }
    }

    public static OrderTable of(int numberOfGuests, boolean empty) {
        return new OrderTable(numberOfGuests, empty);
    }

    public void group(Long tableGroupId) {
        this.tableGroupId = tableGroupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getTableGroupId() {
        return tableGroupId;
    }

    public void setTableGroupId(final Long tableGroupId) {
        this.tableGroupId = tableGroupId;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(final int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(final boolean empty) {
        this.empty = empty;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (isEmpty()) {
            throw new IllegalArgumentException("주문 테이블이 빈 테이블이면 방문자 수를 변경할 수 없습니다.");
        }
        this.numberOfGuests = numberOfGuests;
    }
}
