package ru.beetlerat.socialnetwork.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "images")
public class ImageModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "size")
    private Long size;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "url")
    private String url;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "bytes")
    private byte[] bytes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public ImageModel() {

    }

    public ImageModel(Integer id, String name, String originalFileName, Long size, String contentType, String url, byte[] bytes) {
        this.id = id;
        this.name = name;
        this.originalFileName = originalFileName;
        this.size = size;
        this.contentType = contentType;
        this.url = url;
        this.bytes = bytes;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public Long getSize() {
        return size;
    }

    public String getContentType() {
        return contentType;
    }

    public String getUrl() {
        return url;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public User getUser() {
        return user;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setUser(User user) {
        this.user = user;
        user.setProfilePhoto(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageModel that = (ImageModel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (originalFileName != null ? !originalFileName.equals(that.originalFileName) : that.originalFileName != null)
            return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (contentType != null ? !contentType.equals(that.contentType) : that.contentType != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (originalFileName != null ? originalFileName.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (contentType != null ? contentType.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}
