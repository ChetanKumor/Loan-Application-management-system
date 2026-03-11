package com.sva.loan_application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

@Configuration
public class EncryptionConfig {

    private final String salt = KeyGenerators.string().generateKey();
    private final String password = "your-strong-encryption-key"; // In production, use environment variables

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.text(password, salt);
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}