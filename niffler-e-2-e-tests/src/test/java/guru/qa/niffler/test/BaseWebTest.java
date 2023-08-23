package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.WebTest;
import guru.qa.niffler.model.UserJson;

import static com.codeborne.selenide.Selenide.$;

@WebTest
public class BaseWebTest {

    public void logIn(UserJson userForTest) {
        Selenide.open("http://127.0.0.1:3000/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(userForTest.getUsername());
        $("input[name='password']").setValue(userForTest.getPassword());
        $("button[type='submit']").click();
    }
}
