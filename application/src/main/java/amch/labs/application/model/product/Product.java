package amch.labs.application.model.product;

import amch.labs.application.model.money.Money;

public record Product(
    ProductId id, String name, String description, Money price, int itemsInStock) {}
