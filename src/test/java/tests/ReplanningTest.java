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
        DeliveryData data = DataGenerator.generateData("ru");
        String firstDate = DataGenerator.generateDate(3);
        String secondDate = DataGenerator.generateDate(7);

        // ========== ПЕРВАЯ ЗАЯВКА ==========
        deliveryPage.fillForm(data, firstDate);
        deliveryPage.submitForm();
        deliveryPage.verifySuccessNotification(firstDate);
        deliveryPage.waitForNotificationDisappear();

        // ========== ВТОРАЯ ЗАЯВКА (без перезагрузки страницы!) ==========
        // Просто меняем дату на уже заполненной форме
        deliveryPage.changeDate(secondDate);
        deliveryPage.submitForm();

        // Проверяем модалку, перепланируем
        deliveryPage.verifyReplanModal();
        deliveryPage.clickReplan();

        // ========== ПРОВЕРКА ВТОРОГО УВЕДОМЛЕНИЯ (как просил учитель) ==========
        deliveryPage.verifySuccessNotification(secondDate);
    }
}