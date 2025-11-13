import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SmokeTests {

    public static String token;
    public static int balance;

    @BeforeAll
    public static void validAuthSmoke() {

        Precondition.installSpecification(Precondition.requestSpec(), Precondition.respSpec(200));

        ExtractableResponse<Response> response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "admin")
                .formParam("password", "qazWSXedc")
                .when()
                .post("authorization")
                .then()
                .assertThat()
                .body("result", equalTo("OK"))
                .body("token", hasLength(32))
                .body("balance", greaterThanOrEqualTo(0))
                .extract();

        token = response.path("token"); // получаем токен
        balance = response.path("balance"); // получаем баланс

    }

    @Test
    public void transactionSmoke() {

        float transferMoney = 10.00F;

        Precondition.installSpecification(Precondition.requestSpec(), Precondition.respSpec(200));
        given()
                .contentType("application/x-www-form-urlencoded")
                .header("X-Api-Key", token)
                .formParam("action", "withdraw")
                .formParam("amount", transferMoney)
                .when()
                .post("transaction")
                .then()
                .body("result", equalTo("OK"))
                .body("balance", equalTo(balance - transferMoney));

    }


    @Test
    public void logoutSmoke() {

        Precondition.installSpecification(Precondition.requestSpec(), Precondition.respSpec(200));
        given()
                .header("X-Api-Key", token)
                .when()
                .get("logout")
                .then()
                .assertThat()
                .body("result", equalTo("OK"));

    }



}
