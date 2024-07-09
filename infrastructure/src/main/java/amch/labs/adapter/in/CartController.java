package amch.labs.adapter.in;

import static amch.labs.adapter.in.CustomerIdParser.parseCustomerId;
import static amch.labs.adapter.in.ProductIdParser.parseProductId;

import amch.labs.application.api.CartUseCase;
import amch.labs.application.api.ProductNotFoundException;
import amch.labs.application.model.cart.Cart;
import amch.labs.application.model.customer.CustomerId;
import amch.labs.application.model.exception.NotEnoughItemsInStockException;
import amch.labs.application.model.product.ProductId;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {
  private final CartUseCase addToCartUseCase;

  public CartController(CartUseCase addToCartUseCase) {
    this.addToCartUseCase = addToCartUseCase;
  }

  @PostMapping("/{customerId}/line-items")
  public CartWebModel addLineItem(
      @PathVariable("customerId") String customerIdString,
      @RequestParam("productId") String productIdString,
      @RequestParam("quantity") int quantity) {
    CustomerId customerId = parseCustomerId(customerIdString);
    ProductId productId = parseProductId(productIdString);

    try {
      Cart cart = addToCartUseCase.addToCart(customerId, productId, quantity);
      return CartWebModel.fromDomainModel(cart);
    } catch (NotEnoughItemsInStockException e) {
      throw new RuntimeException(e);
    } catch (ProductNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
