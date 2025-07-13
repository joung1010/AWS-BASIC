package com.business.dream.config;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptConfigTest {

    @Autowired
    private StringEncryptor jasyptStringEncryptor;
    private String[] testValues;


    @BeforeEach
    public void setUp() {
        testValues = new String[]{};
    }

    @Test
    public void testEncryption() {
        System.out.println("=== 암호화 결과 ===");
        for (String value : testValues) {
            String encrypted = jasyptStringEncryptor.encrypt(value);
            System.out.println("ENC(" + encrypted + ")");
        }
    }

    @Test
    public void testDecryption() {
        System.out.println("=== 복호화 결과 ===");
        for (String value : testValues) {
            String encrypted = jasyptStringEncryptor.encrypt(value);
            String decrypted = jasyptStringEncryptor.decrypt(encrypted);
            System.out.println(decrypted);
        }
    }
}