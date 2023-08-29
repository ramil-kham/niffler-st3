package guru.qa.niffler.page;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class FriendsPage {
    public FriendsPage friendDataShouldBeVisible() {
        Allure.step("Отображается аватар друга", () ->
                $(By.className("people__user-avatar")).shouldBe(visible));

        Allure.step("Отображаются уведомление о дружбе", () ->
                $(By.className("abstract-table__buttons")).shouldBe(visible));

        Allure.step("Отображаются иконка удаления дружбы", () ->
                $(("[data-tooltip-id='remove-friend'] button")).shouldBe(visible, enabled));
        return this;
    }
}
