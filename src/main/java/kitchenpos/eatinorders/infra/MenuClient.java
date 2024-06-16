package kitchenpos.eatinorders.infra;

import kitchenpos.menus.domain.tobe.Menu;

import java.util.UUID;

public interface MenuClient {
    public Menu getMenuById(UUID id);
}
