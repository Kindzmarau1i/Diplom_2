import common.StellarBurgerApiClient;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;

public class BaseTest {
    protected String email;
    protected String password;
    protected String name;

    StellarBurgerApiClient stellarBurgerApiClient = new StellarBurgerApiClient();

    @Before
    public void prepareTestData() {
        this.email = RandomStringUtils.randomAlphabetic(10) + "@" + "yandex.ru";
        this.password = RandomStringUtils.randomAlphabetic(10);
        this.name = RandomStringUtils.randomAlphabetic(10);
    }

    @Step("Удалить пользователя")
    public void deleteCustomer(String accessToken) {
        stellarBurgerApiClient.deleteCustomer(accessToken);
    }
}
