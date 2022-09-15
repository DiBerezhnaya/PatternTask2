package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.GenerateData.Registration.getRegisteredUser;
import static ru.netology.GenerateData.Registration.getUser;
import static ru.netology.GenerateData.getRandomLogin;
import static ru.netology.GenerateData.getRandomPassword;

public class TestMode {

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[type='text']").val(registeredUser.getLogin());
        $("[type='password']").val(registeredUser.getPassword());
        $("[class='button__text']").click();
        $("h2").should(text("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[type='text']").val(notRegisteredUser.getLogin());
        $("[type='password']").val(notRegisteredUser.getPassword());
        $("[class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldBe(visible)
                .should(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[type='text']").val(blockedUser.getLogin());
        $("[type='password']").val(blockedUser.getPassword());
        $("[class='button__text']").click();
        $x("//div[@class='notification__content']")
               .shouldBe(visible)
                .should(text("Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[type='text']").val(wrongLogin);
        $("[type='password']").val(registeredUser.getPassword());
        $("[class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldBe(visible)
                .should(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[type='text']").val(registeredUser.getLogin());
        $("[type='password']").val(wrongPassword);
        $("[class='button__text']").click();
        $x("//div[@class='notification__content']")
                .shouldBe(visible)
                .should(text("Неверно указан логин или пароль"));
    }
}
