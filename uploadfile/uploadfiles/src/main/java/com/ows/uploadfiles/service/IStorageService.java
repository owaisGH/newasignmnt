package com.ows.uploadfiles.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {

    void init();

    void storeFile(MultipartFile file);

    Stream<Path> loadAllFiles();

    Path loadFile(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}
