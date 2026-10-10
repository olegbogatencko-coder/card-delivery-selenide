package tests;

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
        open("http://localhost:9999");
    }

    @Test
    void shouldReplanDeliveryDate() {
        // ========== ПЕРВАЯ ЗАЯВКА ==========
        DeliveryData firstData = DataGenerator.generateData("ru");
        deliveryPage.fillForm(firstData);
        deliveryPage.submitForm();
        deliveryPage.verifySuccessNotification(firstData.getDate());
        deliveryPage.waitForNotificationDisappear();

        // ========== ВТОРАЯ ЗАЯВКА ==========
        open("http://localhost:9999");
        DeliveryPage page2 = new DeliveryPage();

        DeliveryData secondData = DeliveryData.builder()
                .city(firstData.getCity())
                .date(DataGenerator.generateDate(7))
                .name(firstData.getName())
                .phone(firstData.getPhone())
                .build();

        page2.fillForm(secondData);
        page2.submitForm();

        // ========== ГЛАВНАЯ ПРОВЕРКА: модалка перепланирования ==========
        page2.verifyReplanModal();
        page2.clickReplan();
    }
}