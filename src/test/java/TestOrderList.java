import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestOrderList {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/api/v1/orders?limit=5&page=0";

    @Test
    public void shouldReturnListOfOrders() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(200) // Проверка, что статус ответа 200 OK
                .body("orders", notNullValue());
    }
}