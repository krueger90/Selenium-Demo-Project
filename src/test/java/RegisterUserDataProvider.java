import java.util.HashMap;
import org.testng.annotations.DataProvider;
import net.datafaker.Faker;

public class RegisterUserDataProvider {

    private Faker faker = new Faker();

    @DataProvider
    public HashMap<String, String> createRandomUser() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put("firstName", faker.name().firstName());
        userData.put("lastName", faker.name().lastName());
        userData.put("email", faker.internet().emailAddress());
        userData.put("phone", faker.phoneNumber().phoneNumber());
        userData.put("password", faker.internet().password());

        return userData;
    }
}
