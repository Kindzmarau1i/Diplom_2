import common.StellarBurgerApiClient;
import common.TypedResponse;
import constants.ErrorMessages;
import dto.requests.CreateCustomerRequestDTO;
import dto.requests.CreateOrderRequestDTO;
import dto.responses.CreateCustomerResponseDTO;
import dto.responses.GetIngredientsDTO;
import dto.responses.GetOrdersCustomerDTO;
import dto.responses.subobjects.CustomerOrderDTO;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GetOrdersCustomerTest extends BaseTest {

    StellarBurgerApiClient stellarBurgerApiClient = new StellarBurgerApiClient();

    @Test
    public void getOrdersCustomerWithAuth() {
        // Создать пользователя
        TypedResponse<CreateCustomerResponseDTO> responseCreateCustomer = prepareCustomer(email, password, name);

        // Получить список ингредиентов
        TypedResponse<GetIngredientsDTO> ingredients = stellarBurgerApiClient.getIngredients();

        // Создать заказ пользователю
        stellarBurgerApiClient.createOrder(CreateOrderRequestDTO.builder()
                .ingredients(List.of(ingredients.body().getData().stream().findFirst()
                        .orElseThrow(() -> new NullPointerException("Не найден ни один ингредиент"))
                        .get_id()))
                .build(), responseCreateCustomer.body().getAccessToken());

        // Получить заказы конкретного пользователя c авторизацией
        TypedResponse<GetOrdersCustomerDTO> responseOrders = stellarBurgerApiClient.getOrdersCustomer(responseCreateCustomer.body().getAccessToken());
        CustomerOrderDTO customerOrders = responseOrders.body().getOrders().stream()
                .findFirst().orElseThrow(() -> new NullPointerException("Не найден ни один заказ"));
        Assert.assertEquals(responseOrders.statusCode(), 200);
        Assert.assertEquals(responseOrders.body().getSuccess(), true);
        Assert.assertFalse(customerOrders.getIngredients().isEmpty());
        Assert.assertNotNull(customerOrders.get_id());
        Assert.assertEquals(customerOrders.getStatus(), "done");
        Assert.assertNotNull(customerOrders.getNumber());
        Assert.assertNotNull(customerOrders.getCreatedAt());
        Assert.assertNotNull(customerOrders.getUpdatedAt());

        // Удалить пользователя
        deleteCustomer(responseCreateCustomer.body().getAccessToken());
    }

    @Test
    public void getOrdersCustomerWithoutAuth() {
        // Создать пользователя
        TypedResponse<CreateCustomerResponseDTO> responseCreateCustomer = prepareCustomer(email, password, name);

        // Получить список ингредиентов
        TypedResponse<GetIngredientsDTO> ingredients = stellarBurgerApiClient.getIngredients();

        // Создать заказ пользователю
        stellarBurgerApiClient.createOrder(CreateOrderRequestDTO.builder()
                .ingredients(List.of(ingredients.body().getData().stream().findFirst()
                        .orElseThrow(() -> new NullPointerException("Не найден ни один ингредиент"))
                        .get_id()))
                .build(), responseCreateCustomer.body().getAccessToken());

        // Получить заказы конкретного пользователя c авторизацией
        TypedResponse<GetOrdersCustomerDTO> responseOrders = stellarBurgerApiClient.getOrdersCustomer("");
        Assert.assertEquals(responseOrders.statusCode(), 401);
        Assert.assertEquals(responseOrders.error().getSuccess(), false);
        Assert.assertEquals(responseOrders.error().getMessage(), ErrorMessages.NONE_AUTHORISED);

        // Удалить пользователя
        deleteCustomer(responseCreateCustomer.body().getAccessToken());
    }

    @Step("Создать пользователя")
    public TypedResponse<CreateCustomerResponseDTO> prepareCustomer(String email, String password, String name) {
        return stellarBurgerApiClient.createCustomer(CreateCustomerRequestDTO.builder()
                .email(email)
                .password(password)
                .name(name)
                .build());
    }
}
