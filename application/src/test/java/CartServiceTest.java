import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import amch.labs.application.CartService;
import amch.labs.application.api.ProductNotFoundException;
import amch.labs.application.model.cart.Cart;
import amch.labs.application.model.customer.CustomerId;
import amch.labs.application.model.exception.NotEnoughItemsInStockException;
import amch.labs.application.model.money.Money;
import amch.labs.application.model.product.Product;
import amch.labs.application.model.product.ProductId;
import amch.labs.application.spi.CartRepository;
import amch.labs.application.spi.ProductRepository;
import java.util.Currency;
import java.util.Optional;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class CartServiceTest {

  private static final CustomerId TEST_CUSTOMER_ID = new CustomerId(61157L);
  private static final Product TEST_PRODUCT_1 = createTestProduct(euros(19, 99));
  private static final Product TEST_PRODUCT_2 = createTestProduct(euros(25, 99));

  private final CartRepository cartRepository = mock(CartRepository.class);
  private final ProductRepository productRepository = mock(ProductRepository.class);
  private final CartService addToCartService = new CartService(cartRepository, productRepository);

  @BeforeEach
  void initTestDoubles() {
    when(productRepository.findById(TEST_PRODUCT_1.id())).thenReturn(Optional.of(TEST_PRODUCT_1));

    when(productRepository.findById(TEST_PRODUCT_2.id())).thenReturn(Optional.of(TEST_PRODUCT_2));
  }

  @Test
  @Order(0)
  void givenExistingCart_addToCart_cartWithAddedProductIsSavedAndReturned()
      throws NotEnoughItemsInStockException, ProductNotFoundException {
    Cart persistedCart = new Cart(TEST_CUSTOMER_ID);
    persistedCart.addProduct(TEST_PRODUCT_1, 1);

    when(cartRepository.findByCustomerId(TEST_CUSTOMER_ID)).thenReturn(Optional.of(persistedCart));

    Cart cart = addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_2.id(), 3);

    verify(cartRepository).save(cart);

    assertThat(cart.lineItems()).hasSize(2);
    assertThat(cart.lineItems().get(0).product()).isEqualTo(TEST_PRODUCT_2);
    assertThat(cart.lineItems().get(0).quantity()).isEqualTo(3);
    assertThat(cart.lineItems().get(1).product()).isEqualTo(TEST_PRODUCT_1);
    assertThat(cart.lineItems().get(1).quantity()).isEqualTo(1);
  }

  @Test
  void givenNoExistingCart_addToCart_cartWithAddedProductIsSavedAndReturned()
      throws NotEnoughItemsInStockException, ProductNotFoundException {
    Cart cart = addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_1.id(), 2);

    verify(cartRepository).save(cart);

    assertThat(cart.lineItems()).hasSize(1);
    assertThat(cart.lineItems().get(0).product()).isEqualTo(TEST_PRODUCT_1);
    assertThat(cart.lineItems().get(0).quantity()).isEqualTo(2);
  }

  @Test
  void givenAnUnknownProductId_addToCart_throwsException() {
    ProductId productId = ProductId.random();

    ThrowableAssert.ThrowingCallable invocation =
        () -> addToCartService.addToCart(TEST_CUSTOMER_ID, productId, 1);

    assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(invocation);
    verify(cartRepository, never()).save(any());
  }

  @Test
  void givenQuantityLessThan1_addToCart_throwsException() {
    int quantity = 0;

    ThrowableAssert.ThrowingCallable invocation =
        () -> addToCartService.addToCart(TEST_CUSTOMER_ID, TEST_PRODUCT_1.id(), quantity);

    assertThatIllegalArgumentException().isThrownBy(invocation);
    verify(cartRepository, never()).save(any());
  }

  private static Product createTestProduct(Money price) {
    return createTestProduct(price, Integer.MAX_VALUE);
  }

  private static Product createTestProduct(Money price, int itemsInStock) {
    return new Product(
        ProductId.random(), //
        "any name",
        "any description",
        price,
        itemsInStock);
  }

  private static Money euros(int euros, int cents) {
    return Money.of(Currency.getInstance("EUR"), euros, cents);
  }
}
