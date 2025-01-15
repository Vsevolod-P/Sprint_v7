import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestCourierLogin {

    private CourierCredentials courierCredentials;

    @Before
    public void setUp() {
        // Создаем курьера перед каждым тестом
        courierCredentials = TestData.createCourier();
        given()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"" + courierCredentials.getLogin() + "\", \"password\": \"" + courierCredentials.getPassword() + "\", \"firstName\": \"Saske\"}")
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier");

    }

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/api/v1/courier/login";

    @Test
    public void courierCanLogin() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"" + courierCredentials.getLogin() + "\", \"password\": \"" + courierCredentials.getPassword() + "\"}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(200) // Успешный запрос
                .body("id", notNullValue()); // Проверка, что возвращается id
    }

    @Test
    public void loginWithInvalidCredentials() {
        String login = "invalidLogin";
        String password = "invalidPassword";

        given()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"" + login + "\", \"password\": \"" + password + "\"}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(404) // Код ошибки для неверных учетных данных
                .body("message", equalTo("Учетная запись не найдена")); // Сообщение об ошибке
    }

    @Test
    public void loginWithoutRequiredFields() {
        // Пример без логина
        given()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"\", \"password\": \"somePassword\"}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(400) // Код ошибки для отсутствующих обязательных полей
                .body("message", equalTo("Недостаточно данных для входа")); // Сообщение об ошибке
    }
}