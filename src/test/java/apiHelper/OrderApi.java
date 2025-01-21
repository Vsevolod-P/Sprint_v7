package apiHelper;

import io.restassured.response.Response;

public class OrderApi extends BaseHttpClient {
    static final String API_ORDER_PATH = "/api/v1/orders";

    public Response createOrder(Order order) {
        return doPostRequest(API_ORDER_PATH, order);
    }

    public Response getOrderList(int limit, int page) {
        String path = "?limit=" + limit + "&page=" + page;
        return doGetRequest(path); // Используем метод из BaseHttpClient
    }
}

