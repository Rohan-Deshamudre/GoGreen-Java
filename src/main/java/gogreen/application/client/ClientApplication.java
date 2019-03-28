package gogreen.application.client;

import gogreen.application.communication.AddFoodRequest;
import gogreen.application.communication.AddHomeTempRequest;
import gogreen.application.communication.AddTransportRequest;
import gogreen.application.communication.CO2Response;
import gogreen.application.communication.ClientMessage;
import gogreen.application.communication.LoginData;
import gogreen.application.model.CO2;
import java.net.URISyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ClientApplication {

    //private static final String URL = "https://gogreen32.herokuapp.com/";
    private static final String URL = "http://localhost:8080/";

    private static Logger log = LogManager.getLogger(ClientApplication.class.getName());
    private static RestTemplate restTemplate = new RestTemplate();

    private static LoginData loginData = null;

    /**
     * get requests index page of our heroku server.
     *
     * @return the text response from the server.
     */
    public static String getRequestHeroku() {
        String quote = restTemplate.getForObject(URL, String.class);
        return quote;
    }

    /**
     * This method sends a POST request to the server with the login information.
     *
     * @param username - the username.
     * @param password - the password.
     * @returns - true iff login is successful.
     */
    public static boolean sendLoginRequest(String username, String password) {
        LoginData curLoginData = new LoginData(username, password);

        try {
            log.info("Logging in to " + username);
            restTemplate
                .postForLocation(URL + "login", curLoginData);
        } catch (RestClientException e) {
            // server returned a http error code
            return false;
        }

        log.info("Login successful!");
        loginData = curLoginData;
        return true;
    }

    /**
     * Clears the currently stored login data.
     */
    public static void clearLoginData() {
        loginData = null;
    }

    /**
     * This method sends a POST request to the server with login credentials for the creation of a
     * new account.
     *
     * @param username - the username.
     * @param password - the password.
     * @return - true iff the registration was successful.
     */
    public static boolean sendRegisterRequest(String username, String password) {
        LoginData curLoginData = new LoginData(username, password);

        log.info("Attempting to register a new account for " + username);
        try {
            restTemplate
                .postForLocation(URL + "login/register", curLoginData);
        } catch (RestClientException e) {
            // registration unsuccessful.
            return false;
        }

        log.info("Account registration successful!");
        loginData = curLoginData;
        return true;
    }

    /**
     * Generic implementation of sending a post request containing activity add data to the server
     *
     * @param URLPath - path leading to correct api function.
     * @param requestData - body of the request.
     * @return - CO2Response describing the change in CO2 made.
     * @throws RestClientException - iff the status code received is not positive.
     */
    private static <T extends ClientMessage> CO2Response sendActivityAddRequest(String URLPath,
        T requestData) throws RestClientException {
        log.info("sending activity add request for: " + URLPath);
        ResponseEntity<CO2Response> res = restTemplate
            .postForEntity(URL + URLPath, requestData, CO2Response.class);
        log.info(res);

        return res.getBody();
    }

    /**
     * This method sends a post request to the server with data provided by the user about their
     * diet.
     *
     * @param choiceBoxValue - decide the value that will be added to the user.
     * @param amount - the amount of food added.
     * @return CO2Response - response object containing data returned by server.
     * @throws RestClientException - on request unsuccessful.
     */
    public static CO2Response sendAddFoodRequest(String choiceBoxValue, int amount)
        throws RestClientException {
        AddFoodRequest req = new AddFoodRequest(loginData, choiceBoxValue, amount);
        return sendActivityAddRequest("activity/food/add", req);
    }

    /**
     * This method sends a post request to the server with data provided by the user about their
     * transportation habits.
     *
     * @param distance - average distance travelled.
     * @param timesaweek - times a week this distance gets travelled.
     * @return CO2Response - response object containing data returned by server.
     * @throws RestClientException - on request unsuccessful.
     */
    public static CO2Response sendAddTransportRequest(int distance, int timesaweek)
        throws RestClientException {
        AddTransportRequest req = new AddTransportRequest(loginData, distance, timesaweek);
        return sendActivityAddRequest("activity/transport/add", req);
    }

    /**
     * This method sends a post request to the server with data provided by the user about their
     * transportation habits.
     *
     * @param temperature - the temperature the thermostat is set to.
     * @param duration - the duration this temperature is set.
     * @return - response object containing data returned by server.
     * @throws RestClientException - on request unsuccessful.
     */
    public static CO2Response sendAddHomeTempRequest(int temperature, int duration)
        throws RestClientException {
        AddHomeTempRequest req = new AddHomeTempRequest(loginData, temperature, duration);
        return sendActivityAddRequest("activity/hometemp/add", req);
    }
}