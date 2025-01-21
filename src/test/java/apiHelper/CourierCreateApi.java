package apiHelper;

public class CourierCreateApi {
    public static Courier createCourier() {
        // Создаем уникальные данные для курьера
        String login = "testCour" + System.currentTimeMillis();
        String password = "password123";
        String firstName = "Naruto";

        return new Courier(login, password, firstName);
    }
}
