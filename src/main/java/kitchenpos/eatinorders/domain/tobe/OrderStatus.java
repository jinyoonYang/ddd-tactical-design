package kitchenpos.eatinorders.domain.tobe;

import jakarta.persistence.Embeddable;

@Embeddable
public enum OrderStatus {
    WAITING, ACCEPTED, SERVED, DELIVERING, DELIVERED, COMPLETED
}
