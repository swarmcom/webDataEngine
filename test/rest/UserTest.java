package rest;

import api.domain.User;
import api.type.DbType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import common.BaseRestTest;
import common.TestUtil;
import org.junit.Test;
import play.Logger;
import play.test.Helpers;
import play.mvc.Result;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;

public class UserTest extends BaseRestTest {

    @Test
    public void getUsertById() throws Exception {
        Result result = sendRequestInSession("GET", "/api/users/id/" + testSuperadmin.getId(), null);
        assertEquals(OK, result.status());
        User user = (User) TestUtil.convertResult(result, User.class);
        assertEquals(testSuperadmin.getBirthDate(), user.getBirthDate());
    }

    @Test
    public void getUsertByName() throws Exception {
        Result result = sendRequestInSession("GET", "/api/users/name/" + testSuperadmin.getUserName(), null);
        assertEquals(OK, result.status());
        Logger.info("MIRCEA " + Helpers.contentAsString(result));
        User user = (User) TestUtil.convertResult(result, User.class);
        assertEquals(testSuperadmin.getBirthDate(), user.getBirthDate());
    }

    @Test
    public void getUsers() throws Exception {
        Result result = sendRequestInSession("GET", "/api/users", null);
        assertEquals(OK, result.status());
        List<Map<String,String>> list = TestUtil.convertListResult(result, List.class);
        assertEquals(1, list.size());
        Map<String,String> account = list.get(0);
        assertEquals(TEST_SUPERADMIN, account.get("userName"));
    }

    @Test
    public void getUsersArray() throws Exception {
        Result result = sendRequestInSession("GET", "/api/users/array", null);
        assertEquals(OK, result.status());
        ArrayNode arrayNode = TestUtil.convertArrayNodeResult(result, ArrayNode.class);
        assertEquals(1, arrayNode.size());
        JsonNode node = arrayNode.get(0);
        assertEquals("\"" + TEST_SUPERADMIN + "\"", node.get(5).toString());
    }

    @Test
    public void getUsersDefaults() throws Exception {
        Result result = sendRequestInSession("GET", "/api/users/defaults", null);
        assertEquals(OK, result.status());
        User user = (User) TestUtil.convertResult(result, User.class);
        assertEquals(TEST_SUPERADMIN, user.getUserName());
    }
    //{"id":"58593b152736d96f1cd8c2ee","userName":"superadmin","password":"$2a$10$zV0ivb8trhRD2WYTjzSzreEgmnnHc/f/dqoZgZOnXIbBalE.xs03i","accountId":"testAccount","roles":["ROLE_ADMIN","ROLE_SUPERADMIN","ROLE_USER"],"digestEncoded":null,"birthDate":null,"settings":{},"new":false}
}
