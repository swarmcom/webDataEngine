package security.encoder;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.http.credentials.DigestCredentials;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DigestPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return StringUtils.equals(charSequence, s);
    }

    public final String getServerResponse(String pwd, DigestCredentials credentials) {
        return credentials.calculateServerDigest(true, pwd);
    }
}
