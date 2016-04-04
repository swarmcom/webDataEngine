package security.util;

import org.apache.commons.codec.digest.DigestUtils;

public class EncoderUtil {

    public static final String digestEncodePassword(String user, String realm, String password) {
        String full = user + ':' + realm + ':' + password;
        String digest = DigestUtils.md5Hex(full);
        return digest;
    }
}
