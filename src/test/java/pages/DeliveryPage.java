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

    public void fillForm(DeliveryData data) {
        Selenide.executeJavaScript(
                "arguments[0].value = '';" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));",
                cityInput);
        cityInput.click();
        cityInput.sendKeys(data.getCity());
        Selenide.sleep(700);
        if (!$$(".menu-item__control").isEmpty()) {
            $$(".menu-item__control").first().click();
        }

        Selenide.executeJavaScript(
                "arguments[0].value = '';" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));",
                dateInput);
        dateInput.click();
        dateInput.sendKeys(data.getDate());
        dateInput.sendKeys(Keys.TAB);

        Selenide.executeJavaScript("arguments[0].value = ''", nameInput);
        nameInput.click();
        nameInput.sendKeys(data.getName());

        Selenide.executeJavaScript("arguments[0].value = ''", phoneInput);
        phoneInput.click();
        phoneInput.sendKeys(data.getPhone());

        Selenide.executeJavaScript("arguments[0].click();", agreementCheckbox);
    }

    public void submitForm() {
        submitButton.click();
    }

    public void verifyReplanModal() {
        replanModal.should(exist, java.time.Duration.ofSeconds(15));
    }

    public void clickReplan() {
        replanButton.click();
    }
}