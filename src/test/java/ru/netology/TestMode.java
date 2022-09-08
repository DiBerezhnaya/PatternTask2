package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.GenerateData.generateInfo;
import static ru.netology.Request.registration;

public class TestMode {

    LoginDetailsInfo active = generateInfo(true);
    LoginDetailsInfo blocked = generateInfo(false);
    LoginDetailsInfo notRegistration = generateInfo(true);

    @BeforeClass
    public void whetherAuthorized() {
        registration(active);
        registration(blocked);
    }

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }


    @Test
    public void lottoResourceReturns200withActive() {

        $("[type='text']").val(active.getLogin());
        $("[type='password']").val(active.getPassword());
        $("[class='button__text']").click();
//        $x("//div[@class='notification__content']")
//                .shouldBe(visible)
//                .should(text("Неверно указан логин или пароль"));
    }

    @Test
    public void lottoResourceReturns400withBlocked() {

        $("[type='text']").val(blocked.getLogin());
        $("[type='password']").val(blocked.getPassword());
        $("[class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldBe(visible)
                .should(text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldNoAuthNotRegistrationUser() {
        $("[type='text']").val(notRegistration.getLogin());
        $("[type='password']").val(notRegistration.getPassword());
        $("[class='button__text']").click();
        $x("//div[@data-test-id='error-notification']").should(visible);
        $x(".//div[@class='notification__content']").should(text("Неверно указан логин или пароль"));
        $x(".//button").click();
        $x("//div[@data-test-id='error-notification']").should(hidden);
    }

}
