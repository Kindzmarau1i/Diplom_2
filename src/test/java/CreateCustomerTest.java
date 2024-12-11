import common.StellarBurgerApiClient;
import common.TypedResponse;
import constants.ErrorMessages;
import dto.requests.CreateCustomerRequestDTO;
import dto.responses.CreateCustomerResponseDTO;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.junit.Test;

public class CreateCustomerTest extends BaseTest {

    StellarBurgerApiClient stellarBurgerApiClient = new StellarBurgerApiClient();

    @Test
    public void createCustomerSuccessTest() {
        // Создать уникального пользователя;
        TypedResponse<CreateCustomerResponseDTO> response = prepareCustomer(email, password, name);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.body().getSuccess(), true);
        Assert.assertEquals(response.body().getUser().getEmail().toLowerCase(), email.toLowerCase());
        Assert.assertEquals(response.body().getUser().getName().toLowerCase(), name.toLowerCase());
        Assert.assertNotNull(response.body().getAccessToken());
        Assert.assertNotNull(response.body().getRefreshToken());

        // Удалить пользователя
        deleteCustomer(response.body().getAccessToken());
    }

    @Test
    public void createExistCustomerTest() {
        // Создать уникального пользователя;
        TypedResponse<CreateCustomerResponseDTO> responseNewCustomer = prepareCustomer(email, password, name);

        // Создать пользователя, который уже зарегистрирован
        TypedResponse<CreateCustomerResponseDTO> responseExistCustomer = prepareCustomer(email, password, name);
        Assert.assertEquals(responseExistCustomer.statusCode(), 403);
        Assert.assertEquals(responseExistCustomer.error().getSuccess(), false);
        Assert.assertEquals(responseExistCustomer.error().getMessage(), ErrorMessages.USER_EXISTS);

        // Удалить пользователя
        deleteCustomer(responseNewCustomer.body().getAccessToken());
    }

    @Test
    public void createCustomerWithoutRequiredAttributes() {
        TypedResponse<CreateCustomerResponseDTO> response = stellarBurgerApiClient.createCustomer(CreateCustomerRequestDTO.builder()
                .email(email)
                .name(name)
                .build());
        Assert.assertEquals(response.statusCode(), 403);
        Assert.assertEquals(response.error().getSuccess(), false);
        Assert.assertEquals(response.error().getMessage(), ErrorMessages.INCORRECT_REGISTER);
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
