package amch.labs.application;

import amch.labs.application.api.CartUseCase;
import amch.labs.application.api.ProductNotFoundException;
import amch.labs.application.ddd.UseCase;
import amch.labs.application.model.cart.Cart;
import amch.labs.application.model.customer.CustomerId;
import amch.labs.application.model.exception.NotEnoughItemsInStockException;
import amch.labs.application.model.product.ProductId;
import amch.labs.application.spi.CartRepository;
import amch.labs.application.spi.ProductRepository;
import java.util.Objects;

@UseCase
public class CartService implements CartUseCase {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;

  public CartService(CartRepository cartRepositoy, ProductRepository productRepository) {
    this.cartRepository = cartRepositoy;
    this.productRepository = productRepository;
  }

  @Override
  public Cart addToCart(CustomerId customerId, ProductId productId, int quantity)
      throws ProductNotFoundException, NotEnoughItemsInStockException {
    Objects.requireNonNull(customerId, "'customerId' must not be null");
    Objects.requireNonNull(productId, "'productId' must not be null");
    if (quantity < 1) {
      throw new IllegalArgumentException("'quantity' must be greater than 0");
    }

    var product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

    var cart = cartRepository.findByCustomerId(customerId).orElseGet(() -> new Cart(customerId));

    cart.addProduct(product, quantity);

    cartRepository.save(cart);

    return cart;
  }
}
