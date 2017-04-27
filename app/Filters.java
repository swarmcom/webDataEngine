import com.google.inject.Inject;
import play.api.mvc.EssentialFilter;
import play.filters.headers.SecurityHeadersFilter;
import play.http.HttpFilters;

public class Filters implements HttpFilters {

    @Inject
    SecurityHeadersFilter securityHeadersFilter;

    public EssentialFilter[] filters() {
        return new EssentialFilter[] { securityHeadersFilter };
    }
}
