package amch.labs.application.spi;

import amch.labs.application.model.cart.Cart;
import amch.labs.application.model.customer.CustomerId;
import java.util.Optional;

public interface CartRepository {

  Optional<Cart> findByCustomerId(CustomerId customerId);

  void save(Cart cart);
}
