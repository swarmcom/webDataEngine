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
public class Accounts extends BaseController {
    @Inject
    AccountService accountService;

    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        Http.RequestBody body = request().body();
        JsonNode node = body.asJson();
        ObjectMapper objectMapper = new ObjectMapper();
        Account savedAccount = null;
        try {
            Account account = objectMapper.readValue(node.toString(), Account.class);
            savedAccount = accountService.createAccount(account.getAccountName(), account.getDbType().toString(),
                    account.getDbUri(), account.getDbName(), account.getSuperadminUserName(), account.getSuperadminInitialPassword());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return convert(savedAccount);
    }

    public Result getByName(String accountName) {
        Account account = accountService.getAccount(accountName);
        return convert(account);
    }

    public Result getById(String accountId) {
        Account account = accountService.getAccountById(accountId);
        return convert(account);
    }

    public Result delete(String accountName) {
        Long result = accountService.deleteAccount(accountName);
        return ok(String.valueOf(result));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result deleteAccounts() {
        List<String> idsArray = convertIds();
        Long result = accountService.deleteAccounts(idsArray);
        return ok(String.valueOf(result));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result modifyByName(String accountName) {
        Account existingAccount = accountService.getAccount(accountName);
        try {
            merge(existingAccount);
            Account modifiedAccount = (existingAccount != null ? modifiedAccount = accountService.saveAccount(existingAccount) : null);
            return convert(modifiedAccount);
        } catch (Exception ex) {
            return convert(null);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result modifyById(String accountId) {
        Account existingAccount = accountService.getAccountById(accountId);
        try {
            merge(existingAccount);
            Account modifiedAccount = (existingAccount != null ? modifiedAccount = accountService.saveAccount(existingAccount) : null);
            return convert(modifiedAccount);
        } catch (Exception ex) {
            return convert(null);
        }
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
        return ok(getTemplate(key,"/public/app/templates/account-template.json"));
    }
}
