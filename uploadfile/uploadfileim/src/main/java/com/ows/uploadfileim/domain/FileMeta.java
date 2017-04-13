package com.ows.uploadfileim.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author mohaahma
 */
@Entity
@Table(name = "FILEMETA") 
public class FileMeta {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    @NotEmpty(message = "{validation.not-empty.message}")
    private String name;

    @Column(name = "FULL_NAME")
    @NotEmpty(message = "{validation.not-empty.message}")
    private String fullName;

    @Column(name = "JOB_TITLE")
    private String jobTitle;

    @Column(name = "EMAIL")
    @Email(message = "{validation.email.message}")
    private String email;

    @Column(name = "MOBILE")
    @Pattern(regexp = "\\d{3,4} \\d{3} \\d{4}", message = "{validation.mobile.message}")
    private String mobile;

    @Column(name = "SKYPE_ID")
    @Pattern(regexp = "[A-Za-z0-9_,\\-\\.]{6,32}", message = "{validation.skypeid.message}")
    private String skypeId;
    
    public FileMeta() {
    }
    
    public FileMeta(String name, String fullName) {
        this.name = name;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (id != null && obj instanceof FileMeta) {
            return id.equals(((FileMeta) obj).name);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (id == null) ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s|%s|%s", id, name, fullName, jobTitle, email, mobile, skypeId);
    }
    
    public static FileMeta parseFileMeta(String fileMetaLine) {
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
