package couchdb.dao;

import couchdb.domain.CouchDBUser;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import play.Logger;

import java.util.List;

public class CouchDBUserRepository extends CouchDbRepositorySupport<CouchDBUser> {

    public CouchDBUserRepository(CouchDbConnector db) {
        super(CouchDBUser.class, db);
        initStandardDesignDocument();
    }

    public CouchDBUser findByUserName(String userName) {
        List<CouchDBUser> users = findByUserNameList(userName);
        if (users != null && !users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    @GenerateView(field = "userName")
    private List<CouchDBUser> findByUserNameList(String userName) {
        List<CouchDBUser> users = queryView("by_userNameList", userName);
        return users;
    }

    public List<CouchDBUser> getAllUsers() {
        return getAll();
    }

    public void create(CouchDBUser user) {
        db.create(user);
    }
}
