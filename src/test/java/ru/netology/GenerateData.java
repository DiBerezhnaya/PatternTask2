package ru.netology;

import com.github.javafaker.Faker;

import static ru.netology.Request.registration;

public class GenerateData {
    public static String getRandomLogin() {
        Faker faker = new Faker();
       return faker.name().username();
    }

    public static String getRandomPassword() {
        Faker faker = new Faker();
        return faker.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        public static LoginDetailsInfo getUser(String status) {
            var user = new LoginDetailsInfo(getRandomLogin(), getRandomPassword(), status);
            return user;
        }

        public static LoginDetailsInfo getRegisteredUser(String status) {
            var registeredUser = getUser(status);
            registration(registeredUser);
            return registeredUser;
        }
    }

}
