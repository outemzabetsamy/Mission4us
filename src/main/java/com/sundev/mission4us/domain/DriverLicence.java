package com.sundev.mission4us.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DriverLicence.
 */
@Entity
@Table(name = "driver_licence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DriverLicence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "driverLicences")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "user", "quotes", "attachments", "experiences", "skillAndHobbies", "languages", "jobs", "driverLicences" },
        allowSetters = true
    )
    private Set<Provider> providers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DriverLicence id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DriverLicence name(String name) {
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
            this.providers.forEach(i -> i.removeDriverLicence(this));
        }
        if (providers != null) {
            providers.forEach(i -> i.addDriverLicence(this));
        }
        this.providers = providers;
    }

    public DriverLicence providers(Set<Provider> providers) {
        this.setProviders(providers);
        return this;
    }

    public DriverLicence addProvider(Provider provider) {
        this.providers.add(provider);
        provider.getDriverLicences().add(this);
        return this;
    }

    public DriverLicence removeProvider(Provider provider) {
        this.providers.remove(provider);
        provider.getDriverLicences().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DriverLicence)) {
            return false;
        }
        return id != null && id.equals(((DriverLicence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DriverLicence{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
