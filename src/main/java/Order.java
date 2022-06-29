import io.restassured.response.ValidatableResponse;
import java.util.ArrayList;

public class Order {
    private String bun;
    private String sauce;
    private String main;

    public Order (String bun, String sauce, String main) {
        this.bun = bun;
        this.sauce = sauce;
        this.main = main;
    }

    public String getBun() {
        return bun;
    }

    public void setBun(String bun) {
        this.bun = bun;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
    public static Order generateOrder() {
        StellarBurgersClient stellarburgersClient = new StellarBurgersClient();
        ValidatableResponse getIngredients = stellarburgersClient.getIngredients();

        ArrayList<String> bunList = getIngredients.extract().body().path("data.findAll { it.type == \"bun\"}._id");
        ArrayList<String> sauceList = getIngredients.extract().body().path("data.findAll { it.type == \"sauce\"}._id");
        ArrayList<String> mainList = getIngredients.extract().body().path("data.findAll { it.type == \"main\"}._id");

        int bunIndex = (int) (Math.random() * bunList.size());
        int sauceIndex = (int) (Math.random() * sauceList.size());
        int mainIndex = (int) (Math.random() * mainList.size());

        return new Order(bunList.get(bunIndex),
                sauceList.get(sauceIndex),
                mainList.get(mainIndex));
    }
}