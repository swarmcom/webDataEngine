package security.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Encoder {
    private MD5Encoder() {
        // do not instantiate
    }

    /**
     * Computes the digest without realm domain name
     */
    public static final String digestToken(String user, String password) {
        String full = user + ':' + password;
        String digest = DigestUtils.md5Hex(full);
        return digest;
    }
}