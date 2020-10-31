package API;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestApiExample {

    @Test
    public void getAllEmployeeTest() {
        given().log().all()
                .when()
                .get("http://dummy.restapiexample.com/api/v1/employees")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("status", equalTo("success"))
                .body("data.id", hasItems("1", "2", "3"))
                .body("data.id", instanceOf(Integer.class));
    }

    @Test
    public void getEmployeeByIdTest() {
        given()
                .when()
                .get("http://dummy.restapiexample.com/api/v1/employee/1")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("status", equalTo("success"))
                .body("data.id", hasItems("1", "2", "3"))
                .body("data.id", instanceOf(Integer.class));
    }


}
