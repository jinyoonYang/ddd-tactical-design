package kitchenpos.eatinorders.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;
    private UUID orderTableId;
    @Embedded
    @Enumerated(EnumType.STRING)
    private OrderType type;
    @Embedded
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;
    @Embedded
    private OrderLineItems orderLineItems;

    protected EatInOrder() {
    }

    private EatInOrder(UUID id, UUID orderTableId, OrderType type, OrderStatus status, LocalDateTime orderDateTime, OrderLineItems orderLineItems) {
        this.validate(orderTableId);

        this.id = id;
        this.orderTableId = orderTableId;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
    }

    private void validate(UUID orderTableId) {
        if (orderTableId == null){
            throw new IllegalArgumentException("orderTableId cannot be null");
        }
    }

    public static EatInOrder create(UUID orderTableId, OrderLineItems orderLineItems) {
        return new EatInOrder(
                UUID.randomUUID(),
                orderTableId,
                OrderType.EAT_IN,
                OrderStatus.WAITING,
                LocalDateTime.now(),
                orderLineItems
        );
    }

    public OrderLineItems getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public OrderType getType() {
        return type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }
}
