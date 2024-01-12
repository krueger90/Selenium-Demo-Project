import org.testng.annotations.Factory;
import net.datafaker.Faker;

public class RegisterUserFactory {

    private static Faker faker = new Faker();

    @Factory
    public static Object[] factory() {
        return new Object[] {
                new RegisterUserTest(
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.internet().emailAddress(),
                        faker.phoneNumber().phoneNumber(),
                        faker.internet().password())
        };
    }
}
