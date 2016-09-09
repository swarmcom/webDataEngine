package api.domain;

import java.util.Map;

/**
 * Created by mirceac on 5/23/16.
 */
public abstract class BeanDomain<T> {
    public abstract void merge(T beanDomain);

    protected void mergeSettings(Map<String, Map<String, Object>> settingsGet, Map<String, Map<String, Object>> settingsPut) {
        if (settingsGet != null) {
            for (Map.Entry entry : settingsGet.entrySet()) {
                Map<String, Object> entryToMerge = settingsGet.get(entry.getKey());
                if (entryToMerge != null) {
                    settingsPut.put((String)entry.getKey(), entryToMerge);
                }
            }
        }
    }
}
