package com.java.banksystemproject.util;

import java.io.IOException;
import java.util.Map;

public interface IFileService<K, V> {
    void writeToFile(String filePath, Map<K, V> map) throws IOException;

    Map<K, V> readFromFile(String filePath) throws IOException, ClassNotFoundException;
}
