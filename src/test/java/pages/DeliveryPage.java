package pages;

import com.codeborne.selenide.SelenideElement;
import data.DeliveryData;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

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
        // ГОРОД — вводим полное название (Faker генерирует настоящие города РФ)
        cityInput.clear();
        cityInput.setValue(data.getCity());

        // ДАТА
        dateInput.doubleClick();
        dateInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));
        dateInput.sendKeys(org.openqa.selenium.Keys.DELETE);
        dateInput.sendKeys(data.getDate());

        // ИМЯ
        nameInput.clear();
        nameInput.setValue(data.getName());

        // ТЕЛЕФОН
        phoneInput.clear();
        phoneInput.setValue(data.getPhone());

        // ГАЛОЧКА
        if (!agreementCheckbox.isSelected()) {
            agreementCheckbox.click();
        }
    }

    public void submitForm() {
        submitButton.click();
    }

    public void verifySuccessNotification(String expectedDate) {
        successNotification.shouldBe(visible).shouldHave(text("Успешно!"));
        successNotification.shouldHave(text(expectedDate));
    }

    public void verifyReplanModal() {
        replanModal.shouldBe(visible)
                .shouldHave(text("Необходимо подтверждение"))
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
    }

    public void clickReplan() {
        replanButton.click();
    }
}