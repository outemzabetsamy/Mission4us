package com.sundev.mission4us.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sundev.mission4us.domain.enumeration.OccupationAndSkillType;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SkillAndHobby.
 */
@Entity
@Table(name = "skill_and_hobby")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SkillAndHobby implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OccupationAndSkillType type;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Long rating;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "user", "quotes", "attachments", "experiences", "skillAndHobbies", "languages", "jobs", "driverLicences" },
        allowSetters = true
    )
    private Provider provider;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SkillAndHobby id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SkillAndHobby name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OccupationAndSkillType getType() {
        return this.type;
    }

    public SkillAndHobby type(OccupationAndSkillType type) {
        this.setType(type);
        return this;
    }

    public void setType(OccupationAndSkillType type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public SkillAndHobby description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRating() {
        return this.rating;
    }

    public SkillAndHobby rating(Long rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public SkillAndHobby provider(Provider provider) {
        this.setProvider(provider);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SkillAndHobby)) {
            return false;
        }
        return id != null && id.equals(((SkillAndHobby) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillAndHobby{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", rating=" + getRating() +
            "}";
    }
}
