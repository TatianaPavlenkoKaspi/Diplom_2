import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetUserOrderTest {
    User user ;
    StellarBurgersClient stellarBurgersClient;
    String authorization;

    @Before
    public void setUp() {
        stellarBurgersClient = new StellarBurgersClient();
    }

    @After
    public void tearDown() {
        if (authorization != null) {
            stellarBurgersClient.deleteUser(authorization);
        }
    }

    @Test
    public void getOrdersAuthorizedUser(){
        user  = User.getRandom();
        authorization = stellarBurgersClient.createNewUser(user).extract().body().path("accessToken");
        ValidatableResponse getOrdersResponse = stellarBurgersClient.getOrdersAuthorizedUser(user, authorization);
        int statusCode = getOrdersResponse.extract().statusCode();

        assertThat("Неверный код ответа", statusCode, equalTo(SC_OK));
    }

    @Test
    public void getOrdersUnauthorizedUser(){
        ValidatableResponse getOrdersResponse = stellarBurgersClient.getOrdersUnauthorizedUser();
        int statusCode = getOrdersResponse.extract().statusCode();
        String message = getOrdersResponse.extract().body().path("message");

        assertThat("Неверный код ответа", statusCode, equalTo(SC_UNAUTHORIZED));
        assertThat("Неверный текст сообщения", message, equalTo("You should be authorised"));
    }
}