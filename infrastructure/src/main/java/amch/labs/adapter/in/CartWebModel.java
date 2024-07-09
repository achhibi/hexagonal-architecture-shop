package amch.labs.adapter.in;

import amch.labs.application.model.cart.Cart;
import amch.labs.application.model.money.Money;
import java.util.List;

public record CartWebModel(
    List<CartLineItemWebModel> lineItems, int numberOfItems, Money subTotal) {

  static CartWebModel fromDomainModel(Cart cart) {
    return new CartWebModel(
        cart.lineItems().stream().map(CartLineItemWebModel::fromDomainModel).toList(),
        cart.numberOfItems(),
        cart.subTotal());
  }
}
