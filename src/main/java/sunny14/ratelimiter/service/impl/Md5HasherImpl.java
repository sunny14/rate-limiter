package sunny14.ratelimiter.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunny14.ratelimiter.service.Hasher;
import sunny14.ratelimiter.service.exceptions.HasherException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5HasherImpl implements Hasher {

    private static Logger log = LoggerFactory.getLogger("Md5HasherImpl");

    private MessageDigest md;

    @Override
    public String hash(String str) throws HasherException {

        //TODO: if str is not url encoded then encode
        //TODO: make sure md5 will treat both ASCII and UTF8 characters

        lazyInit();

        byte [] strBytes = str.toLowerCase().getBytes();
        return new String (md.digest(strBytes));

    }

    private void lazyInit() throws HasherException {
        if (md == null) {
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                log.error(e.getMessage());
                throw new HasherException(e);
            }
        }
    }
}
