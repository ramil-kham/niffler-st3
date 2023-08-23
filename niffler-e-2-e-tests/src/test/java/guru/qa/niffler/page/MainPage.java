package guru.qa.niffler.page;

import io.qameta.allure.Allure;

import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    public MainPage clickNavigationItem(String itemname) {
        Allure.step("Выбрать меню " + itemname, () -> $("[data-tooltip-id='" + itemname + "']").click());
        return this;
    }
}
