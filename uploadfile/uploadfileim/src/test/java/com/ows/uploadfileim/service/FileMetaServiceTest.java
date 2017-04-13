package com.ows.uploadfileim.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ows.uploadfileim.Application;
import com.ows.uploadfileim.domain.FileMeta;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class FileMetaServiceTest {
    @Autowired
    private FileMetaService fileMetaService;
    
    @Before
    public void startUp() throws IOException {
    	fileMetaService.loadFileMetas(getClass().getResource("/filemetas-test.txt").getFile());
    }
    
    @After
    public void tearDown() {
    	fileMetaService.deleteAllFileMetas();
    }
    
    @Test
    public void testSearchFileMetas() {
        List<FileMeta> fileMetas;
        
        // search all
        fileMetas = fileMetaService.searchFileMetas("", 0, 10);
        assertThat(fileMetas.size(), is(equalTo(5)));
        
        // search by id
        fileMetas = fileMetaService.searchFileMetas("00", 0, 10);
        assertThat(fileMetas.size(), is(equalTo(5)));
        
        // search by name
        fileMetas = fileMetaService.searchFileMetas("name1", 0, 10);
        assertThat(fileMetas.size(), is(equalTo(1)));
        assertThat(fileMetas.get(0).getId(), is(equalTo("001")));
        
        // search by fullName
        fileMetas = fileMetaService.searchFileMetas("fullName2", 0, 10);
        assertThat(fileMetas.size(), is(equalTo(1)));
        assertThat(fileMetas.get(0).getId(), is(equalTo("002")));
        
        // search by jobTitle
        fileMetas = fileMetaService.searchFileMetas("title3", 0, 10);
        assertThat(fileMetas.size(), is(equalTo(1)));
        assertThat(fileMetas.get(0).getId(), is(equalTo("003")));
        
        // search by email
        fileMetas = fileMetaService.searchFileMetas("email4", 0, 10);
        assertThat(fileMetas.size(), is(equalTo(1)));
        assertThat(fileMetas.get(0).getId(), is(equalTo("004")));
        
        // search by skypeId
        fileMetas = fileMetaService.searchFileMetas("skypeId5", 0, 10);
        assertThat(fileMetas.size(), is(equalTo(1)));
        assertThat(fileMetas.get(0).getId(), is(equalTo("005")));
    }
    
    @Test
    public void testSaveFileMeta() {
        // create
        FileMeta filemeta = new FileMeta("new name", "new full name");
        fileMetaService.saveFileMeta(filemeta);
        
        List<FileMeta> filemetas;
        filemetas = fileMetaService.searchFileMetas("new name", 0, 10);
        assertThat(filemetas.size(), is(equalTo(1)));
        filemeta = filemetas.get(0);
        assertThat(filemeta.getFullName(), is(equalTo("new full name")));
        
        // update
        filemeta = fileMetaService.getFileMeta("001");
        assertNotNull(filemeta);
        filemeta.setFullName("update full name");
        fileMetaService.saveFileMeta(filemeta);
        filemeta = fileMetaService.getFileMeta("001");
        assertThat(filemeta.getFullName(), is(equalTo("update full name")));
    }
    
    @Test
    public void testDeleteFileMeta() {
        List<FileMeta> filemetas;
        filemetas = fileMetaService.searchFileMetas("", 0, 10);
        assertThat(filemetas.size(), is(equalTo(5)));
        
        fileMetaService.deleteFileMetas("001", "002");
        
        filemetas = fileMetaService.searchFileMetas("", 0, 10);
        assertThat(filemetas.size(), is(equalTo(3)));
        
        filemetas.forEach(filemeta -> {
            assertThat(filemeta.getId(), is(not("001")));
            assertThat(filemeta.getId(), is(not("00")));
        });
    }
}
