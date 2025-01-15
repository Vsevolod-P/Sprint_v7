import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestCourierCreation {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/api/v1/courier";
    private CourierCredentials courierCredentials;

    @Before
    public void setUp() {
        // Получаем данные для создания курьера
        courierCredentials = TestData.createCourier();
    }

    @Test
    @Description("Создание курьера")
    public void courierCanBeCreated() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"" + courierCredentials.getLogin() + "\", \"password\": \"" + courierCredentials.getPassword() + "\", \"firstName\": \"Saske\"}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201) // Код ответа для успешного создания
                .body("ok", equalTo(true)); // Проверка, что возвращается ok: true
        //Удаляем курьера
        TestData.deleteCourier(courierCredentials.getLogin(), courierCredentials.getPassword());

    }


    @Test
    @Description("Создание курьера с пустым логином")
    public void cannotCreateCourierWithoutRequiredFields() {
        // Пример без логина
        given()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"\", \"password\": \"Password123\", \"firstName\": \"Saske\"}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(400) // Код ошибки для отсутствующих обязательных полей
                .body("message", equalTo("Недостаточно данных для создания учетной записи")); // Сообщение об ошибке
    }

    @Test
    @Description("Создание курьера с существующим логином")
    public void cannotCreateCourierWithExistingLogin() {
        // Создаем курьера
        given()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"" + courierCredentials.getLogin() + "\", \"password\": \"" + courierCredentials.getPassword() + "\", \"firstName\": \"Saske\"}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(201); // Успешное создание
        // Пытаемся создать курьера с тем же логином
        given()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"" + courierCredentials.getLogin() + "\", \"password\": \"" + courierCredentials.getPassword() + "\", \"firstName\": \"Saske\"}")
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(409) // Код ошибки для дублирующего курьера
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой.")); // Сообщение об ошибке
        //Удаляем курьера
        TestData.deleteCourier(courierCredentials.getLogin(), courierCredentials.getPassword());
    }
}
