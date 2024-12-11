package common;

import dto.responses.TestErrorDescriptionDTO;
import io.restassured.response.Response;

/**
 * Класс-обертка для работы с ответами и ошибками
 */
public class TypedResponse<T> {
    private final Response response;
    private final Class<T> cls;
    private T valid;
    private TestErrorDescriptionDTO invalid;

    public TypedResponse(Response response, Class<T> cls) {
        this.response = response;
        this.cls = cls;
    }

    public T body() {
        if (valid == null && !response.body().asString().contentEquals("")) {
            valid = response.as(cls);
        }
        return valid;
    }

    public Response response() {
        return response;
    }

    public int statusCode() {
        return response.statusCode();
    }

    public TestErrorDescriptionDTO error() {
        if (invalid == null && !response.body().asString().contentEquals("")) {
            invalid = response.as(TestErrorDescriptionDTO.class);
        }
        return invalid;
    }
}
