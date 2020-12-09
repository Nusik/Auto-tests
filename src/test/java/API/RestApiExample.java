package API;

import API.models.Employee;
import API.models.EmployeeResponse;
import API.models.PostEmployeeModel;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.testng.Assert.assertEquals;

public class RestApiExample {

    @BeforeClass
    public void start() {
        RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
    }

    @Test
    public void positiveGetAllEmployeeTest() {
        given().log().all()
                .when()
                .get("/employees")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("status", equalTo("success"))
                .body("data.id", hasItems("1", "2", "3"));
    }

    @Test
    public void getAllEmployeesTestNegative() {
        //try to get all employees data from wrong endpoint
        String endpoint = "employee";            // correct is "employees"
        String url = mainUrl + endpoint;
        given()
                .when()
                .get(url)
                .then()
                .log().all()
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Error Occured! Page Not found, contact rstapi2example@gmail.com"));
    }

    @Test
    public void getEmployeeByIdTest() {
        Employee expectedEmployee = new Employee("Tiger Nixon", 320800, 61, "");
        EmployeeResponse expectedResponse = new EmployeeResponse("success", expectedEmployee, "Successfully! Record has been fetched.");
        EmployeeResponse response = given()
                .when()
                .get("/employee/1")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(EmployeeResponse.class);

        assertEquals(response, expectedResponse);
    }

    @Test
    public void getEmployeeIdTestNegative() {
        //try to get employee data by wrong endpoint
        String endpoint = "employeee/";
        String id = "id";
        String url = mainUrl + endpoint + id;
        given()
                .when()
                .get(url)
                .then()
                .log().all()
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Error Occured! Page Not found, contact rstapi2example@gmail.com"));
    }

    @Test
    public void postEmployeeTest() {
        PostEmployeeModel employee = new PostEmployeeModel("Test", "990099", "12");
        EmployeeResponse expectedResponse = new EmployeeResponse("success", new Employee(), "Successfully! Record has been fetched.");

        EmployeeResponse response = given()
                .with()
                .body(employee)
                .get("/create")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(EmployeeResponse.class);

        assertEquals(response, expectedResponse);
    }

    @Test
    public void postEmployeeTestNegative() {
        //try wrong method
        String endpoint = "create";
        String url = mainUrl + endpoint;
        PostEmployee employee = new PostEmployee("Thomas", "675432", "43");

        given()
                .with()
                .body(employee)
                .put(url)
                .then()
                .log().all()
                .statusCode(405);
    }

    @Test
    public void deleteEmployeeIdTest() {
        Employee employee = new Employee(2);
        EmployeeResponse expectedResponse = new EmployeeResponse("success", employee, "Successfully! Record has been fetched.");
        EmployeeResponse response = given()
                .delete("/delete/" + employee)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(EmployeeResponse.class);
        assertEquals(response, expectedResponse);
    }

    @Test
    public void deleteEmployeeTestNegative() {
        //try wrong method
        String endpoint = "delete/";
        String id = "id";
        String url = mainUrl + endpoint + id;

        given()
                .when()
                .post(url)
                .then()
                .statusCode(405);
    }

    @Test
    public void putEmployeeTest() {
        Map<String, String> request = new HashMap<>();
        request.put("id", "21");
        request.put("employee_name", "New test");
        request.put("employee_salary", "50000");
        request.put("employee_age", "22");
        request.put("profile_image", "");
        EmployeeResponse expectedResponse = new EmployeeResponse("success", new Employee(), "Successfully! Record has been fetched.");
        EmployeeResponse response = given()
                .with()
                .body(request)
                .put("/update/21")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(EmployeeResponse.class);
        assertEquals(response, expectedResponse);
    }

   @Test
    public void putEmployeeTestNegative() {
        //try wrong method
        String endpoint = "update/";
        String id = "id";
        String url = mainUrl + endpoint + id;
        PostEmployee employee = new PostEmployee("Thomas", "675432", "43");

        given()
                .with()
                .body(employee)
                .post(url)
                .then()
                .log().all()
                .statusCode(405);
    }
}
