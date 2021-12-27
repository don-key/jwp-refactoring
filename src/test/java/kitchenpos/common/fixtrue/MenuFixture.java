package kitchenpos.common.fixtrue;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.domain.MenuProducts;

import java.math.BigDecimal;

public class MenuFixture {

    private MenuFixture() {

    }

    public static Menu of(String name, BigDecimal price, MenuGroup menuGroup, MenuProducts menuProducts) {
        return Menu.of(name, price, menuGroup, menuProducts);
    }
}
