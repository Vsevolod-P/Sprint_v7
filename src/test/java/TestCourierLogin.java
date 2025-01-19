import com.google.gson.Gson;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class TestCourierLogin {

    private Courier courier;
    private CourierApi CourierApi;
    private final Gson gson = new Gson();

    @Before
    public void setUp() {
        // Создаем курьера перед каждым тестом
        CourierApi = new CourierApi();
        courier = CourierApi.createCourier();
        CourierApi.createCourierRequest(courier); // Создаем курьера через API
    }

    @After
    public void tearDown() {
        // Удаляем курьера после теста
        CourierApi.deleteCourier(courier.getLogin(), courier.getPassword());
    }

    @Test
    @Description("Проверка ручки логина /api/v1/courier/login")
    @Step("Возможность логина")
    public void courierCanLogin() {
        // Создаем объект для логина
        CourierApi.LoginRequest loginRequest = new CourierApi.LoginRequest(courier.getLogin(), courier.getPassword());
        Response response = CourierApi.doPostRequest(CourierApi.apiPathLogin, loginRequest);

        response.then()
                .statusCode(200) // Успешный запрос
                .body("id", notNullValue()); // Проверка, что возвращается id
    }
    @Test
    @Description("Проверка не валидных данных")
    @Step("Ввод не правильных данных")
    public void loginWithInvalidCredentials() {
        String login = "invalidLogin";
        String password = "invalidPassword";

        CourierApi.LoginRequest invalidLoginRequest = new CourierApi.LoginRequest(login, password);

        Response response = CourierApi.doPostRequest(CourierApi.apiPathLogin, invalidLoginRequest);

        response.then()
                .statusCode(404) // Код ошибки для неверных учетных данных
                .body("message", equalTo("Учетная запись не найдена")); // Сообщение об ошибке
    }
    @Test
    @Description("Проверка пустых полей")
    @Step("Логин с пустым логином")
    public void loginWithoutRequiredFields() {
        CourierApi.LoginRequest loginRequest = new CourierApi.LoginRequest("", "somePassword");

        Response response = CourierApi.doPostRequest(CourierApi.apiPathLogin, loginRequest);

        response.then()
                .statusCode(400) // Код ошибки для отсутствующих обязательных полей
                .body("message", equalTo("Недостаточно данных для входа")); // Сообщение об ошибке
    }
}