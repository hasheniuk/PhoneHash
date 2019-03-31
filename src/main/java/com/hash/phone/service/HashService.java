package com.hash.phone.service;

public interface HashService {

    String hash(String phone);
    String findPhone(String hash);
}
