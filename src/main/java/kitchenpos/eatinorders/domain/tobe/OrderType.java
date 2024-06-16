package kitchenpos.eatinorders.domain.tobe;

import jakarta.persistence.Embeddable;

@Embeddable
public enum OrderType {
    DELIVERY, TAKEOUT, EAT_IN
}
