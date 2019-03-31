package com.hash.phone.service;

import com.hash.phone.dao.HashDao;
import com.hash.phone.dao.HashDaoImpl;
import com.hash.phone.util.HashUtils;

public class HashServiceImpl implements HashService {
    private static final String COLLISION_SEPARATOR = "@";

    private HashDao dao = new HashDaoImpl();

    @Override
    public String hash(String phone) {
        String hash = HashUtils.hash(phone);
        String saved = dao.findPhone(hash);

        if (saved.isEmpty()) {
            dao.saveHash(hash, phone);
            return hash;
        }

        String[] phones = saved.split(COLLISION_SEPARATOR);

        for (int i = 0; i < phones.length; i++) {
            if (phone.equals(phones[i])) {
                if (i != 0) {
                    hash += COLLISION_SEPARATOR + i;
                }
                return hash;
            }
        }

        phone += COLLISION_SEPARATOR + phones.length;
        dao.saveHash(hash, phone);

        return hash + COLLISION_SEPARATOR + phones.length;
    }

    @Override
    public String findPhone(String hash) {
        int collisionSeparatorIndex = hash.indexOf(COLLISION_SEPARATOR);

        String clearHash = hash;
        int collisionIndex = 0;
        if (collisionSeparatorIndex >= 0) {
            clearHash = hash.substring(collisionSeparatorIndex);
            collisionIndex = Integer.parseInt(hash.substring(collisionSeparatorIndex + 1));
        }

        String phone = dao.findPhone(clearHash);
        if (phone == null) return "";

        String[] phones = phone.split(COLLISION_SEPARATOR);

        if (collisionIndex >= phones.length) return "";

        return phones[collisionIndex];
    }
}
