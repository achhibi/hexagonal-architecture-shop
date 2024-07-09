package amch.labs.adapter.in;

import amch.labs.application.model.cart.CartLineItem;
import amch.labs.application.model.money.Money;
import amch.labs.application.model.product.Product;

public record CartLineItemWebModel(
    String productId, String productName, Money price, int quantity) {

  public static CartLineItemWebModel fromDomainModel(CartLineItem lineItem) {
    Product product = lineItem.product();
    return new CartLineItemWebModel(
        product.id().value(), product.name(), product.price(), lineItem.quantity());
  }
}
