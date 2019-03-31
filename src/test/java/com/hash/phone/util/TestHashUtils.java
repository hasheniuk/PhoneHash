package com.hash.phone.util;

import com.hash.phone.env.Environment;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TestHashUtils {
    private Environment env = Environment.get();

    @Before
    public void before() {
        env.removeProperty("hash.algorithm");
        env.removeProperty("hash.salt");
    }

    @Test
    public void test_SHA_1_hash() {
        env.setProperty("hash.algorithm", "SHA-1");

        String expected = HashUtils.hash("123456789");
        String actual = "98O8HYCOBHMq32eZZczDTKeuNEE=";

        assertEquals(expected, actual);
    }

    @Test
    public void test_SHA_1_hash_with_salt() {
        env.setProperty("hash.algorithm", "SHA-1");
        env.setProperty("hash.salt", "1");

        String expected = HashUtils.hash("123456789");
        String actual = "D6CskmEfU2pdl+SFx40BSnaI+Y0=";

        assertEquals(expected, actual);
    }

    @Test
    public void test_SHA_2_hash() {
        env.setProperty("hash.algorithm", "SHA-2");

        String expected = HashUtils.hash("123456789");
        String actual = "mz5hvynxfHVXL64uhuF4CaRRPQfIoYFSrPNFIQ==";

        assertEquals(expected, actual);
    }

    @Test
    public void test_SHA_2_hash_with_salt() {
        env.setProperty("hash.algorithm", "SHA-2");
        env.setProperty("hash.salt", "1");

        String expected = HashUtils.hash("123456789");
        String actual = "hgdm8l8bNaGsQ+hmU6TjtE7iRWSvJ3pHYzrxLA==";

        assertEquals(expected, actual);
    }

    @Test
    public void test_SHA_3_hash() {
        env.setProperty("hash.algorithm", "SHA-3");

        String expected = HashUtils.hash("123456789");
        String actual = "V5XD1ij9Y4yYNaTHmlWAnyZQaMiHKaGj/N+FIg==";

        assertEquals(expected, actual);
    }

    @Test
    public void test_SHA_3_hash_with_salt() {
        env.setProperty("hash.algorithm", "SHA-3");
        env.setProperty("hash.salt", "1");

        String expected = HashUtils.hash("123456789");
        String actual = "+hGkm8/zb3YDfl7meDY0LjT6+NuaSRirBO3p7A==";

        assertEquals(expected, actual);
    }
}
