package amch.labs.adapter.out;

import amch.labs.application.model.cart.Cart;
import amch.labs.application.model.customer.CustomerId;
import amch.labs.application.spi.CartRepository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCartRepositroy implements CartRepository {

  private final Map<CustomerId, Cart> carts = new ConcurrentHashMap<>();

  @Override
  public Optional<Cart> findByCustomerId(CustomerId customerId) {
    return Optional.ofNullable(carts.get(customerId));
  }

  @Override
  public void save(Cart cart) {
    carts.put(cart.id(), cart);
  }
}
