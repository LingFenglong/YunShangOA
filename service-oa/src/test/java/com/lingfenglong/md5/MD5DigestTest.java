package com.lingfenglong.md5;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.Arrays;

//@SpringBootTest
public class MD5DigestTest {
    @Test
    void md5Test() {
        byte[] bytes = DigestUtils.md5Digest("123456".getBytes());
        System.out.println(Arrays.toString(bytes));
    }
}
