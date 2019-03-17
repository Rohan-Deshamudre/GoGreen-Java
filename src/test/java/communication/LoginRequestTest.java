package communication;

import communication.clientMessage.LoginRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    LoginData loginData = new LoginData("user", "pwd");

    @Test
    public void constructorTest() {
        LoginRequest req = new LoginRequest(loginData);
        assertTrue(req.getLoginData().equals(loginData));
    }

    @Test
    public void constructorTestEmpty() {
        LoginRequest req = new LoginRequest();
        assertNull(req.getLoginData());
    }
    @Test
    public void toStringTest() {
        LoginRequest req = new LoginRequest(loginData);
        assertEquals(req.toString(),"\n===LoginRequest===\n<LoginData{\n    username: user\n    password: pwd\n}>");
    }

    @Test
    public void toStringTestNull() {
        LoginRequest req = new LoginRequest();
        assertEquals(req.toString(), "\n===LoginRequest===\n<LoginData{\n    username: \n    password: \n}>");
    }
}