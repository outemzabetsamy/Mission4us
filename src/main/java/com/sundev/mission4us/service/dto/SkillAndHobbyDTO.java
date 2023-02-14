package com.sundev.mission4us.service.dto;

import com.sundev.mission4us.domain.enumeration.OccupationAndSkillType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sundev.mission4us.domain.SkillAndHobby} entity.
 */
public class SkillAndHobbyDTO implements Serializable {

    private Long id;

    private String name;

    private OccupationAndSkillType type;

    private String description;

    private Long rating;

    private ProviderDTO provider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OccupationAndSkillType getType() {
        return type;
    }

    public void setType(OccupationAndSkillType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
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
        if (!(o instanceof SkillAndHobbyDTO)) {
            return false;
        }

        SkillAndHobbyDTO skillAndHobbyDTO = (SkillAndHobbyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, skillAndHobbyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillAndHobbyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", rating=" + getRating() +
            ", provider=" + getProvider() +
            "}";
    }
}
