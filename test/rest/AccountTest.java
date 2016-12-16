package rest;

import api.domain.Account;
import common.BaseRestTest;
import org.junit.Test;
import play.Logger;
import play.mvc.Result;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;

public class AccountTest extends BaseRestTest {

    @Test
    public void getAccountById() throws Exception {
        Result result = sendRequestInSession("GET", "/api/accounts/id/" + testAccount.getId(), null);
        assertEquals(OK, result.status());
        Account account = (Account) convertResult(result, Account.class);
        assertEquals(testAccount.getAccountName(), account.getAccountName());
    }

    @Test
    public void saveAccount() throws Exception {
        /*String bodyJson = "{\"accountName\":\"exampleCustomer\",\"dbType\":\"mongo\"," +
                "\"dbUri\":\""+TEST_DATABASE_URL+"\",\"dbName\":\""+TEST_DATABASE_INTEGRATION+"\",\"superadminUserName\":\"exampleSuper\",\"superadminInitialPassword\":\"example123\"}";

        Result result = sendRequestInSession("POST", "/api/accounts", bodyJson);
        assertEquals(OK, result.status());
        result = sendRequestInSession("GET", "/api/accounts/name/exampleCustomer", null);
        assertEquals(OK, result.status());
        Account account = (Account) convertResult(result, Account.class);
        assertEquals("exampleCustomer", account.getAccountName());
        result = sendRequestInSession("DELETE", "/api/accounts/exampleCustomer", null);
        assertEquals(OK, result.status());
        result = sendRequestInSession("DELETE", "/api/users/exampleSuper", null);
        assertEquals(OK, result.status());*/
    }

}
