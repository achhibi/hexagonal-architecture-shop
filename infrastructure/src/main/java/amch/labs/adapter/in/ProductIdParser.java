package amch.labs.adapter.in;

import amch.labs.adapter.in.exception.ClientErrorException;
import amch.labs.adapter.in.exception.ErrorEntity;
import amch.labs.application.model.product.ProductId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ProductIdParser {

  private ProductIdParser() {}

  public static ProductId parseProductId(String string) {
    if (string == null) {
      throw new ClientErrorException(
          ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
              .body(new ErrorEntity(HttpStatus.BAD_REQUEST.value(), "Missing 'productId'")));
    }

    try {
      return new ProductId(string);
    } catch (IllegalArgumentException e) {
      throw new ClientErrorException(
          ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
              .body(new ErrorEntity(HttpStatus.BAD_REQUEST.value(), "Invalid 'productId'")));
    }
  }
}
