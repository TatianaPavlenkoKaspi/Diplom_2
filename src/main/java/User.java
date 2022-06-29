import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name ;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User getRandom() {
        final String name = RandomStringUtils.randomAlphabetic(10);
        final String email = RandomStringUtils.randomAlphabetic(10) + "@google.com";
        final String password = RandomStringUtils.randomAlphabetic(10);

        return new User(name, email, password);
    }

}