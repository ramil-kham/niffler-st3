package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.$$;

public class AllPeoplePage {

    public SelenideElement neededRow(String username) {
        return $$((".abstract-table tbody tr"))
                .find(text(username));
    }

    public AllPeoplePage pendingInvitationStatusShouldBeVisible() {
        Allure.step("Отображается аватар друга", () ->
                neededRow("valentin").$(By.className("people__user-avatar")).shouldBe(visible));

        Allure.step("Для пользователя valentin отображается статус 'Pending invitation'", () ->
                neededRow("valentin").$(byClassName("abstract-table__buttons"))
                        .shouldHave(Condition.text("Pending invitation")));
        return this;
    }

    public AllPeoplePage displayingInvitationReceivedStatus() {
        Allure.step("Отображается аватар друга", () ->
                neededRow("bee").$(By.className("people__user-avatar")).shouldBe(visible));

        Allure.step("Для пользователя bee отображается галочка для добавления друга", () ->
                neededRow("bee")
                        .$(".button-icon_type_submit")
                        .shouldBe(visible, enabled));

        Allure.step("Для пользователя bee отображается кнопка для отклонения предложения о дружбе", () ->
                neededRow("bee").$(".button-icon_type_close")
                        .shouldBe(visible, enabled));
        return this;
    }
}
