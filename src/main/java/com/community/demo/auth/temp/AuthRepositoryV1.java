package com.community.demo.auth.temp;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthRepositoryV1  {
    Map<String, Auth> authDb = new HashMap<>();

    public void save(Auth auth) {
        String email = auth.getEmail();
        authDb.put(email, auth);
    }

    public Auth findByEmail(String email) {
        return authDb.get(email);
    }

    public Auth findById(long userId) {
        return null;
    }
}
