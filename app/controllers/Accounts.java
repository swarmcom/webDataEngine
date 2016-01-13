package controllers;

import api.domain.Account;
import api.service.AccountService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.IOException;

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
}
