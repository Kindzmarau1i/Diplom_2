import common.StellarBurgerApiClient;
import common.TypedResponse;
import constants.ErrorMessages;
import dto.requests.CreateCustomerRequestDTO;
import dto.requests.CreateOrderRequestDTO;
import dto.responses.CreateCustomerResponseDTO;
import dto.responses.CreateOrderResponseDTO;
import dto.responses.GetIngredientsDTO;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CreateOrderTest extends BaseTest {

    StellarBurgerApiClient stellarBurgerApiClient = new StellarBurgerApiClient();

    @Test
    public void createOrderWithAuthTest() {
        // Создать пользователя
        TypedResponse<CreateCustomerResponseDTO> responseCreateCustomer = prepareCustomer(email, password, name);

        // Получить список ингредиентов
        TypedResponse<GetIngredientsDTO> ingredients = stellarBurgerApiClient.getIngredients();

        // Создать заказ с авторизацией
        TypedResponse<CreateOrderResponseDTO> responseCreateOrder = stellarBurgerApiClient.createOrder(CreateOrderRequestDTO.builder()
                .ingredients(List.of(ingredients.body().getData().stream().findFirst()
                        .orElseThrow(() -> new NullPointerException("Не найден ни один ингредиент"))
                        .get_id()))
                .build(), responseCreateCustomer.body().getAccessToken());
        Assert.assertEquals(responseCreateOrder.statusCode(), 200);
        Assert.assertEquals(responseCreateOrder.body().getSuccess(), true);
        Assert.assertEquals(responseCreateOrder.body().getName(), "Флюоресцентный бургер");
        Assert.assertEquals(responseCreateOrder.body().getOrder().getStatus(), "done");
        Assert.assertNotNull(responseCreateOrder.body().getOrder().getPrice());
        Assert.assertNotNull(responseCreateOrder.body().getOrder().getNumber());

        // Удалить пользователя
        deleteCustomer(responseCreateCustomer.body().getAccessToken());
    }

    @Test
    public void createOrderWithoutAuthTest() {
        // Создать пользователя
        TypedResponse<CreateCustomerResponseDTO> responseCreateCustomer = prepareCustomer(email, password, name);

        // Получить список ингредиентов
        TypedResponse<GetIngredientsDTO> ingredients = stellarBurgerApiClient.getIngredients();

        // Создать заказ без авторизации
        TypedResponse<CreateOrderResponseDTO> responseCreateOrder = stellarBurgerApiClient.createOrder(CreateOrderRequestDTO.builder()
                .ingredients(List.of(ingredients.body().getData().stream().findFirst()
                        .orElseThrow(() -> new NullPointerException("Не найден ни один ингредиент"))
                        .get_id()))
                .build(), "");
        Assert.assertEquals(responseCreateOrder.statusCode(), 200);
        Assert.assertEquals(responseCreateOrder.body().getSuccess(), true);
        Assert.assertEquals(responseCreateOrder.body().getName(), "Флюоресцентный бургер");
        Assert.assertNotNull(responseCreateOrder.body().getOrder().getNumber());

        // Удалить пользователя
        deleteCustomer(responseCreateCustomer.body().getAccessToken());
    }

    @Test
    public void createOrderWithoutIngredients() {
        // Создать пользователя
        TypedResponse<CreateCustomerResponseDTO> responseCreateCustomer = prepareCustomer(email, password, name);

        // Создать заказ без ингредиентов
        TypedResponse<CreateOrderResponseDTO> responseCreateOrder = stellarBurgerApiClient.createOrder(CreateOrderRequestDTO.builder()
                .ingredients(List.of())
                .build(), responseCreateCustomer.body().getAccessToken());
        Assert.assertEquals(responseCreateOrder.statusCode(), 400);
        Assert.assertEquals(responseCreateOrder.error().getSuccess(), false);
        Assert.assertEquals(responseCreateOrder.error().getMessage(), ErrorMessages.NONE_INGREDIENTS);

        // Удалить пользователя
        deleteCustomer(responseCreateCustomer.body().getAccessToken());
    }

    @Test
    public void createOrderWithInvalidIdIngredient() {
        // Создать пользователя
        TypedResponse<CreateCustomerResponseDTO> responseCreateCustomer = prepareCustomer(email, password, name);

        // Создать заказ, передав несуществующий id ингредиента
        TypedResponse<CreateOrderResponseDTO> responseCreateOrder = stellarBurgerApiClient.createOrder(CreateOrderRequestDTO.builder()
                .ingredients(List.of(RandomStringUtils.randomAlphabetic(10)))
                .build(), responseCreateCustomer.body().getAccessToken());
        Assert.assertEquals(responseCreateOrder.statusCode(), 500);

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
