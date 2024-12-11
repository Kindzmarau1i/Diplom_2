package common;

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

/**
 * Набор методов для работы с API сервиса
 */
public interface StellarBurgersApi {

    /**
     * Создать пользователя
     */
    TypedResponse<CreateCustomerResponseDTO> createCustomer(CreateCustomerRequestDTO requestBody);

    /**
     * Логин пользователя
     */
    TypedResponse<LoginCustomerResponseDTO> loginCustomer(LoginCustomerRequestDTO requestBody);

    /**
     * Редактировать пользователя
     */
    TypedResponse<UpdateCustomerResponseDTO> updateCustomer(UpdateCustomerRequestDTO requestBody, String accessToken);

    /**
     * Удалить пользователя
     */
    void deleteCustomer(String accessToken);

    /**
     * Создать заказ
     */
    TypedResponse<CreateOrderResponseDTO> createOrder(CreateOrderRequestDTO requestBody, String accessToken);

    /**
     * Получить данные об ингредиентах
     */
    TypedResponse<GetIngredientsDTO> getIngredients();

    /**
     * Получить заказы конкретного пользователя
     */
    TypedResponse<GetOrdersCustomerDTO> getOrdersCustomer(String accessToken);
}
