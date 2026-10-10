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
        cityInput.setValue(data.getCity());
        dateInput.setValue(data.getDate());
        nameInput.setValue(data.getName());
        phoneInput.setValue(data.getPhone());
        agreementCheckbox.click();
    }

    public void submitForm() {
        submitButton.click();
    }

    public void verifySuccessNotification(String expectedDate) {
        successNotification.shouldBe(visible).shouldHave(text("Успешно!"));
        successNotification.shouldHave(text(expectedDate));
    }

    public void waitForNotificationDisappear() {
        successNotification.shouldBe(disappear, java.time.Duration.ofSeconds(15));
    }

    public void verifyReplanModal() {
        replanModal.shouldBe(visible).shouldHave(text("Необходимо подтверждение"));
    }

    public void clickReplan() {
        replanButton.click();
    }
}