package ru.netology;

import com.github.javafaker.Faker;

public class GenerateData {

    public static LoginDetailsInfo generateInfo(boolean status) {
        return new LoginDetailsInfo(Login(), Password(), userStatus(status));
    }

    public static String Login() {
        Faker faker = new Faker();
        return faker.name().username();
    }

    public static String Password() {
        Faker faker = new Faker();
        return faker.internet().password();
    }

    public static String userStatus(boolean status) {
        if (status) {
            return "active";
        } else {
            return "blocked";
        }
    }
}
