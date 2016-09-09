package controllers;

import api.domain.BeanDomain;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import play.Logger;

import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;

import java.util.List;

/**
 * Created by mirceac on 9/7/16.
 */
public abstract class SimpleEntityController extends EntityController {

    protected abstract BeanDomain addAbstract() throws Exception;

    protected abstract List<? extends BeanDomain> listAbstract() throws Exception;

    protected abstract ArrayNode listArrayAbstract() throws Exception;

    public abstract Result getTemplate(String key);

    protected abstract String getDefaultsJSON();

    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {
        try {
            return convert(addAbstract());
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    public Result list() {
        try {
            List<? extends BeanDomain> beans = listAbstract();
            JsonNode node = Json.toJson(beans);
            return ok(node.toString());
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    public Result listArray() {
        try {
            ArrayNode node = listArrayAbstract();
            return ok(node.toString());
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    public Result getDefaults() {
        return ok(getDefaultsJSON());
    }
}
