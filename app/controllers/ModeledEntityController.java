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
public abstract class ModeledEntityController extends EntityController {

    protected abstract BeanDomain addAbstract(String model) throws Exception;

    protected abstract List<? extends BeanDomain> listAbstract(String model) throws Exception;

    protected abstract ArrayNode listArrayAbstract(String model) throws Exception;

    public abstract Result getTemplateByModel(String key, String model);

    protected abstract String getDefaultsJSON(String model);

    @BodyParser.Of(BodyParser.Json.class)
    public Result add(String model) {
        try {
            return convert(addAbstract(model));
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    public Result list(String model) {
        try {
            List<? extends BeanDomain> beans = listAbstract(model);
            JsonNode node = Json.toJson(beans);
            return ok(node.toString());
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    public Result listArray(String model) {
        try {
            ArrayNode node = listArrayAbstract(model);
            return ok(node.toString());
        } catch (Exception e) {
            Logger.error("error ", e);
            return convert(null);
        }
    }

    public Result getDefaults(String model) {
        return ok(getDefaultsJSON(model));
    }
}
