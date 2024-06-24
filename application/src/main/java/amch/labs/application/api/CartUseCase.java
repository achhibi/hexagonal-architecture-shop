package amch.labs.application.api;

import amch.labs.application.model.cart.Cart;
import amch.labs.application.model.customer.CustomerId;
import amch.labs.application.model.exception.NotEnoughItemsInStockException;
import amch.labs.application.model.product.ProductId;

public interface CartUseCase {

  Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
      throws ProductNotFoundException, NotEnoughItemsInStockException;
}
