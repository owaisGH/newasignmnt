package com.ows.uploadfileim.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ows.uploadfileim.domain.FileMeta;
import com.ows.uploadfileim.service.FileMetaService;



/**
 * @author mohaahma
 */
@RestController
@RequestMapping(value = "/rest/filemetas")
public class FileMetaController extends BaseController {
    @Autowired
    private FileMetaService fileMetaService;
    
    @RequestMapping(method = GET)
    public List<FileMeta> searchFileMetas(
            @RequestParam(defaultValue="") String keyword, 
            @RequestParam(defaultValue="0") int page, 
            @RequestParam(defaultValue="200") int pageSize) {
        traceAction("search filemetas, keyword: " + keyword);
        return fileMetaService.searchFileMetas(keyword, page, pageSize);
    }

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public FileMeta createFileMeta(@RequestBody @Valid FileMeta filemeta) {
        traceAction("create filemeta, name: " + filemeta.getName());
        filemeta.setId(null);
        return fileMetaService.saveFileMeta(filemeta);
    }
    
    @RequestMapping(value = "/{id}", method = PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public FileMeta updateFilMFileMeta(@PathVariable String id, @RequestBody @Valid FileMeta filemeta) {
        traceAction("update filemeta, id: " + id);
        filemeta.setId(id);
        return fileMetaService.saveFileMeta(filemeta);
    }
    
    @RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFileMetas(@RequestParam String[] ids) {
        traceAction("delete filemetas, ids: '" + Arrays.toString(ids));
        fileMetaService.deleteFileMetas(ids);
    }
}
