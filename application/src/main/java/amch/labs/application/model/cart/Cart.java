package amch.labs.application.model.cart;

import amch.labs.application.model.customer.CustomerId;
import amch.labs.application.model.exception.NotEnoughItemsInStockException;
import amch.labs.application.model.money.Money;
import amch.labs.application.model.product.Product;
import amch.labs.application.model.product.ProductId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class Cart {

  @Getter private final CustomerId id;
  private Map<ProductId, CartLineItem> lineItems = new HashMap<>();

  public Cart(CustomerId id) {
    this.id = id;
  }

  public void addProduct(Product product, int quantity) throws NotEnoughItemsInStockException {
    lineItems
        .computeIfAbsent(product.id(), ignored -> new CartLineItem(product))
        .increaseQuantityBy(quantity, product.itemsInStock());
  }

  public List<CartLineItem> lineItems() {
    return List.copyOf(lineItems.values());
  }

  public int numberOfItems() {
    return lineItems.values().stream().mapToInt(CartLineItem::quantity).sum();
  }

  public Money subTotal() {
    return lineItems.values().stream().map(CartLineItem::subTotal).reduce(Money::add).orElse(null);
  }
}
