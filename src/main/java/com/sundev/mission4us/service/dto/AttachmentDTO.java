package com.sundev.mission4us.service.dto;

import com.sundev.mission4us.domain.enumeration.AttachmentType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.sundev.mission4us.domain.Attachment} entity.
 */
public class AttachmentDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String filename;

    private AttachmentType type;

    private Boolean isValidated;

    private Boolean isUpdatedInS3;

    @Lob
    private byte[] data;

    private String dataContentType;
    private String extension;

    private Long fileSize;

    private ZonedDateTime created;

    private Long createdBy;

    private ZonedDateTime updated;

    private Long updatedBy;

    private ClientDTO client;

    private ProviderDTO provider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public Boolean getIsUpdatedInS3() {
        return isUpdatedInS3;
    }

    public void setIsUpdatedInS3(Boolean isUpdatedInS3) {
        this.isUpdatedInS3 = isUpdatedInS3;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public ProviderDTO getProvider() {
        return provider;
    }

    public void setProvider(ProviderDTO provider) {
        this.provider = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentDTO)) {
            return false;
        }

        AttachmentDTO attachmentDTO = (AttachmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attachmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentDTO{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            ", type='" + getType() + "'" +
            ", isValidated='" + getIsValidated() + "'" +
            ", isUpdatedInS3='" + getIsUpdatedInS3() + "'" +
            ", data='" + getData() + "'" +
            ", extension='" + getExtension() + "'" +
            ", fileSize=" + getFileSize() +
            ", created='" + getCreated() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updated='" + getUpdated() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            ", client=" + getClient() +
            ", provider=" + getProvider() +
            "}";
    }
}
