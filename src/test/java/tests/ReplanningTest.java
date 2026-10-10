package tests;

import com.codeborne.selenide.Selenide;
import data.DataGenerator;
import data.DeliveryData;
import org.junit.jupiter.api.Test;
import pages.DeliveryPage;

import static com.codeborne.selenide.Selenide.open;

public class ReplanningTest {

    @Test
    void shouldReplanDeliveryDate() {
        // ========== ПЕРВАЯ ЗАЯВКА ==========
        open("http://localhost:9999");
        DeliveryPage page1 = new DeliveryPage();

        DeliveryData firstData = DataGenerator.generateData("ru");
        page1.fillForm(firstData);
        page1.submitForm();
        page1.verifySuccessNotification(firstData.getDate());

        // Ждём, чтобы уведомление исчезло, и приложение обработало первую заявку
        Selenide.sleep(7000);

        // ========== ВТОРАЯ ЗАЯВКА (на обновлённой странице) ==========
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

        // Проверяем модалку перепланирования
        page2.verifyReplanModal();

        // Нажимаем «Перепланировать»
        page2.clickReplan();

        // Проверяем успех
        page2.verifySuccessNotification(secondData.getDate());
    }
}