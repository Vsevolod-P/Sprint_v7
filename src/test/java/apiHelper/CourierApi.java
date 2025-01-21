package apiHelper;
import com.google.gson.Gson;
import io.restassured.response.Response;

public class CourierApi extends BaseHttpClient {

    public static final String API_PATH = "/api/v1/courier";
    public static final String API_LOGIN_PATH = "/api/v1/courier/login";
    public static final String API_COURIER_PATH = "/api/v1/courier/:";
    private static final Gson gson = new Gson();



    public Response createCourierRequest(Courier courier) {
        // Отправляем POST запрос для создания курьера
        return doPostRequest(API_PATH, gson.toJson(courier));

    }
    public void deleteCourier(String login, String password) {
        // Логин для получения ID курьера
        CourierLoginApi.LoginRequest loginRequest = new CourierLoginApi.LoginRequest(login, password); //ничего лучше не придумал
        Response response = doPostRequest(API_LOGIN_PATH, gson.toJson(loginRequest));

        // Получаем ID курьера из ответа
        int courierId = response.jsonPath().getInt("id");

        // Удаляем курьера по ID
        doGetRequest(API_COURIER_PATH + courierId);
    }
}
