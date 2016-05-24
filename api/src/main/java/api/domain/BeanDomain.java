package api.domain;

/**
 * Created by mirceac on 5/23/16.
 */
public abstract class BeanDomain<T> {
    public abstract void merge(T beanDomain);
}
