import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiFetcher {

    public static void main(String[] args) {
        // URL API для запросов
        String usersApi = "https://fake-json-api.mock.beeceptor.com/users";
        String companiesApi = "https://fake-json-api.mock.beeceptor.com/companies";

        // Выполнение запросов и вывод данных
        System.out.println("Fetching Users:");
        fetchAndPrint(usersApi);

        System.out.println("\nFetching Companies:");
        fetchAndPrint(companiesApi);
    }

    // Метод для выполнения GET-запроса и вывода данных
    public static void fetchAndPrint(String apiUrl) {
        try {
            // Создание URL и соединения
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Чтение ответа
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Парсинг и вывод ответа
            if (response.toString().startsWith("[")) {
                // Если массив JSON
                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(jsonArray.getJSONObject(i).toString(4));
                }
            } else {
                // Если объект JSON
                JSONObject jsonObject = new JSONObject(response.toString());
                System.out.println(jsonObject.toString(4));
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
