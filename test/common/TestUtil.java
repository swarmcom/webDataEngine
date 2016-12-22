package common;

import api.domain.BeanDomain;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.lang3.StringUtils;
import play.mvc.Result;
import play.test.Helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TestUtil {
    public static BeanDomain convertResult(Result result, Class<? extends BeanDomain> classDomain) throws Exception {
        return (new ObjectMapper()).readValue(Helpers.contentAsString(result), classDomain);
    }

    public static ArrayNode convertArrayNodeResult(Result result, Class<ArrayNode> classArrayNode) throws Exception {
        return (new ObjectMapper()).readValue(Helpers.contentAsString(result), classArrayNode);
    }

    public static List<Map<String,String>> convertListResult(Result result, Class<List> classListDomain) throws Exception {
        return (new ObjectMapper()).readValue(Helpers.contentAsString(result), classListDomain);
    }

    public static Collection<String> getFieldList(List<Map<String,String>> mapList, String field) {
        Collection<String> fieldList = new ArrayList<String>();
        for (Map<String, String> map : mapList) {
            fieldList.add(map.get(field));
        }
        return fieldList;
    }

    public static String createIdsJson(Collection<String> ids) {
        StringBuilder idsJson = new StringBuilder("{\"ids\":[");
        String[] arrayString = new String[ids.size()];
        int i=0;
        for (String id : ids) {
            arrayString[i++] = String.format("{\"id\":\"%s\"}", id);
        }
        idsJson.append(StringUtils.join(arrayString, ","));
        idsJson.append("]}");
        return idsJson.toString();
    }
}
