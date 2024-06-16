package kitchenpos.eatinorders.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import kitchenpos.eatinorders.infra.MenuClient;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "menuId", nullable = false)
    private UUID menuId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Transient
    private MenuClient menuClient;

    protected OrderLineItem() {
    }

    private OrderLineItem(long quantity, UUID menuId, BigDecimal price, MenuClient menuClient) {
        this.validate(menuClient, menuId, quantity);

        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
        this.menuClient = menuClient;
    }

    private void validate(MenuClient menuClient, UUID menuId, long quantity) {
        if(!Optional.ofNullable(menuClient.getMenuById(menuId)).isPresent()){
            throw new IllegalArgumentException("Menu does not exist");
        }

        if(quantity <= 0){
            throw new IllegalArgumentException("quantity must be greater than zero");
        }
    }

    public static OrderLineItem create(long quantity, UUID menuId, BigDecimal price, MenuClient menuClient) {
        return new OrderLineItem(quantity, menuId, price, menuClient);
    }
}
