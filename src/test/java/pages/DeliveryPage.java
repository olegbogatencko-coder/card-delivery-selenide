package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import data.DeliveryData;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DeliveryPage {
    private final SelenideElement cityInput = $("[data-test-id='city'] input");
    private final SelenideElement dateInput = $("[data-test-id='date'] input");
    private final SelenideElement nameInput = $("[data-test-id='name'] input");
    private final SelenideElement phoneInput = $("[data-test-id='phone'] input");
    private final SelenideElement agreementCheckbox = $("[data-test-id='agreement']");
    private final SelenideElement submitButton = $("button.button");

    private final SelenideElement replanModal = $("[data-test-id='replan-notification']");
    private final SelenideElement replanButton = replanModal.$("button.button");
    private final SelenideElement successNotification = $("[data-test-id='success-notification']");

    public void fillForm(DeliveryData data) {
        // ГОРОД — вводим по одной букве, потом ждём подсказку
        cityInput.click();
        cityInput.sendKeys(data.getCity());
        Selenide.sleep(800);
        // Кликаем по подсказке, если она появилась
        if (!$$(".menu-item__control").isEmpty()) {
            $$(".menu-item__control").first().click();
        }

        // ДАТА — двойной клик, чтобы открыть календарь, потом вводим текст
        dateInput.click();
        dateInput.press(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));
        dateInput.press(org.openqa.selenium.Keys.DELETE);
        dateInput.sendKeys(data.getDate());
        dateInput.press(org.openqa.selenium.Keys.TAB);

        // ИМЯ
        nameInput.click();
        nameInput.sendKeys(data.getName());

        // ТЕЛЕФОН
        phoneInput.click();
        phoneInput.sendKeys(data.getPhone());

        // ГАЛОЧКА — через JavaScript-клик, чтобы обойти все нюансы
        Selenide.executeJavaScript("arguments[0].click();", agreementCheckbox);
    }

    public void submitForm() {
        submitButton.click();
    }

    public void verifySuccessNotification(String expectedDate) {
        successNotification.shouldBe(visible, java.time.Duration.ofSeconds(15))
                .shouldHave(text("Успешно!"));
    }

    public void verifyReplanModal() {
        replanModal.shouldBe(visible, java.time.Duration.ofSeconds(15))
                .shouldHave(text("Необходимо подтверждение"));
    }

    public void clickReplan() {
        replanButton.click();
    }
}