package kitchenpos.eatinorders.domain.tobe;

import kitchenpos.eatinorders.infra.MenuClient;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuGroup;
import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.menus.domain.tobe.MenuProducts;
import kitchenpos.products.domain.tobe.FakeProfanities;
import kitchenpos.products.domain.tobe.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@Component
public class FakeMenuClientImpl implements MenuClient {
    private boolean isResultFlag = false;

    public FakeMenuClientImpl() {
    }

    public void setResultFlag(boolean isResultFlag) {
        isResultFlag = isResultFlag;
    }

    @Override
    public Menu getMenuById(UUID id) {
        if(!isResultFlag) {
          return null;
        }

        Product product = Product.createProduct("후라이드", BigDecimal.valueOf(16000), new FakeProfanities());
        MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), "세트메뉴");
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(MenuProduct.createMenuProduct(product.getId(), product.getPrice().getPriceValue(), 2)));
        BigDecimal menuPrice = BigDecimal.valueOf(19000);

        return Menu.createMenu("후라이드+후라이드", menuGroup, menuPrice, menuProducts, new FakeProfanities());
    }
}
