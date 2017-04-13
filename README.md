# uploadfile

## Description:
Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications. All configuration is done by annotations. You don’t need a single XML configuration file. Here we use in memory H2 db.

## System Requirement :
JAVA 1.8 Spring Boot 1.5.1 H2 db Hybernate Spring Security

## Below available REST API:
## Search: method "GET" base on "keyword, page, pageSize" keyword may id/name/fullname/skypeid etc.
http://localhost:8080/rest/filemetas?keyword=005

## Create Meta: method "POST" base on @RequestBody @Valid FileMeta filemeta.
http://localhost:8080/rest/filemetas

## update method "PUT" base on Id.
http://localhost:8080/rest/filemetas/005

## delete method "DELETE" base on @RequestParam String[] ids.
http://localhost:8080/rest/filemetas

## get/download content method "GET" base on id.
http://localhost:8080/rest/photos/007

## upload content method "POST" base on id.
http://localhost:8080/rest/photos/007

Continue..........
