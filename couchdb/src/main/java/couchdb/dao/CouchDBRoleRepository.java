package couchdb.dao;

import couchdb.domain.CouchDBRole;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;

import java.util.List;

public class CouchDBRoleRepository extends CouchDbRepositorySupport<CouchDBRole> {

    public CouchDBRoleRepository(CouchDbConnector db) {
        super(CouchDBRole.class, db);
        initStandardDesignDocument();
    }

    public CouchDBRole findByRoleName(String roleName) {
        List<CouchDBRole> roles = findByRoleNameList(roleName);
        if (roles != null && !roles.isEmpty()) {
            return roles.get(0);
        }
        return null;
    }

    @GenerateView(field = "roleName")
    private List<CouchDBRole> findByRoleNameList(String roleName) {
        List<CouchDBRole> roles = queryView("by_roleNameList", roleName);
        return roles;
    }

    public List<CouchDBRole> getAllRoles() {
        return getAll();
    }

    public void create(CouchDBRole role) {
        db.create(role);
    }
}
