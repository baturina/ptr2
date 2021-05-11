package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void setUpNewUser(User registration) {
        given()
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(registration) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static User getRegisteredUser(String status) {
        String login = faker.name().name();
        String password = faker.internet().password();
        User registration = new User(login, password, status);
        setUpNewUser(registration);
        return registration;
    }

    public static String getRandomLogin() {
        return faker.name().name();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }
}



