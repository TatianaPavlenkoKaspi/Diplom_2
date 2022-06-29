import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {
    User user;
    StellarBurgersClient stellarBurgersClient;
    String authorization;
    String authorization2;
    String email;

    @Before
    public void setUp() {
        stellarBurgersClient = new StellarBurgersClient();
        user  = User.getRandom();
    }

    @After
    public void tearDown() {
        if (authorization != null) {
            stellarBurgersClient.deleteUser(authorization);
        }
        if (authorization2 != null) {
            stellarBurgersClient.deleteUser(authorization2);
        }
    }

    @Test
    @DisplayName("Test user can be created")
    public void userCanBeCreated(){
        ValidatableResponse createResponse = stellarBurgersClient.createNewUser(user);
        int statusCode = createResponse.extract().statusCode();
        boolean errorStatus = createResponse.extract().body().path("success");
        authorization = createResponse.extract().body().path("accessToken");

        assertThat("Неверный код ответа", statusCode, equalTo(SC_OK));
        assertThat("Неверный статус ответа", errorStatus, equalTo(true));

    }

    @Test
    @DisplayName("Cannot create an existing user")
    public void cannotCreateAnExitingUser(){
        ValidatableResponse createResponse = stellarBurgersClient.createNewUser(user);
        email = createResponse.extract().body().path("user.email");
        authorization = createResponse.extract().body().path("accessToken");

        ValidatableResponse createResponse2 = stellarBurgersClient.createNewUser(user);
        int statusCode = createResponse2.extract().statusCode();
        boolean errorStatus = createResponse2.extract().body().path("success");
        String message = createResponse2.extract().body().path("message");

        if (statusCode == 200) {
            authorization2 = createResponse2.extract().body().path("accessToken");
        }

        assertThat("Неверный код ответа", statusCode, equalTo(SC_FORBIDDEN));
        assertThat("Неверный статус ответа", errorStatus, equalTo(false));
        assertThat("Неверный текст сообщения", message, equalTo("User already exists"));
    }

    @Test
    @DisplayName("Create user without required field Email")
    public void createUserWithoutRequiredFieldEmail(){
        user.setEmail("");
        ValidatableResponse createResponse = stellarBurgersClient.createNewUser(user);
        int statusCode = createResponse.extract().statusCode();
        boolean errorStatus = createResponse.extract().body().path("success");
        String message = createResponse.extract().body().path("message");

        if (statusCode == 200) {
            authorization = createResponse.extract().body().path("accessToken");
        }

        assertThat("Неверный код ответа", statusCode, equalTo(SC_FORBIDDEN));
        assertThat("Неверный статус ответа", errorStatus, equalTo(false));
        assertThat("Неверный текст сообщения", message, equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Create user without required field Password")
    public void createUserWithoutRequiredFieldPassword(){
        user.setPassword("");
        ValidatableResponse createResponse = stellarBurgersClient.createNewUser(user);
        int statusCode = createResponse.extract().statusCode();
        boolean errorStatus = createResponse.extract().body().path("success");
        String message = createResponse.extract().body().path("message");

        if (statusCode == 200) {
            authorization = createResponse.extract().body().path("accessToken");
        }

        assertThat("Неверный код ответа", statusCode, equalTo(SC_FORBIDDEN));
        assertThat("Неверный статус ответа", errorStatus, equalTo(false));
        assertThat("Неверный текст сообщения", message, equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Create user without required field Name")
    public void createUserWithoutRequiredFieldName(){
        user.setName("");
        ValidatableResponse createResponse = stellarBurgersClient.createNewUser(user);
        int statusCode = createResponse.extract().statusCode();
        boolean errorStatus = createResponse.extract().body().path("success");
        String message = createResponse.extract().body().path("message");

        if (statusCode == 200) {
            authorization = createResponse.extract().body().path("accessToken");
        }

        assertThat("Неверный код ответа", statusCode, equalTo(SC_FORBIDDEN));
        assertThat("Неверный статус ответа", errorStatus, equalTo(false));
        assertThat("Неверный текст сообщения", message, equalTo("Email, password and name are required fields"));
    }
}