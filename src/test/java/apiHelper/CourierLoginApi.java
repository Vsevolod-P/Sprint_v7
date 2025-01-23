package apiHelper;

public class CourierLoginApi {
    // Класс для представления данных для логина
    public static class LoginRequest {
        private String login;
        private String password;

        public LoginRequest(String login, String password) {
            this.login = login;
            this.password = password;
        }
    }
}
