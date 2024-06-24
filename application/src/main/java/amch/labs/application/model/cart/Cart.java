package amch.labs.application.model.cart;

import amch.labs.application.model.customer.CustomerId;
import amch.labs.application.model.exception.NotEnoughItemsInStockException;
import amch.labs.application.model.product.Product;
import amch.labs.application.model.product.ProductId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

  private final CustomerId customerId;
  private Map<ProductId, CartLineItem> lineItems = new HashMap<>();

  public Cart(CustomerId customerId) {
    this.customerId = customerId;
  }

  public void addProduct(Product product, int quantity) throws NotEnoughItemsInStockException {
    lineItems
        .computeIfAbsent(product.id(), ignored -> new CartLineItem(product))
        .increaseQuantityBy(quantity, product.itemsInStock());
  }

  public List<CartLineItem> lineItems() {
    return List.copyOf(lineItems.values());
  }
}
