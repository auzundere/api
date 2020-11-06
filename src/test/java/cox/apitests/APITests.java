package cox.apitests;

import cox.base.TestBase;
import cox.client.RestClient;
import cox.utilities.ConfigurationReader;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Arrays;
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

    @Test(groups = {"smoke"}, priority = 1)
    public void getAllVehicles() throws IOException {

        restClient = new RestClient();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        int datasetId = 12;
        String path = "/" + datasetId + "/vehicles";
        closeableHttpResponse = restClient.get(URL + path, headerMap);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code was not 200");
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        String expectedResult = "{\n" +
                "  \"vehicleIds\": [\n" +
                "    0\n" +
                "  ]\n" +
                "}";
        Assert.assertEquals(responseString, expectedResult, "The response body wa not the same, ");
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        LOGGER.info(Arrays.toString(headersArray));
    }

    @Test(groups = {"smoke"}, priority = 2)
    public void getVehicle() throws IOException {

        restClient = new RestClient();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        int datasetId = 12;
        int vehicleId = 10;
        String path = "/" + datasetId + "/vehicles/" + vehicleId;
        closeableHttpResponse = restClient.get(URL + path, headerMap);
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code was not 200");
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        String expectedResult = "{\n" +
                "  \"vehicleId\": 10,\n" +
                "  \"year\": 0,\n" +
                "  \"make\": \"string\",\n" +
                "  \"model\": \"string\",\n" +
                "  \"dealerId\": 0\n" +
                "}";
        Assert.assertEquals(responseString, expectedResult, "The response body wa not the same, ");
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        LOGGER.info(Arrays.toString(headersArray));
    }


    @AfterMethod
    public void tearDown() {
    }

    @AfterTest
    public void afterSuite() {
    }

}
