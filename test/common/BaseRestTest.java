package common;

import api.domain.Account;
import api.domain.BeanDomain;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.*;

import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

public abstract class BaseRestTest implements TestConstants {

    protected Application application;
    protected Http.Session session;
    protected Account testAccount;

    @Before
    public void setUp() throws Exception {
        ClassLoader classLoader = FakeApplication.class.getClassLoader();
        application = new GuiceApplicationBuilder()
                .in(classLoader)
                .in(Mode.TEST)
                .build();

        Helpers.start(application);

        String bodyJson = "{\"accountName\":\""+TEST_ACCOUNT +"\",\"dbType\":\"mongo\"," +
                "\"dbUri\":\""+TEST_DATABASE_URL+"\",\"dbName\":\""+TEST_DATABASE_INTEGRATION+"\",\"superadminUserName\":\""+TEST_SUPERADMIN+"\",\"superadminInitialPassword\":\""+TEST_SUPERADMIN_PASSWORD+"\"}";

        Result result = sendRequest("POST", "/test/api/accounts", bodyJson, false);

        assertEquals(OK, result.status());

        testAccount = (Account) convertResult(result, Account.class);//(new ObjectMapper()).readValue(Helpers.contentAsString(result), Account.class);

        String uri = "/callback?client_name=FormClient&accountid=" + TEST_ACCOUNT + "&username=" + TEST_SUPERADMIN + "&password=" + TEST_SUPERADMIN_PASSWORD;
        result = sendRequest("POST", uri, null, false);

        assertEquals(SEE_OTHER, result.status());

        session = result.session();
    }

    //{"ids":[{"id":"5697a1402736ddb092bb7b69"}]}

    /*private Callback<TestBrowser> tenantLogin() {
        Callback<TestBrowser> callback = new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333/main/account");
                assertTrue(browser.pageSource().contains("<login-form/>"));
                browser.goTo("http://localhost:3333/callback?client_name=FormClient&accountid="+TEST_ACCOUNT+"&username="+TEST_SUPERADMIN+"&password="+TEST_SUPERADMIN_PASSWORD);
                assertEquals("VVV ", browser.pageSource().toString());
            }
        };
        return callback;
    }*/

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

    protected BeanDomain convertResult(Result result, Class<? extends BeanDomain> classDomain) throws Exception {
        return (new ObjectMapper()).readValue(Helpers.contentAsString(result), classDomain);
    }

    @After
    public void tearDown() throws Exception {
        Result result = sendRequestInSession("DELETE", "/api/accounts/" + TEST_ACCOUNT, null);
        assertEquals(OK, result.status());

        result = sendRequestInSession("DELETE", "/api/users/" + TEST_SUPERADMIN, null);
        assertEquals(OK, result.status());

        Helpers.stop(application);
    }
}
