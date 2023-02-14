package com.sundev.mission4us.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Language.
 */
@Entity
@Table(name = "language")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "languages")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "user", "quotes", "attachments", "experiences", "skillAndHobbies", "languages", "jobs", "driverLicences" },
        allowSetters = true
    )
    private Set<Provider> providers = new HashSet<>();

    @ManyToMany(mappedBy = "languages")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "quotes", "languages", "client" }, allowSetters = true)
    private Set<Mission> missions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Language id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Language code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Language name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Provider> getProviders() {
        return this.providers;
    }

    public void setProviders(Set<Provider> providers) {
        if (this.providers != null) {
            this.providers.forEach(i -> i.removeLanguage(this));
        }
        if (providers != null) {
            providers.forEach(i -> i.addLanguage(this));
        }
        this.providers = providers;
    }

    public Language providers(Set<Provider> providers) {
        this.setProviders(providers);
        return this;
    }

    public Language addProvider(Provider provider) {
        this.providers.add(provider);
        provider.getLanguages().add(this);
        return this;
    }

    public Language removeProvider(Provider provider) {
        this.providers.remove(provider);
        provider.getLanguages().remove(this);
        return this;
    }

    public Set<Mission> getMissions() {
        return this.missions;
    }

    public void setMissions(Set<Mission> missions) {
        if (this.missions != null) {
            this.missions.forEach(i -> i.removeLanguage(this));
        }
        if (missions != null) {
            missions.forEach(i -> i.addLanguage(this));
        }
        this.missions = missions;
    }

    public Language missions(Set<Mission> missions) {
        this.setMissions(missions);
        return this;
    }

    public Language addMission(Mission mission) {
        this.missions.add(mission);
        mission.getLanguages().add(this);
        return this;
    }

    public Language removeMission(Mission mission) {
        this.missions.remove(mission);
        mission.getLanguages().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Language)) {
            return false;
        }
        return id != null && id.equals(((Language) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Language{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
