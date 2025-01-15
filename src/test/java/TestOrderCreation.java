import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestOrderCreation {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/api/v1/orders";

    @Parameterized.Parameter
    public String color;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"BLACK,GREY"},
                {""} // Пустая строка для случая, когда цвет не указан
        });
    }

    @Test
    public void createOrderWithColors() {
        String requestBody = String.format(
                "{ " +
                        "\"firstName\": \"Naruto\", " +
                        "\"lastName\": \"Uchiha\", " +
                        "\"address\": \"Konoha, 142 apt.\", " +
                        "\"metroStation\": 4, " +
                        "\"phone\": \"+7 800 355 35 35\", " +
                        "\"rentTime\": 5, " +
                        "\"deliveryDate\": \"2020-06-06\", " +
                        "\"comment\": \"Saske, come back to Konoha\", " +
                        "\"color\": [%s] " +
                        "}",
                color.isEmpty() ? "" : "\"" + color.replace(",", "\",\"") + "\""
        );

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201) // Код ответа 201 для успешного создания
                .body("track", notNullValue()); // Проверка, что поле track присутствует в ответе
    }
}
