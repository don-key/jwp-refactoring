package kitchenpos.common.fixtrue;

import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.Product;

public class MenuProductFixture {

    private MenuProductFixture() {
    }

    public static MenuProduct of(Product product, long quantity) {
        return MenuProduct.of(product, quantity);
    }
}
