package com.ows.uploadfileim.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.ows.uploadfileim.domain.FileMeta;
import com.ows.uploadfileim.repository.FileMetaRepository;



/**
 * @author mohaahma
 */
@Service
@Transactional(readOnly = true)
public class FileMetaService {
    private final AtomicInteger idGeneration = new AtomicInteger(1000);
    
    @Autowired
    private FileMetaRepository fileMetaRepo;
    
    @Autowired
    private GaugeService gaugeService;
    
    @Autowired
    private CounterService counterService;
    
    @Autowired
    private ApplicationContext appContext;

    @Transactional
    public long loadFileMetas(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                         //.parallel()
                         .map(this::parseFileMeta)
                         .map(this::saveFileMeta)
                         .count();
        }
    }

    public List<FileMeta> searchFileMetas(String keyword, int page, int pageSize) {
        keyword = (keyword == null) ? "" : keyword.toLowerCase();

        StopWatch watch = new StopWatch();
        watch.start();
        List<FileMeta> fileMetas = fileMetaRepo.searchFileMetas(keyword, new PageRequest(page, pageSize));
        watch.stop();
        gaugeService.submit("query.by.keyword." + keyword, watch.getTotalTimeMillis());
        counterService.increment("search.by.keyword." + keyword);
        
        return fileMetas;
    }

    public FileMeta getFileMeta(String id) {
        return fileMetaRepo.findOne(id);
    }

    @Transactional
    public FileMeta saveFileMeta(@Valid FileMeta fileMeta) {
        if (fileMeta == null) {
            return null;
        }
        
        if (fileMeta.getId() == null) {
        	fileMeta.setId(String.valueOf(idGeneration.incrementAndGet()));
        }

        return fileMetaRepo.save(fileMeta);
    }

    @Transactional
    public void deleteFileMetas(String... ids) {
    	fileMetaRepo.deleteFileMetas(ids);
    }
    
    @Transactional
    public void deleteAllFileMetas() {
    	fileMetaRepo.deleteAllInBatch();
    }
    
    /**
     * fileMetaLine format: id|name|fullName|jobTitle|email|mobile|skypeId
     */
    private FileMeta parseFileMeta(String fileMetaLine) {
        String[] items = fileMetaLine.split("\\|");
        if (items.length < 2) {
            throw new IllegalArgumentException("Invalid fileMeta-line format: " + fileMetaLine);
        }
        
        FileMeta fileMeta = new FileMeta();
        fileMeta.setId(items[0]);
        fileMeta.setName(items[1]);
        if (items.length > 2) {
        	fileMeta.setFullName(items[2]);
        }
        if (items.length > 3) {
        	fileMeta.setJobTitle(items[3]);
        }
        if (items.length > 4) {
        	fileMeta.setEmail(items[4]);
        }
        if (items.length > 5) {
        	fileMeta.setMobile(items[5]);
        }
        if (items.length > 6) {
        	fileMeta.setSkypeId(items[6]);
        }
        
        return fileMeta;
    }
}
