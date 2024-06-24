package amch.labs.application.model.product;

import java.util.UUID;

public record ProductId(String value) {

  public static ProductId random() {
    return new ProductId(UUID.randomUUID().toString());
  }
}
