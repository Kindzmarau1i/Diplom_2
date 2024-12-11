package common;

import constants.Endpoints;
import dto.requests.CreateCustomerRequestDTO;
import dto.requests.CreateOrderRequestDTO;
import dto.requests.LoginCustomerRequestDTO;
import dto.requests.UpdateCustomerRequestDTO;
import dto.responses.CreateCustomerResponseDTO;
import dto.responses.CreateOrderResponseDTO;
import dto.responses.GetIngredientsDTO;
import dto.responses.GetOrdersCustomerDTO;
import dto.responses.LoginCustomerResponseDTO;
import dto.responses.UpdateCustomerResponseDTO;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Класс-реализация методов API сервиса
 */
public class StellarBurgerApiClient implements StellarBurgersApi {

    BaseProvider baseProvider = new BaseProvider();

    @Step("Создать пользователя")
    @Override
    public TypedResponse<CreateCustomerResponseDTO> createCustomer(CreateCustomerRequestDTO requestBody) {
        Response response = given().log().all()
                .spec(baseProvider.getBaseSpec())
                .body(requestBody)
                .post(Endpoints.AUTH_REGISTER);
        response.then().log().all();
        return new TypedResponse<>(response, CreateCustomerResponseDTO.class);
    }

    @Step("Залогинить пользователя")
    @Override
    public TypedResponse<LoginCustomerResponseDTO> loginCustomer(LoginCustomerRequestDTO requestBody) {
        Response response = given()
                .spec(baseProvider.getBaseSpec())
                .body(requestBody)
                .post(Endpoints.AUTH_LOGIN);
        return new TypedResponse<>(response, LoginCustomerResponseDTO.class);
    }

    @Step("Редактировать пользователя")
    @Override
    public TypedResponse<UpdateCustomerResponseDTO> updateCustomer(UpdateCustomerRequestDTO requestBody, String accessToken) {
        Response response = given()
                .spec(baseProvider.getBaseSpec())
                .header("authorization", accessToken)
                .body(requestBody)
                .patch(Endpoints.AUTH_USER);
        return new TypedResponse<>(response, UpdateCustomerResponseDTO.class);
    }

    @Step("Удалить пользователя")
    @Override
    public void deleteCustomer(String accessToken) {
        given().spec(baseProvider.getBaseSpec())
                .header("authorization", accessToken)
                .delete(Endpoints.AUTH_USER);
    }

    @Step("Создать заказ")
    @Override
    public TypedResponse<CreateOrderResponseDTO> createOrder(CreateOrderRequestDTO requestBody, String accessToken) {
        Response response = given()
                .spec(baseProvider.getBaseSpec())
                .header("authorization", accessToken)
                .body(requestBody)
                .post(Endpoints.ORDERS);
        return new TypedResponse<>(response, CreateOrderResponseDTO.class);
    }

    @Step("Получить данные об ингредиентах")
    @Override
    public TypedResponse<GetIngredientsDTO> getIngredients() {
        Response response = given()
                .spec(baseProvider.getBaseSpec())
                .get(Endpoints.INGREDIENTS);
        return new TypedResponse<>(response, GetIngredientsDTO.class);
    }

    @Step("Получить заказы конкретного пользователя")
    @Override
    public TypedResponse<GetOrdersCustomerDTO> getOrdersCustomer(String accessToken) {
        Response response = given().given().log().all()
                .spec(baseProvider.getBaseSpec())
                .header("authorization", accessToken)
                .get(Endpoints.ORDERS);
        response.then().log().all();
        return new TypedResponse<>(response, GetOrdersCustomerDTO.class);
    }
}
