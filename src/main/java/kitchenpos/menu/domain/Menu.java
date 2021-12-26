package kitchenpos.menu.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_menu_menu_group"))
    private MenuGroup menuGroup;

    @Embedded
    private MenuProducts menuProducts = new MenuProducts();

    protected Menu() {
    }

    private Menu(String name, BigDecimal price, MenuGroup menuGroup) {
        validatePrice(price);
        this.name = Objects.requireNonNull(name, "메뉴명은 필수입니다.");
        this.price = price;
        this.menuGroup = menuGroup;
    }

    public static Menu of(String name, BigDecimal price, MenuGroup menuGroup) {
        return new Menu(name, price, menuGroup);
    }

    public void addMenuProduct(List<MenuProduct> menuProducts) {
        menuProducts.forEach(menuProduct -> {
            validateTotalPrice(menuProduct);
            this.menuProducts.add(menuProduct);
            menuProduct.changeMenu(this);
        });
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }

    private void validateTotalPrice(MenuProduct menuProduct) {
        if (price.compareTo(menuProduct.calculateTotalPrice()) > 0) {
            throw new IllegalArgumentException("메뉴의 가격은 " +
                    "메뉴 상품들의 수량 * 상품의 가격을 모두 더한 금액 보다 작거나 같아야 합니다.");
        }
    }

    private void validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("상품의 가격은 0원 이상 이어야 합니다.");
        }
    }
}
