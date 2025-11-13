import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

public class Precondition {

    public static RequestSpecification requestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri("https://apitests.nyc.wf/")
                .setAccept("application/json")
                .build();
    }

    public static ResponseSpecification respSpec(int status){
        return new ResponseSpecBuilder()
                .expectStatusCode(status)
                .build();
    }
    public static void installSpecification(RequestSpecification requestSpec, ResponseSpecification responseSpec){
        requestSpecification = requestSpec;
        responseSpecification = responseSpec;
    }

}