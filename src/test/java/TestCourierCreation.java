import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class TestCourierCreation {

    private Courier courier;
    private CourierApi CourierApi;
    private boolean isCourierCreated;

    @Before
    public void setUp() {
        // Создаем курьера перед каждым тестом
        CourierApi = new CourierApi();
        courier = CourierApi.createCourier();
        isCourierCreated = false;
    }
   // Убрал, т.к. тут мы проверяем только одно создание курьера, в остальных тестах курьер не создается
    @After
    public void tearDown() {
        // Удаляем курьера после теста
        if (isCourierCreated) {
            CourierApi.deleteCourier(courier.getLogin(), courier.getPassword());
        }
    }

    @Test
    @Description("Проверка ручки создания курьера /api/v1/courier")
    @Step("Создаем курьера")
    public void courierCanBeCreated() {
        Response response = CourierApi.createCourierRequest(courier);

        response.then()
                .statusCode(201) // Проверяем, что статус ответа 201 (Created)
                .body("ok", equalTo(true)); // Проверяем, что в ответе есть поле "ok" со значением true
        isCourierCreated = true; // ловим созданного курьера
    }
    @Test
    @Step("Попытка создания курьера с пустым логином")
    public void cannotCreateCourierWithoutRequiredFields() {
        // Пример без логина
        Courier emptyCourier = new Courier("", "Password123", "Saske");
        Response response = CourierApi.createCourierRequest(emptyCourier);
        response.then()
                .statusCode(400) // Проверяем, что статус ответа 400
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }
    @Test
    @Step("Создание курьера с существующим логином")
    public void cannotCreateCourierWithExistingLogin() {
        // Пытаемся создать курьера с тем же логином
        Response response = CourierApi.createCourierRequest(courier);
        //создаем первого курьера
        response.then()
                .statusCode(201) // Проверяем, что статус ответа 201 (Created)
                .body("ok", equalTo(true));
        Response responseDublicate = CourierApi.createCourierRequest(courier);

        responseDublicate.then()
                .statusCode(409) // Проверяем, что статус ответа 409
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        isCourierCreated = true; // ловим созданного курьера
    }
}
