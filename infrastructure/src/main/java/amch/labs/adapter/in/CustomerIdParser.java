package amch.labs.adapter.in;

import amch.labs.adapter.in.exception.ClientErrorException;
import amch.labs.adapter.in.exception.ErrorEntity;
import amch.labs.application.model.customer.CustomerId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class CustomerIdParser {

  private CustomerIdParser() {}

  public static CustomerId parseCustomerId(String val) {
    try {
      return new CustomerId(Integer.parseInt(val));
    } catch (IllegalArgumentException e) {
      throw new ClientErrorException(
          ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
              .body(new ErrorEntity(HttpStatus.BAD_REQUEST.value(), "Invalid 'customerId'")));
    }
  }
}
