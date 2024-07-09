package amch.labs.adapter.in.exception;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

public class ClientErrorException extends RuntimeException {

  @Getter private final ResponseEntity<ErrorEntity> response;

  public ClientErrorException(ResponseEntity<ErrorEntity> response) {
    super(getMessage(response));
    this.response = response;
  }

  private static String getMessage(ResponseEntity<ErrorEntity> response) {
    ErrorEntity body = response.getBody();
    return body != null ? body.errorMessage() : null;
  }
}
