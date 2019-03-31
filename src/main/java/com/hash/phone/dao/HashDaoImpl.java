package com.hash.phone.dao;

import com.hash.phone.env.Environment;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HashDaoImpl implements HashDao {
    private static final RocksDB DB;

    static {
        String storageDir = Environment.get().getProperty("storage.path", "./storage");
        String hashAlgorithm = Environment.get().getProperty("hash.algorithm", "SHA-3");
        String salt = Environment.get().getProperty("hash.salt", "");

        String storagePath = storageDir + File.separatorChar + hashAlgorithm;
        if (!salt.isEmpty()) {
            storagePath += File.separatorChar + salt;
        }

        if (!Files.exists(Paths.get(storagePath))) {
            try {
                Files.createDirectories(Paths.get(storagePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        RocksDB.loadLibrary();

        Options options = new Options().setCreateIfMissing(true);

        try {
            DB = RocksDB.open(options, storagePath);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveHash(String hash, String phone) {
        try {
            DB.put(hash.getBytes(), phone.getBytes());
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findPhone(String hash) {
        try {
            byte[] bytes = DB.get(hash.getBytes());
            return bytes == null ? "" : new String(bytes);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }
}
