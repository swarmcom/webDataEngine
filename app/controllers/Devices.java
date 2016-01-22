package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.Play;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;

public class Devices extends Controller {

    public Result polycomTemplate(String key) {
        String schema = StringUtils.EMPTY;

        try {
            JsonNode node = new ObjectMapper().readTree(Play.application().getFile("/public/app/devices/polycom/polycomTemplate.json"));
            schema = node.get(key).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok(schema);
    }
}
