package tests;


import apiHelper.Order;
import apiHelper.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestOrderCreation {

    private OrderApi orderApi = new OrderApi(); // Создаем экземпляр OrderApi

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
    @Description("Создаем заказ и проверяем что не пустой")
    @Step("Проверяем что при создании заказа, получили трек-номер")
    public void createOrderWithColors() {
        Order order = new Order(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come f**k you",
                color
        );

        Response response = orderApi.createOrder(order); // Используем метод из OrderApi

        response.then()
                .statusCode(201) // Код ответа 201 для успешного создания
                .body("track", notNullValue()); // Проверка, что поле track присутствует в ответе
    }
}