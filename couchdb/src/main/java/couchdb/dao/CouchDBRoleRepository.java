package couchdb.dao;

import couchdb.domain.CouchDBRole;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;

import java.util.List;
@View( name = "by_accountIdAndRoleName", map = "function(doc) { if (doc.accountId && doc.roleName ) {emit(doc.roleName, doc._id )} }")
public class CouchDBRoleRepository extends CouchDbRepositorySupport<CouchDBRole> {

    public CouchDBRoleRepository(CouchDbConnector db) {
        super(CouchDBRole.class, db);
        initStandardDesignDocument();
    }

    public CouchDBRole findByAccountIdAndRoleName(String accountId, String roleName) {
        List<CouchDBRole> roles = findByAccountIdAndRoleNameList(accountId, roleName);
        if (roles != null && !roles.isEmpty()) {
            return roles.get(0);
        }
        return null;
    }

    @GenerateView(field = "roleName")
    private List<CouchDBRole> findByAccountIdAndRoleNameList(String accountId, String roleName) {
        List<CouchDBRole> roles = queryView("by_roleNameList", roleName);
        return roles;
    }

    public List<CouchDBRole> getAllRoles(String accountId) {
        List<CouchDBRole> roles = queryView("by_accountId", accountId);
        return roles;
    }

    public void create(CouchDBRole role) {
        db.create(role);
    }
}
