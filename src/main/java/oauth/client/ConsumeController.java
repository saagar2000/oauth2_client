package oauth.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
