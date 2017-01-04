package common;

import api.domain.Account;
import api.domain.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;

import play.mvc.Http;
import play.mvc.Result;
import play.test.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public abstract class BaseRestTest extends WithServer implements TestConstants {

    protected Http.Session session;
    protected Account testAccount;
    protected User testSuperadmin;

    @Before
    public void setUpRest() throws Exception {
        String bodyJson = "{\"accountName\":\""+TEST_ACCOUNT +"\",\"dbType\":\"mongo\"," +
                "\"dbUri\":\""+TEST_DATABASE_URL+"\",\"dbName\":\""+TEST_DATABASE_INTEGRATION+"\",\"superadminUserName\":\""+TEST_SUPERADMIN+"\",\"superadminInitialPassword\":\""+TEST_SUPERADMIN_PASSWORD+"\"}";

        Result result = sendRequest("POST", "/test/api/accounts", bodyJson, false);

        assertEquals(OK, result.status());

        testAccount = (Account) TestUtil.convertResult(result, Account.class);

        String uri = "/callback?client_name=FormClient&accountid=" + TEST_ACCOUNT + "&username=" + TEST_SUPERADMIN + "&password=" + TEST_SUPERADMIN_PASSWORD;
        result = sendRequest("POST", uri, null, false);

        assertEquals(SEE_OTHER, result.status());

        session = result.session();

        result = sendRequestInSession("GET", "/api/users/name/" + TEST_SUPERADMIN, null);

        testSuperadmin = (User) TestUtil.convertResult(result, User.class);
    }

    protected Result sendRequest(String method, String uri, String bodyJson, boolean inSession) throws Exception {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(method)
                .uri(uri);
        if (!StringUtils.isEmpty(bodyJson)) {
            JsonNode jsonNode = (new ObjectMapper()).readTree(bodyJson);
            request.bodyJson(jsonNode);
        }
        if (inSession) {
            request.session(session);
        }
        return route(request);
    }

    protected Result sendRequestInSession(String method, String uri, String bodyJson) throws Exception {
        return sendRequest(method, uri, bodyJson, true);
    }

    @After
    public void tearDownRest() throws Exception {
        Result result = sendRequestInSession("DELETE", "/api/users/" + TEST_SUPERADMIN, null);
        assertEquals(OK, result.status());

        result = sendRequestInSession("DELETE", "/api/roles/account/" + TEST_ACCOUNT, null);
        assertEquals(OK, result.status());

        result = sendRequestInSession("DELETE", "/api/accounts/" + TEST_ACCOUNT, null);
        assertEquals(OK, result.status());
    }
}
