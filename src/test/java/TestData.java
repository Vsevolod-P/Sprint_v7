import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestData {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/api/v1/courier";

    public static CourierCredentials createCourier() {
        // Создаем уникальные данные для курьера
        String login = "testCour" + System.currentTimeMillis();
        String password = "password123";

        return new CourierCredentials(login, password);
    }

    public static void deleteCourier(String login, String password) {
        // Логин для получения ID курьера
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"login\": \"" + login + "\", \"password\": \"" + password + "\"}")
                .post(BASE_URL + "/login");

        // Получаем ID курьера из ответа
        int courierId = response.jsonPath().getInt("id");

        // Удаляем курьера по ID
        RestAssured.given()
                .contentType(ContentType.JSON)
                .delete(BASE_URL + "/:" + courierId);
    }
}