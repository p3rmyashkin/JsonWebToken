package com.example.securingweb.services;

import com.example.securingweb.entites.MetaTokenInf;
import com.example.securingweb.repository.MetaTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class MetaTokenService {

    @Autowired
    private MetaTokenRepository repository;

    private final Random random = new Random();

    private String generateRandomString(int length) {

        return random.ints(97, 123)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public MetaTokenInf generateTokenInf() {
        MetaTokenInf metaTokenInf = new MetaTokenInf();
        metaTokenInf.setValue(generateRandomString(16));
        repository.save(metaTokenInf);
        return metaTokenInf;
    }

    public boolean validateTokenIsPresentAndThenDelete(Long id, String value) {
        boolean isValid = false;
        Optional<MetaTokenInf> byId = repository.findById(id);
        MetaTokenInf metaTokenInf = byId.orElseThrow(() -> new RuntimeException("no such token"));
        if (metaTokenInf.getValue().equals(value)) isValid = true;
        repository.deleteById(id);
        return isValid;
    }
}
