import common.StellarBurgerApiClient;
import common.TypedResponse;
import constants.ErrorMessages;
import dto.requests.CreateCustomerRequestDTO;
import dto.requests.LoginCustomerRequestDTO;
import dto.responses.LoginCustomerResponseDTO;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.junit.Test;

public class LoginCustomerTest extends BaseTest {

    StellarBurgerApiClient stellarBurgerApiClient = new StellarBurgerApiClient();

    @Test
    public void loginCustomerSuccessTest() {
        // Создать пользователя
        prepareCustomer(email, password, name);

        // Выполнить логин под существующим пользователем
        TypedResponse<LoginCustomerResponseDTO> loginResponse = loginCustomer(email, password);
        Assert.assertEquals(loginResponse.statusCode(), 200);
        Assert.assertEquals(loginResponse.body().getSuccess(), true);
        Assert.assertEquals(loginResponse.body().getUser().getEmail().toLowerCase(), email.toLowerCase());
        Assert.assertEquals(loginResponse.body().getUser().getName().toLowerCase(), name.toLowerCase());
        Assert.assertNotNull(loginResponse.body().getAccessToken());
        Assert.assertNotNull(loginResponse.body().getRefreshToken());

        // Удалить пользователя
        deleteCustomer(loginResponse.body().getAccessToken());
    }

    @Test
    public void loginCustomerInvalidLoginAndPassword() {
        // Выполнить логин с неверным логином и паролем
        TypedResponse<LoginCustomerResponseDTO> loginResponse = loginCustomer(email, password);
        Assert.assertEquals(loginResponse.statusCode(), 401);
        Assert.assertEquals(loginResponse.error().getSuccess(), false);
        Assert.assertEquals(loginResponse.error().getMessage(), ErrorMessages.INCORRECT_LOGIN);
    }

    @Step("Создать пользователя")
    public void prepareCustomer(String email, String password, String name) {
        stellarBurgerApiClient.createCustomer(CreateCustomerRequestDTO.builder()
                .email(email)
                .password(password)
                .name(name)
                .build());
    }

    @Step("Выполнить логин пользователя")
    public TypedResponse<LoginCustomerResponseDTO> loginCustomer(String email, String password) {
        return stellarBurgerApiClient.loginCustomer(LoginCustomerRequestDTO.builder()
                .email(email)
                .password(password)
                .build());
    }
}
