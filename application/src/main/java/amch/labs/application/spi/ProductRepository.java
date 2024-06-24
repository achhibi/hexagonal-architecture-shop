package amch.labs.application.spi;

import amch.labs.application.model.product.Product;
import amch.labs.application.model.product.ProductId;
import java.util.Optional;

public interface ProductRepository {

  Optional<Product> findById(ProductId productId);
}
