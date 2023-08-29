package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.User;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.AllPeoplePage;
import guru.qa.niffler.page.MainPage;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvitationReceivedWebTest extends BaseWebTest {
    @BeforeEach
    void doLogin(@User(userType = User.UserType.INVITATION_RECEIVED) UserJson userForTest) {
        logIn(userForTest);
        MainPage mainPage= new MainPage();
        mainPage.clickNavigationItem("people");
    }

    @Test
    @AllureId("103")
    void checkDisplayingFriendData(@User(userType = User.UserType.INVITATION_RECEIVED) UserJson userForTest) {
        AllPeoplePage allPeoplePage = new AllPeoplePage();
        allPeoplePage.displayingInvitationReceivedStatus();
    }
}
