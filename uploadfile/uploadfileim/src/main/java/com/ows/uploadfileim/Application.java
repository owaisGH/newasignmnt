// Copyright (c) 2014 KMS Technology, Inc.
package com.ows.uploadfileim;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ows.uploadfileim.service.FileMetaService;



/**
 * @author mohaahma
 */
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
public class Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
    public static void main(String... args) throws IOException {
        ApplicationContext appContext = SpringApplication.run(Application.class, args);
        
        FileMetaService fileMetaService = appContext.getBean(FileMetaService.class);
        String file2Path = (args.length > 0)? args[0] : "C:/Users/mohaahma/Documents/Workspace-M/uploadfileim/etc/filemetas.txt"; //C:/Users/mohaahma/Documents/Workspace-M/xmtdb/etc
        fileMetaService.loadFileMetas(file2Path);
    }
}