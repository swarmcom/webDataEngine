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

import java.util.ArrayList;
import java.util.Collection;
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
        User user = (User) TestUtil.convertResult(result, User.class);
        assertEquals(testSuperadmin.getBirthDate(), user.getBirthDate());
    }

    @Test
    public void getUsers() throws Exception {
        Result result = sendRequestInSession("GET", "/api/users", null);
        assertEquals(OK, result.status());
        List<Map<String,String>> list = TestUtil.convertListResult(result, List.class);
        assertEquals(1, list.size());
        Map<String,String> user = list.get(0);
        assertEquals(TEST_SUPERADMIN, user.get("userName"));
    }

    @Test
    public void getUsersArray() throws Exception {
        Result result = sendRequestInSession("GET", "/api/users/array", null);
        assertEquals(OK, result.status());
        ArrayNode arrayNode = TestUtil.convertArrayNodeResult(result, ArrayNode.class);
        assertEquals(1, arrayNode.size());
        JsonNode node = arrayNode.get(0);
        assertEquals("\"" + TEST_SUPERADMIN + "\"", node.get(2).toString());
    }

    @Test
    public void getUsersDefaults() throws Exception {
        Result result = sendRequestInSession("GET", "/api/users/defaults", null);
        assertEquals(OK, result.status());
        User user = (User) TestUtil.convertResult(result, User.class);
        assertEquals("On the phone", user.getSettings().get("instantMessaging").get("statusMsg"));
    }

    @Test
    public void userOperations() throws Exception {
        //add 3 users
        String bodyJson = "{\"userName\":\"user1\",\"password\":\"1235\",\"accountId\":\"testAccount\",\"roles\":[\"ROLE_USER\"],\"digestEncoded\":true}";
        Result result = sendRequestInSession("POST", "/api/users", bodyJson);
        assertEquals(OK, result.status());
        User user = (User) TestUtil.convertResult(result, User.class);
        assertEquals("user1", user.getUserName());

        Collection<String> ids = new ArrayList<String>();
        ids.add(user.getId());

        bodyJson = "{\"userName\":\"user2\",\"password\":\"1235\",\"accountId\":\"testAccount\",\"roles\":[\"ROLE_USER\"],\"digestEncoded\":false}";
        result = sendRequestInSession("POST", "/api/users", bodyJson);
        assertEquals(OK, result.status());

        bodyJson = "{\"userName\":\"user3\",\"password\":\"1235\",\"accountId\":\"testAccount\",\"roles\":[\"ROLE_USER\"],\"digestEncoded\":false}";
        result = sendRequestInSession("POST", "/api/users", bodyJson);
        assertEquals(OK, result.status());
        user = (User) TestUtil.convertResult(result, User.class);

        ids.add(user.getId());

        //check users size
        result = sendRequestInSession("GET", "/api/users", null);
        assertEquals(OK, result.status());
        List<Map<String,String>> list = TestUtil.convertListResult(result, List.class);
        assertEquals(4, list.size());

        //delete 2 users given ids list
        result = sendRequestInSession("DELETE", "/api/users/delete/ids", TestUtil.createIdsJson(ids));
        assertEquals(OK, result.status());

        //check users size
        result = sendRequestInSession("GET", "/api/users", null);
        assertEquals(OK, result.status());
        list = TestUtil.convertListResult(result, List.class);
        assertEquals(2, list.size());

        //delete user by username
        result = sendRequestInSession("DELETE", "/api/users/user2", null);
        assertEquals(OK, result.status());

        //check users size
        result = sendRequestInSession("GET", "/api/users", null);
        assertEquals(OK, result.status());
        list = TestUtil.convertListResult(result, List.class);
        assertEquals(1, list.size());
    }
}
