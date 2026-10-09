package data;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {}

    public static DeliveryData generateData(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String city = faker.address().cityName();
        String name = faker.name().lastName() + " " + faker.name().firstName();
        String phone = "+7 " + faker.number().digits(3) + " "
                + faker.number().digits(3) + " "
                + faker.number().digits(2) + " "
                + faker.number().digits(2);

        return DeliveryData.builder()
                .city(city)
                .date(generateDate(3))
                .name(name)
                .phone(phone)
                .build();
    }

    public static String generateDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}