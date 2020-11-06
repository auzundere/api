package cox.apitests;

import com.fasterxml.jackson.databind.ObjectMapper;
import cox.base.TestBase;
import cox.client.RestClient;
import cox.data.Users;
import cox.utilities.ConfigurationReader;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class APITests extends TestBase {

    TestBase testBase;
    String serviceURL;
    String apiURL;
    String URL;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    static Logger LOGGER = Logger.getLogger(APITests.class.getSimpleName());
    public Properties prop;

    @BeforeSuite
    public void startTest() {

    }

    @BeforeMethod
    public void setup() {
        testBase = new TestBase();
        serviceURL = ConfigurationReader.getProperty("URL");
        apiURL = ConfigurationReader.getProperty("serviceURL");
        URL = serviceURL + apiURL;
    }

    /**
     * User can Login with a valid username and password using "/login" endpoint of the API
     */
    @Test(groups = {"smoke", "login"}, priority = 1)
    public void login(Method method) throws IOException {

        restClient = new RestClient();
        String username = "logger";
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("api-key", "special-key");
        headerMap.put(username, "logger");
        headerMap.put("password", "abc123");
        closeableHttpResponse = restClient.get(URL + "/login", headerMap);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code was not 200");
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        Assert.assertTrue(responseString.contains("logged in user session:"));
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        logHeaders();
    }

    /**
     * User can logout by using "/logout" end point of the API
     */
    @Test(groups = {"smoke", "logout"}, priority = 2)
    public void logout(Method method) throws IOException {
        restClient = new RestClient();
        Map<String, String> headerMap = new HashMap<String, String>();

        closeableHttpResponse = restClient.get(URL + "/logout", headerMap);

        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code was not 200");
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        logHeaders();
    }

    @Test(groups = {"smoke", "update"}, priority = 3)
    public void updateAUser(Method method) throws IOException {
        restClient = new RestClient();
        String userName = "logger";
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("accept", "application/json");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("username", "logger");
        headerMap.put("password", "abc123");

        ObjectMapper mapper = new ObjectMapper();
        Users users = new Users(1234, userName, "Resul", "Aslan", "abc@abc.com", "password", "1232344567", 1234567);

        String usersJSonString = mapper.writeValueAsString(users);

        closeableHttpResponse = restClient.put(URL + "/" + userName, usersJSonString, headerMap);

        //1. get Status code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);
        logHeaders();
    }

    /**
     * User can delete a user using by /{userName} end point
     */
    @Test(groups = {"smoke", "delete"}, priority = 4)
    public void deleteAUser(Method method) throws IOException {
        restClient = new RestClient();
        String userName = "logger";
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("accept", "application/json");
        headerMap.put("Content-Type", "application/json");
        headerMap.put("username", "logger");
        headerMap.put("password", "abc123");

        ObjectMapper mapper = new ObjectMapper();
        closeableHttpResponse = restClient.delete(URL + "/" + userName);

        //1. get Status code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);
        logHeaders();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
        } else if (result.getStatus() == ITestResult.SKIP) {
        }
    }

    @AfterTest
    public void afterSuite() {
    }

    private void logHeaders(){
    }

}
