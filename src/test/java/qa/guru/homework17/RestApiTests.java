package qa.guru.homework17;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class RestApiTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void successfulGetPage2() {
        given()
                .contentType(JSON)
                .when()
                .get("api/users?page=2")
                .then()
                .statusCode(200);
    }

    @Test
    void checkPage2ForUserEmail() {
        String response = given()
                .contentType(JSON)
                .when()
                .get("api/users?page=2")
                .then()
                .extract().response().asString();
        Assertions.assertTrue(response.contains("tobias.funke@reqres.in"),"Искомый email не найден!");
    }

    @Test
    void checkPage2ForNotExistingUserEmail() {
        String response = given()
                .contentType(JSON)
                .when()
                .get("api/users?page=2")
                .then()
                .extract().response().asString();
        Assertions.assertFalse(response.contains("email@email.it"),"Email не должен существовать!");
    }

    @Test
    void checkTotalUserCount() {
        String userCount = given()
                .contentType(JSON)
                .when()
                .get("api/users")
                .then()
                .extract().path("total").toString();
        Assertions.assertEquals("12", userCount, "Неверное количество пользователей!");
    }

    @Test
    void checkTotalPageCount() {
        String pageCount = given()
                .contentType(JSON)
                .when()
                .get("api/users")
                .then()
                .extract().path("total_pages").toString();
        Assertions.assertEquals("2", pageCount, "Неверное количество страниц!");
    }

    @Test
    void checkNumberPage2() {
        String pageNumber = given()
                .contentType(JSON)
                .when()
                .get("api/users?page=2")
                .then()
                .extract().path("page").toString();
        Assertions.assertEquals("2", pageNumber,"Номер страницы неправильный!");
    }

}
