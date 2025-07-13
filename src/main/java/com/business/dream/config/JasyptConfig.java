package com.business.dream.config;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * <b> JasyptConfig </b>
 *
 * @author jh.park
 * @version 0.1.0
 * @since 2025-07-13
 */

@Slf4j
@Configuration
public class JasyptConfig {

    public static final String ENCRYPTOR_BEAN_NAME = "jasyptStringEncryptor";

    @Value("${jasypt.encryptor.password:#{null}}")
    private String encryptionKeyFromConfig;

    @Bean(JasyptConfig.ENCRYPTOR_BEAN_NAME)
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        String encryptionKey = encryptionKeyFromConfig;
        if (encryptionKey == null || encryptionKey.contains("YourRandomEncryptionKeyWillBeGeneratedAtRuntime")) {
            encryptionKey = generateRandomKey();
            log.info("Generated random encryption key: {}" , encryptionKey);
            log.info("For production use, set this key as JASYPT_ENCRYPTOR_PASSWORD environment variable");
        }

        config.setPassword(encryptionKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");

        encryptor.setConfig(config);

        return encryptor;
    }

    private String generateRandomKey() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }
}
