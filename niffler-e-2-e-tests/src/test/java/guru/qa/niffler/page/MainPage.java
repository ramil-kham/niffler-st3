package guru.qa.niffler.page;

import io.qameta.allure.Allure;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    public MainPage clickNavigationItem(String itemname) {
        Allure.step("Выбрать меню " + itemname, () -> $("[data-tooltip-id='" + itemname + "']")
                .shouldBe(visible, enabled).click());
        return this;
    }
}
