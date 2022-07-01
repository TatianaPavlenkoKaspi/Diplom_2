import client.StellarBurgersClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {
    User user;
    StellarBurgersClient stellarBurgersClient;
    String authorization;

    @Before
    public void setUp() {
        stellarBurgersClient = new StellarBurgersClient();
        user  = User.getRandom();
        stellarBurgersClient.createNewUser(user);
    }

    @After
    public void tearDown() {
        if (authorization != null) {
            stellarBurgersClient.deleteUser(authorization);
        }}

    @Test
    @DisplayName("Test user can login")
    public void userCanLogin(){
        ValidatableResponse loginResponse = stellarBurgersClient.loginUser(user);
        int statusCode = loginResponse.extract().statusCode();
        boolean errorStatus = loginResponse.extract().body().path("success");
        authorization = loginResponse.extract().body().path("accessToken");

        assertThat("Неверный код ответа", statusCode, equalTo(SC_OK));
        assertThat("Неверный статус ответа", errorStatus, equalTo(true));
    }

    @Test
    @DisplayName("Login with incorrect email")
    public void loginWithIncorrectEmail(){
        user.setEmail("qatest@mail.ru");
        ValidatableResponse loginResponse = stellarBurgersClient.loginUser(user);
        int statusCode = loginResponse.extract().statusCode();
        boolean errorStatus = loginResponse.extract().body().path("success");
        String message = loginResponse.extract().body().path("message");

        if (statusCode == 200) {
            authorization = loginResponse.extract().body().path("accessToken");
        }

        assertThat("Неверный код ответа", statusCode, equalTo(SC_UNAUTHORIZED));
        assertThat("Неверный статус ответа", errorStatus, equalTo(false));
        assertThat("Неверный текст сообщения", message, equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login with incorrect password")
    public void loginWithIncorrectPassword(){
        user.setPassword("Aa123");
        ValidatableResponse loginResponse = stellarBurgersClient.loginUser(user);
        int statusCode = loginResponse.extract().statusCode();
        boolean errorStatus = loginResponse.extract().body().path("success");
        String message = loginResponse.extract().body().path("message");

        if (statusCode == 200) {
            authorization = loginResponse.extract().body().path("accessToken");
        }

        assertThat("Неверный код ответа", statusCode, equalTo(SC_UNAUTHORIZED));
        assertThat("Неверный статус ответа", errorStatus, equalTo(false));
        assertThat("Неверный текст сообщения", message, equalTo("email or password are incorrect"));
    }
}