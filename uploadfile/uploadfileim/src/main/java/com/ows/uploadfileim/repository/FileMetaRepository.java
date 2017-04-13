package com.ows.uploadfileim.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ows.uploadfileim.domain.FileMeta;



/**
 * @author mohaahma
 */
public interface FileMetaRepository extends JpaRepository<FileMeta, String> {
    @Query("select c from FileMeta c where lower(c.id) like :keyword% "
            + "or lower(c.name) like :keyword% "
            + "or lower(c.fullName) like :keyword% "
            + "or lower(c.jobTitle) like :keyword% "
            + "or lower(c.email) like :keyword% "
            + "or lower(c.mobile) like :keyword% "
            + "or lower(c.skypeId) like :keyword% "
            + "order by c.name")
    List<FileMeta> searchFileMetas(@Param("keyword") String keyword, Pageable pageable);
    
    @Modifying
    @Query("delete from FileMeta where id in (:ids)")
    void deleteFileMetas(@Param("ids") String... ids);
}