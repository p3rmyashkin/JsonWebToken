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

    private final static int LENGTH = 16;
    private final Random random = new Random();

    private String generateRandomString() {

        return random.ints(97, 123)
                .limit(LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public MetaTokenInf generateTokenInf() {
        MetaTokenInf metaTokenInf = new MetaTokenInf();
        metaTokenInf.setValue(generateRandomString());
        repository.save(metaTokenInf);
        return metaTokenInf;
    }

    public boolean validateTokenIsPresentAndThenDelete(Long id, String value) {
        Optional<MetaTokenInf> byId = repository.findById(id);
        if (byId.isPresent()) {
            MetaTokenInf metaTokenInf = byId.get();
            if (metaTokenInf.getValue().equals(value)) {
                repository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}
