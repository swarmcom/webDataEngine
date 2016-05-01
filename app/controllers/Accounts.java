package controllers;

import api.domain.Account;
import api.service.AccountService;
import auth.AuthenticationAction;
import auth.BasicAuthentication;
import auth.DigestAuthentication;
import auth.SessionSecured;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Component
@With(AuthenticationAction.class)
@BasicAuthentication
@DigestAuthentication
@Security.Authenticated(SessionSecured.class)
@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
public class Accounts extends Controller {
    @Inject
    AccountService accountService;

    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        Http.RequestBody body = request().body();
        JsonNode node = body.asJson();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Account account = objectMapper.readValue(node.toString(), Account.class);
            accountService.createAccount(account.getAccountName(), account.getDbType().toString(),
                    account.getDbUri(), account.getDbName(), account.getSuperadminUserName(), account.getSuperadminInitialPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("created");
    }
    public Result list() {
        List<? extends Account> accounts = accountService.getAccounts();
        JsonNode node = Json.toJson(accounts);
        return ok(node.toString());
    }

    public Result listArray() {
        List<? extends Account> accounts = accountService.getAccounts();
        ArrayNode node = Json.newArray();
        for (Account account : accounts) {
            ArrayNode itemNode = Json.newArray();
            itemNode.add(account.getAccountName());
            itemNode.add(account.getDbName());
            itemNode.add(account.getDbType().name());
            itemNode.add(account.getDbUri());
            itemNode.add(account.getSuperadminUserName());
            node.add(itemNode);
        }
        return ok(node.toString());
    }
}
