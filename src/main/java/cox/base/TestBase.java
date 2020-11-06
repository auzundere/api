package cox.base;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;


public class TestBase {
    public static final int RESPONSE_STATUS_CODE_200 = 200;
    public static final int RESPONSE_STATUS_CODE_201 = 201;
    public static final int RESPONSE_STATUS_CODE_400 = 400;
    public static final int RESPONSE_STATUS_CODE_401 = 401;
    public static final int RESPONSE_STATUS_CODE_500 = 500;

    public TestBase(){
        //Disables weird extra logging records comes from http request.
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger("org.apache.http");
        root.setLevel(ch.qos.logback.classic.Level.INFO);
    }
}
