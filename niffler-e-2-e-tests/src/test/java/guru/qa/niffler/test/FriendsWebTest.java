package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.User;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.MainPage;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.jupiter.User.UserType.INVITATION_SENT;
import static guru.qa.niffler.jupiter.User.UserType.WITH_FRIENDS;

public class FriendsWebTest extends BaseWebTest {

    @BeforeEach
    void doLogin(
            @User(userType = WITH_FRIENDS) UserJson userWithFriends,
            @User(userType = INVITATION_SENT) UserJson userInvitationSent
    ) {
        logIn(userWithFriends);
        MainPage mainPage = new MainPage();
        mainPage.clickNavigationItem("friends");
        System.out.println(userWithFriends.getUsername());
        System.out.println(userInvitationSent.getUsername());
    }

    @Test
    @AllureId("101")
    void checkDisplayingFriendData(
            @User(userType = WITH_FRIENDS) UserJson userForTest,
            @User(userType = INVITATION_SENT) UserJson userForTestAnother
    ) {
        FriendsPage friendsPage = new FriendsPage();
        friendsPage.friendDataShouldBeVisible();
        System.out.println(userForTest.getUsername());
        System.out.println(userForTestAnother.getUsername());
    }
}
