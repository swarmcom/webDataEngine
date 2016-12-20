package rest;

import api.domain.Account;
import api.type.DbType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import common.BaseRestTest;
import common.TestUtil;
import org.junit.Test;
import play.mvc.Result;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;

public class AccountTest extends BaseRestTest {

    @Test
    public void getAccountById() throws Exception {
        Result result = sendRequestInSession("GET", "/api/accounts/id/" + testAccount.getId(), null);
        assertEquals(OK, result.status());
        Account account = (Account) TestUtil.convertResult(result, Account.class);
        assertEquals(testAccount.getAccountName(), account.getAccountName());
    }

    @Test
    public void getAccountByName() throws Exception {
        Result result = sendRequestInSession("GET", "/api/accounts/name/" + testAccount.getAccountName(), null);
        assertEquals(OK, result.status());
        Account account = (Account) TestUtil.convertResult(result, Account.class);
        assertEquals(testAccount.getAccountName(), account.getAccountName());
    }

    @Test
    public void getAccounts() throws Exception {
        Result result = sendRequestInSession("GET", "/api/accounts", null);
        assertEquals(OK, result.status());
        List<Map<String,String>> list = TestUtil.convertListResult(result, List.class);
        assertEquals(1, list.size());
        Map<String,String> account = list.get(0);
        assertEquals(TEST_SUPERADMIN, account.get("superadminUserName"));
    }

    @Test
    public void getAccountsArray() throws Exception {
        Result result = sendRequestInSession("GET", "/api/accounts/array", null);
        assertEquals(OK, result.status());
        ArrayNode arrayNode = TestUtil.convertArrayNodeResult(result, ArrayNode.class);
        assertEquals(1, arrayNode.size());
        JsonNode node = arrayNode.get(0);
        assertEquals("\"" + TEST_SUPERADMIN + "\"", node.get(5).toString());
    }

    @Test
    public void getAccountsDefaults() throws Exception {
        Result result = sendRequestInSession("GET", "/api/accounts/defaults", null);
        assertEquals(OK, result.status());
        Account account = (Account) TestUtil.convertResult(result, Account.class);
        assertEquals(DbType.mongo, account.getDbType());
    }
}
