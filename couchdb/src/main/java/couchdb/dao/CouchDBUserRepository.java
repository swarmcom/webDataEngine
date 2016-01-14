package couchdb.dao;

import couchdb.domain.CouchDBUser;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;

import java.util.List;

@View( name = "by_accountIdAndUserName", map = "function(doc) { if (doc.accountId && doc.userName ) {emit(doc.userName, doc._id )} }")
public class CouchDBUserRepository extends CouchDbRepositorySupport<CouchDBUser> {

    public CouchDBUserRepository(CouchDbConnector db) {
        super(CouchDBUser.class, db);
        initStandardDesignDocument();
    }

    public CouchDBUser findByAccountIdAndUserName(String accountId, String userName) {
        List<CouchDBUser> users = findByAccountIdAndUserNameList(accountId, userName);
        if (users != null && !users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    private List<CouchDBUser> findByAccountIdAndUserNameList(String accountId, String userName) {
        List<CouchDBUser> users = queryView("by_accountIdAndUserName", userName);
        return users;
    }


    public List<CouchDBUser> getAllUsers(String accountId) {
        return findByAccountId(accountId);
    }

    @GenerateView
    private List<CouchDBUser> findByAccountId(String accountId) {
        List<CouchDBUser> users = queryView("by_accountId", accountId);
        return users;
    }

    public void create(CouchDBUser user) {
        db.create(user);
    }
}
