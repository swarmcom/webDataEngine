package controllers;

import api.domain.BeanDomain;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;

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

    protected BeanDomain merge(BeanDomain existingBean) {
        Http.RequestBody body = request().body();
        JsonNode node = body.asJson();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String dataToUpdate = node.toString();
            BeanDomain bean = objectMapper.readValue(dataToUpdate, existingBean.getClass());
            existingBean.merge(bean);
            return bean;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
