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
        Configuration.browser = "chrome";
        Configuration.headless = Boolean.parseBoolean(
                System.getProperty("selenide.headless", "false")
        );

        // Флаги Chrome для работы в CI (Ubuntu/GitHub Actions)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-setuid-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-zygote");
        options.addArguments("--single-process");
        options.addArguments("--remote-allow-origins=*");
        Configuration.browserCapabilities = options;

        open("http://localhost:9999");
    }

    @Test
    void shouldReplanDeliveryDate() {
        // ========== ПЕРВАЯ ЗАЯВКА ==========
        DeliveryData firstData = DataGenerator.generateData("ru");
        deliveryPage.fillForm(firstData);
        deliveryPage.submitForm();
        deliveryPage.verifySuccessNotification(firstData.getDate());

        // Пауза, чтобы приложение обработало первую заявку и уведомление исчезло
        Selenide.sleep(7000);

        // ========== ВТОРАЯ ЗАЯВКА ==========
        // Перезагружаем страницу, чтобы поля были чистыми (иначе данные дублируются)
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