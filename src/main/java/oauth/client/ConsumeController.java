package oauth.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@RestController
@RequiredArgsConstructor
public class ConsumeController {

  private static final String PATH = "/rest/api/v1/products";
  private final ApiClient apiClient;

  @GetMapping(value = "/products-client")
  public String getProductList() {

    return apiClient.invokeApi(PATH);
  }
}
