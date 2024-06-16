package kitchenpos.eatinorders.domain.tobe;

import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class EatInOrderTest {
    private MenuRepository menuRepository;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void create() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();
        final UUID orderTableId = createOrderTableRequest(true, 4).getId();
        final OrderLineItem orderLineItem = createOrderLineItemRequest(menuId, 19_000L, 3L);
        final EatInOrder actual = EatInOrder.create(orderTableId, OrderLineItems.create(Arrays.asList(orderLineItem)));
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(OrderType.EAT_IN),
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(actual.getOrderDateTime()).isNotNull(),
                () -> assertThat(actual.getOrderLineItems().getOrderLineItems()).hasSize(1),
                () -> assertThat(actual.getOrderTableId()).isEqualTo(orderTableId)
        );
    }

    @DisplayName("등록되지 않은 메뉴로 매장주문을 등록할 수 없다.")
    @Test
    void createException() {
        final UUID menuId = menuRepository.save(menu(19_000L, true, menuProduct())).getId();

        final FakeMenuClientImpl fakeMenuClient = new FakeMenuClientImpl();
        fakeMenuClient.setResultFlag(false);

        assertThatThrownBy(() -> EatInOrder.create(createOrderTableRequest(true, 4).getId()
                , OrderLineItems.create(Arrays.asList(
                        OrderLineItem.create(1L, menuId, BigDecimal.valueOf(1000), fakeMenuClient)
                        ))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static OrderLineItem createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        FakeMenuClientImpl fakeMenuClient = new FakeMenuClientImpl();
        fakeMenuClient.setResultFlag(true);

        final OrderLineItem orderLineItem = OrderLineItem.create(quantity, menuId, BigDecimal.valueOf(price), fakeMenuClient);

        return orderLineItem;
    }

    private static OrderTable createOrderTableRequest(final boolean occupied, final int numberOfGuests) {
        final OrderTable orderTable = new OrderTable();
        orderTable.setId(UUID.randomUUID());
        orderTable.setName("1번");
        orderTable.setNumberOfGuests(numberOfGuests);
        orderTable.setOccupied(occupied);
        return orderTable;
    }
}
