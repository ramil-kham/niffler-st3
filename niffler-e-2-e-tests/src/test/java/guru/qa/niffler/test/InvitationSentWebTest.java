package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.User;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.AllPeoplePage;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.MainPage;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvitationSentWebTest extends BaseWebTest {
    @BeforeEach
    void doLogin(@User(userType = User.UserType.INVITATION_SENT) UserJson userForTest) {
        logIn(userForTest);
        MainPage mainPage= new MainPage();
        mainPage.clickNavigationItem("people");
    }

    @Test
    @AllureId("102")
    void checkDisplayingFriendData(@User(userType = User.UserType.INVITATION_SENT) UserJson userForTest) {
        AllPeoplePage allPeoplePage = new AllPeoplePage();
        allPeoplePage.pendingInvitationStatusShouldBeVisible();
    }
}
