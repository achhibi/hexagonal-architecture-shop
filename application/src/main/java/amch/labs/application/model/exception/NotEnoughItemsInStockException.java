package amch.labs.application.model.exception;

public class NotEnoughItemsInStockException extends Exception {

  private final int itemsInStock;

  public NotEnoughItemsInStockException(String message, int itemsInStock) {
    super(message);
    this.itemsInStock = itemsInStock;
  }

  public int itemsInStock() {
    return itemsInStock;
  }
}
