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
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.Logger;
import play.Play;
import play.libs.Json;
import play.mvc.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Result get(String accountName) {
        Account account = accountService.getAccount(accountName);
        if (account != null) {
            JsonNode node = Json.toJson(account);
            return ok(node.toString());
        } else {
            return ok("");
        }
    }

    public Result delete(String accountName) {
        Long result = accountService.deleteAccount(accountName);
        return ok(String.valueOf(result));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result deleteAccounts() {
        Http.RequestBody body = request().body();
        JsonNode node = body.asJson();
        String dataToDelete = node.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HashMap values = objectMapper.readValue(dataToDelete, HashMap.class);
            Logger.info(values.get("ids").getClass().getName());
            List<Map<String, String>> ids = (List)values.get("ids");
            List<String> idsArray = new ArrayList<String>();
            for (Map id : ids) {
                idsArray.add((String)id.get("id"));
            }
            Long result = accountService.deleteAccounts(idsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok("deleted");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result modify(String accountName) {
        Http.RequestBody body = request().body();
        JsonNode node = body.asJson();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String dataToUpdate = node.toString();
            Account existingAccount = accountService.getAccount(accountName);
            Account account = objectMapper.readValue(dataToUpdate, Account.class);
            existingAccount.merge(account);
            accountService.saveAccount(account);
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
            itemNode.add(account.getId());
            itemNode.add(account.getAccountName());
            itemNode.add(account.getDbName());
            itemNode.add(account.getDbType().name());
            itemNode.add(account.getDbUri());
            itemNode.add(account.getSuperadminUserName());
            node.add(itemNode);
        }
        return ok(node.toString());
    }

    public Result accountTemplate(String key) {
        return ok(getAccountTemplate(key));
    }

    private String getAccountTemplate(String key) {
        String schema = StringUtils.EMPTY;
        try {
            JsonNode node = new ObjectMapper().readTree(Play.application().getFile("/public/app/templates/customer-template.json"));
            schema = node.get(key).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schema;
    }
}
