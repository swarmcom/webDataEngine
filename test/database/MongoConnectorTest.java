package database; /**
 * Created by mirceac on 12/2/16.
 */

import static org.junit.Assert.*;

import api.domain.User;
import common.TestConstants;
import mongo.config.MongoConfig;
import mongo.service.MongoUserService;
import org.apache.commons.codec.binary.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import security.authorizer.RoleType;

import java.util.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={MongoConfig.class})
@TestPropertySource(properties = {"uri="+TestConstants.TEST_DATABASE_URL, "dbname="+TestConstants.TEST_DATABASE_CONNECTOR})
public class MongoConnectorTest implements TestConstants {
    @Autowired
    protected MongoUserService service;
    private User user;

    @Before
    public void setUp() throws InterruptedException {
        Set roles = new TreeSet();
        roles.add(RoleType.ROLE_SUPERADMIN);
        user = service.createUser(TEST_ACCOUNT, TEST_USER, "password_user", roles);
    }

    @Test
    public void findsUserByUserName() throws Exception {
        User localUser = service.getUser(TEST_ACCOUNT, TEST_USER);
        assertEquals(user.getUserName(), localUser.getUserName());
    }

    @Test
    public void saveUser() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1977, 6, 26);
        user.setBirthDate(calendar.getTime());
        user.setPassword("password_modified");
        service.saveUser(TEST_ACCOUNT, user);
        User localUser = service.getUser(TEST_ACCOUNT, TEST_USER);
        assertEquals(user.getBirthDate(), calendar.getTime());
        assertEquals("password_modified", localUser.getPassword());
    }

    @Test
    public void getUserById() {
        User localUser = service.getUserById(TEST_ACCOUNT, user.getId());
        assertEquals(localUser.getUserName(), TEST_USER);
    }

    @Test
    public void operationsUsers() throws Exception {
        Set roles = new TreeSet();
        roles.add(RoleType.ROLE_USER);
        service.createUser(TEST_ACCOUNT, "username1", "password1", roles);
        service.createUser(TEST_ACCOUNT, "username2", "password2", roles);
        service.createUser(TEST_ACCOUNT, "username3", "password3", roles);
        service.createUser(TEST_ACCOUNT, "username4", "password4", roles);
        service.createUser(TEST_ACCOUNT, "username5", "password5", roles);
        List<? extends User> users = service.getUsers(TEST_ACCOUNT);
        assertEquals(6, users.size());
        Collection<String> userIds = new ArrayList<String>();
        for (User user: users) {
            if (!StringUtils.equals(TEST_USER, user.getUserName())) {
                userIds.add(user.getId());
            }
        }
        service.deleteUsers(TEST_ACCOUNT, userIds);
        users = service.getUsers(TEST_ACCOUNT);
        assertEquals(1, users.size());
    }

    @After
    public void tearDown() {
        service.deleteUser(TEST_ACCOUNT, TEST_USER);
    }
}
