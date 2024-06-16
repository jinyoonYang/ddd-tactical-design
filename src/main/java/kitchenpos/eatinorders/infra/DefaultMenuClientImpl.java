package kitchenpos.eatinorders.infra;

import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultMenuClientImpl implements MenuClient{
    private MenuRepository menuRepository;

    public DefaultMenuClientImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu getMenuById(UUID id) {
        return menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("menu not found"));
    }
}
