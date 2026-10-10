package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import data.DataGenerator;
import data.DeliveryData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.DeliveryPage;

import static com.codeborne.selenide.Selenide.open;

public class ReplanningTest {
    private final DeliveryPage deliveryPage = new DeliveryPage();

    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1920x1080";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        Configuration.browserCapabilities = options;

        open("http://localhost:9999");
    }

    @Test
    void shouldReplanDeliveryDate() {
        // ========== ПЕРВАЯ ЗАЯВКА ==========
        DeliveryData firstData = DataGenerator.generateData("ru");
        deliveryPage.fillForm(firstData);
        deliveryPage.submitForm();

        // Пауза, чтобы приложение обработало первую заявку
        Selenide.sleep(5000);

        // ========== ВТОРАЯ ЗАЯВКА С НОВОЙ ДАТОЙ ==========
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

        Selenide.sleep(2000);
    }
}