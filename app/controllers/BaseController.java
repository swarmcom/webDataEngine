package controllers;

import api.domain.BeanDomain;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.Play;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mirceac on 5/23/16.
 */
public class BaseController extends Controller {

    protected Result convert(Object obj) {
        if (obj != null) {
            JsonNode node = Json.toJson(obj);
            return ok(node.toString());
        } else {
            return internalServerError();
        }
    }

    protected void merge(BeanDomain existingBean) throws Exception {
        Http.RequestBody body = request().body();
        JsonNode node = body.asJson();
        ObjectMapper objectMapper = new ObjectMapper();
        String dataToUpdate = node.toString();
        BeanDomain bean = objectMapper.readValue(dataToUpdate, existingBean.getClass());
        existingBean.merge(bean);
    }

    protected List<String> convertIds() {
        Http.RequestBody body = request().body();
        JsonNode node = body.asJson();
        String dataToDelete = node.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> idsArray = new ArrayList<String>();
        try {
            HashMap values = objectMapper.readValue(dataToDelete, HashMap.class);
            Logger.info(values.get("ids").getClass().getName());
            List<Map<String, String>> ids = (List)values.get("ids");
            for (Map id : ids) {
                idsArray.add((String)id.get("id"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return idsArray;
    }

    protected String getTemplate(String key, String templateFile) {
        String schema = StringUtils.EMPTY;
        try {
            JsonNode node = new ObjectMapper().readTree(Play.application().getFile(templateFile));
            schema = node.get(key).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schema;
    }
}
