import common.StellarBurgerApiClient;
import common.TypedResponse;
import constants.ErrorMessages;
import dto.requests.CreateCustomerRequestDTO;
import dto.requests.UpdateCustomerRequestDTO;
import dto.responses.CreateCustomerResponseDTO;
import dto.responses.UpdateCustomerResponseDTO;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

public class ChangeCustomerTest extends BaseTest {

    StellarBurgerApiClient stellarBurgerApiClient = new StellarBurgerApiClient();

    @Test
    public void updateCustomerWithAuthTest() {
        // Создать пользователя
        TypedResponse<CreateCustomerResponseDTO> responseCreate = prepareCustomer(email, password, name);

        // Обновить пользователя с авторизацией
        String newEmail = RandomStringUtils.randomAlphabetic(10);
        String newName = RandomStringUtils.randomAlphabetic(10);
        TypedResponse<UpdateCustomerResponseDTO> responseUpdate = stellarBurgerApiClient.updateCustomer(UpdateCustomerRequestDTO.builder()
                .email(newEmail)
                .name(newName)
                .build(), responseCreate.body().getAccessToken());
        Assert.assertEquals(responseUpdate.statusCode(), 200);
        Assert.assertEquals(responseUpdate.body().getSuccess(), true);
        Assert.assertEquals(responseUpdate.body().getUser().getEmail().toLowerCase(), newEmail.toLowerCase());
        Assert.assertEquals(responseUpdate.body().getUser().getName().toLowerCase(), newName.toLowerCase());

        // Удалить пользователя
        deleteCustomer(responseCreate.body().getAccessToken());
    }

    @Test
    public void updateCustomerWithoutAuthTest() {
        // Создать пользователя
        TypedResponse<CreateCustomerResponseDTO> responseCreate = prepareCustomer(email, password, name);

        // Обновить пользователя без авторизации
        String newEmail = RandomStringUtils.randomAlphabetic(10);
        String newName = RandomStringUtils.randomAlphabetic(10);
        TypedResponse<UpdateCustomerResponseDTO> responseUpdate = stellarBurgerApiClient.updateCustomer(UpdateCustomerRequestDTO.builder()
                .email(newEmail)
                .name(newName)
                .build(), "");
        Assert.assertEquals(responseUpdate.statusCode(), 401);
        Assert.assertEquals(responseUpdate.error().getSuccess(), false);
        Assert.assertEquals(responseUpdate.error().getMessage(), ErrorMessages.NONE_AUTHORISED);

        // Удалить пользователя
        deleteCustomer(responseCreate.body().getAccessToken());
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
