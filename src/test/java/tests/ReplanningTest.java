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
        Configuration.headless = Boolean.parseBoolean(
                System.getProperty("selenide.headless", "false")
        );

        // ВАЖНО: на CI (Ubuntu) Chrome требует флаги --no-sandbox и --disable-dev-shm-usage,
        // иначе падает с SessionNotCreatedException
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        Configuration.browserCapabilities = options;

        open("http://localhost:9999");
    }

    @Test
    void shouldReplanDeliveryDate() {
        // Первая заявка
        DeliveryData firstData = DataGenerator.generateData("ru");
        deliveryPage.fillForm(firstData);
        deliveryPage.submitForm();
        deliveryPage.verifySuccessNotification(firstData.getDate());

        Selenide.sleep(7000);

        // Вторая заявка — на обновлённой странице
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

        page2.verifyReplanModal();
        page2.clickReplan();
        page2.verifySuccessNotification(secondData.getDate());
    }
}