package kitchenpos.eatinorders.domain.tobe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Embeddable
public class OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "order_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> orderLineItems;


    protected OrderLineItems() {
    }

    private OrderLineItems(List<OrderLineItem> orderLineItems) {
        this.validate(orderLineItems);
        this.orderLineItems = orderLineItems;
    }

    private void validate(List<OrderLineItem> orderLineItems) {
        if (orderLineItems.size() <= 0){
            throw new IllegalArgumentException("orderLineItems must contain at least one orderLineItem");
        }
    }

    public static OrderLineItems create(List<OrderLineItem> orderLineItems) {
        return new OrderLineItems(orderLineItems);
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }
}
