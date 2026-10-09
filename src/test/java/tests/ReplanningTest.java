package tests;

import com.codeborne.selenide.Configuration;
import data.DataGenerator;
import data.DeliveryData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.DeliveryPage;

import static com.codeborne.selenide.Selenide.open;

public class ReplanningTest {
    private final DeliveryPage deliveryPage = new DeliveryPage();

    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1920x1080";
        open("http://localhost:9999");
    }

    @Test
    void shouldReplanDeliveryDate() {
        // Шаг 1: заполняем форму ПЕРВЫЙ раз
        DeliveryData firstData = DataGenerator.generateData("ru");
        deliveryPage.fillForm(firstData);
        deliveryPage.submitForm();
        deliveryPage.verifySuccessNotification(firstData.getDate());

        // Шаг 2: заполняем форму ВТОРОЙ раз ТЕМИ ЖЕ данными,
        // но с ДРУГОЙ датой (как и сказано в задании)
        DeliveryData secondData = DeliveryData.builder()
                .city(firstData.getCity())      // тот же город
                .date(DataGenerator.generateDate(7))  // другая дата
                .name(firstData.getName())      // то же имя
                .phone(firstData.getPhone())    // тот же телефон
                .build();

        deliveryPage.fillForm(secondData);
        deliveryPage.submitForm();

        // Шаг 3: проверяем, что появилось модальное окно перепланирования
        deliveryPage.verifyReplanModal();

        // Шаг 4: нажимаем «Перепланировать»
        deliveryPage.clickReplan();

        // Шаг 5: проверяем, что встреча успешно перепланирована
        deliveryPage.verifySuccessNotification(secondData.getDate());
    }
}