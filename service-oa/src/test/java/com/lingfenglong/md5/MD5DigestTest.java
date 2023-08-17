package com.lingfenglong.md5;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;


//@SpringBootTest
public class MD5DigestTest {
    @Test
    void md5Test() {
        String s = DigestUtils.md5DigestAsHex("111111".getBytes());
        System.out.println(s);
    }
}
