package couchdb.dao;

import couchdb.domain.CouchdbUser;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;

import java.util.List;

public class CouchdbUserRepository extends CouchDbRepositorySupport<CouchdbUser> {

    public CouchdbUserRepository(CouchDbConnector db) {
        super(CouchdbUser.class, db);
        initStandardDesignDocument();
    }

    public CouchdbUser findByUserName(String userName) {
        List<CouchdbUser> users = findByUserNameList(userName);
        if (users != null && !users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    @GenerateView(field = "userName")
    private List<CouchdbUser> findByUserNameList(String userName) {
        List<CouchdbUser> users = queryView("by_userNameList", userName);
        return users;
    }

    public void create(CouchdbUser user) {
        db.create(user);
    }
}
