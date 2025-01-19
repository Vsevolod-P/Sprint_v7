import com.google.gson.Gson;
import io.restassured.response.Response;

public class CourierApi extends BaseHttpClient {

    static final String apiPath = "/api/v1/courier";
    static final String apiPathLogin = "/api/v1/courier/login";
    static final String apiPathCourierId = "/api/v1/courier/:";
    private static final Gson gson = new Gson();

    public static Courier createCourier() {
        // Создаем уникальные данные для курьера
        String login = "testCour" + System.currentTimeMillis();
        String password = "password123";
        String firstName = "Naruto";

        return new Courier(login, password, firstName);
    }

    // Класс для представления данных для логина
    protected static class LoginRequest {
        private String login;
        private String password;

        public LoginRequest(String login, String password) {
            this.login = login;
            this.password = password;
        }
    }

    public Response createCourierRequest(Courier courier) {
        // Отправляем POST запрос для создания курьера
        return doPostRequest(apiPath, gson.toJson(courier));

    }
    public void deleteCourier(String login, String password) {
        // Логин для получения ID курьера
        LoginRequest loginRequest = new LoginRequest(login, password); //ничего лучше не придумал
        Response response = doPostRequest(apiPathLogin, gson.toJson(loginRequest));

        // Получаем ID курьера из ответа
        int courierId = response.jsonPath().getInt("id");

        // Удаляем курьера по ID
        doGetRequest(apiPathCourierId + courierId);
    }
}
