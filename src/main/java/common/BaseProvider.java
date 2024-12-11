package common;

import constants.Endpoints;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

/**
 * Класс-спецификация для API-запросов
 */
public class BaseProvider {

    private final String APPLICATION_JSON = "application/json";

    private RequestSpecification getSpec(String server, String path) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri("https://" + server)
                .setBasePath(path)
                .setContentType(APPLICATION_JSON);
        return RestAssured.given().spec(builder.build());
    }

    /**
     * Получить спецификацию для обращения к API сервиса
     */
    public RequestSpecification getBaseSpec() {
        return getSpec(Endpoints.SERVER, Endpoints.PATH);
    }
}
