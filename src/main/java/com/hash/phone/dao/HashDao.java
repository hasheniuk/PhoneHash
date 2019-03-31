package com.hash.phone.dao;

public interface HashDao {

    void saveHash(String hash, String phone);
    String findPhone(String hash);
}
