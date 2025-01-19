import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class TestOrderList {

    private OrderApi orderApi = new OrderApi(); // Создаем экземпляр OrderApi

    @Test
    @Description("Получение не пустого списка")
    @Step("Просто проверяем что получили 200 и какие-то данные")
    public void shouldReturnListOfOrders() {
        Response response = orderApi.getOrderList(5, 0); // Получаем список заказов

        response.then()
                .statusCode(200) // Проверка, что статус ответа 200 OK
                .body("orders", notNullValue()); // Проверка, что поле "orders" присутствует в ответе
    }
}