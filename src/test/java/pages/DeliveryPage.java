package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import data.DeliveryData;
import org.openqa.selenium.Keys;

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
        // ГОРОД — очищаем через JS, вводим и кликаем по подсказке
        Selenide.executeJavaScript(
                "arguments[0].value = '';" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));",
                cityInput);
        cityInput.click();
        cityInput.sendKeys(data.getCity());
        Selenide.sleep(700);
        // Если появилась подсказка — кликаем по первой
        if (!$$(".menu-item__control").isEmpty()) {
            $$(".menu-item__control").first().click();
        }

        // ДАТА — очищаем через JS, вводим
        Selenide.executeJavaScript(
                "arguments[0].value = '';" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));",
                dateInput);
        dateInput.click();
        dateInput.sendKeys(data.getDate());
        dateInput.sendKeys(Keys.TAB);

        // ИМЯ — очищаем через JS
        Selenide.executeJavaScript("arguments[0].value = ''", nameInput);
        nameInput.click();
        nameInput.sendKeys(data.getName());

        // ТЕЛЕФОН — очищаем через JS
        Selenide.executeJavaScript("arguments[0].value = ''", phoneInput);
        phoneInput.click();
        phoneInput.sendKeys(data.getPhone());

        // ГАЛОЧКА — через JS-клик, он надёжнее
        Selenide.executeJavaScript("arguments[0].click();", agreementCheckbox);
    }

    public void submitForm() {
        submitButton.click();
    }

    public void verifySuccessNotification(String expectedDate) {
        successNotification.shouldBe(visible).shouldHave(text("Успешно!"));
        successNotification.shouldHave(text(expectedDate));
    }

    public void verifyReplanModal() {
        replanModal.shouldBe(visible, java.time.Duration.ofSeconds(10))
                .shouldHave(text("Необходимо подтверждение"));
    }

    public void clickReplan() {
        replanButton.click();
    }
}