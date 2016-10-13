package controllers;

import api.domain.BeanDomain;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.Play;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mirceac on 5/23/16.
 */
public abstract class EntityController extends Controller {

    protected static final String DATE_FORMAT_1 = "dd/MM/yyyy";

    protected abstract BeanDomain getByNameAbstract(String name) throws Exception;

    protected abstract BeanDomain getByIdAbstract(String id) throws Exception;

    protected abstract Long deleteByNameAbstract(String name)throws Exception;

    protected abstract Long deleteListAbstract() throws Exception;

    protected abstract BeanDomain modifyByNameAbstract(String name) throws Exception;

    protected abstract BeanDomain modifyByIdAbstract(String id) throws Exception;

    public Result getByName(String name) {
        try {
            return convert(getByNameAbstract(name));
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    public Result getById(String id) {
        try {
            return convert(getByIdAbstract(id));
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    public Result deleteByName(String name) {
        try {
            return ok(String.valueOf(deleteByNameAbstract(name)));
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result deleteList() {
        try {
            return ok(String.valueOf(deleteListAbstract()));
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result modifyByName(String name) {
        try {
            return convert(modifyByNameAbstract(name));
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result modifyById(String id) {
        try {
            return convert(modifyByIdAbstract(id));
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    protected Result convert(Object obj) {
        if (obj != null) {
            Json.mapper().setDateFormat(new SimpleDateFormat(DATE_FORMAT_1));
            JsonNode node = Json.toJson(obj);
            return ok(node.toString());
        } else {
            return internalServerError();
        }
    }

    protected void merge(BeanDomain existingBean) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT_1));
        BeanDomain bean = objectMapper.readValue(getDataToUpdateJSON(), existingBean.getClass());
        existingBean.merge(bean);
    }

    protected void mergeDefaults(BeanDomain bean, String defaults) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.readerForUpdating(bean).readValue(defaults);
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

    protected String getTemplateJSON(String key, String templateFile) {
        String schema = StringUtils.EMPTY;
        try {
            JsonNode node = new ObjectMapper().readTree(Play.application().getFile(templateFile));
            schema = node.get(key).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schema;
    }

    private String getDataToUpdateJSON() {
        Http.RequestBody body = request().body();
        JsonNode node = body.asJson();
        return node.toString();
    }

    protected Result getTemplate(String key, String file) {
        return ok(getTemplateJSON(key, file));
    }
}
