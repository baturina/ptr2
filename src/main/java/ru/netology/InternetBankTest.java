package ru.netology;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class InternetBankTest {

    private static Faker faker = new Faker(new Locale("en"));

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldRedistrationActive() {
        User newUser = DataGenerator.getRegisteredUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(newUser.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(newUser.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldRedistrationBlocked() {
        User newUser = DataGenerator.getRegisteredUser("blocked");
        $("[data-test-id=login] [class = input__control]").setValue(newUser.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(newUser.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldRedistrationInvalidLogin() {
        User newUser = DataGenerator.getRegisteredUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(DataGenerator.getRandomLogin());
        $("[data-test-id=password] [class = input__control]").setValue(newUser.getPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldRedistrationInvalidPassword() {
        User newUser = DataGenerator.getRegisteredUser("active");
        $("[data-test-id=login] [class = input__control]").setValue(newUser.getLogin());
        $("[data-test-id=password] [class = input__control]").setValue(DataGenerator.getRandomPassword());
        $$("button").find(exactText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
