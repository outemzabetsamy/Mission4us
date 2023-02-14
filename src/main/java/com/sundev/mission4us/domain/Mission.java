package com.sundev.mission4us.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sundev.mission4us.domain.enumeration.MissionType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Mission.
 */
@Entity
@Table(name = "mission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mission implements Serializable {

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
    private MissionType type;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @Column(name = "updated_by")
    private Long updatedBy;

    @OneToMany(mappedBy = "mission")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "provider", "mission" }, allowSetters = true)
    private Set<Quote> quotes = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_mission__language",
        joinColumns = @JoinColumn(name = "mission_id"),
        inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "providers", "missions" }, allowSetters = true)
    private Set<Language> languages = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "missions", "attachments" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Mission name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MissionType getType() {
        return this.type;
    }

    public Mission type(MissionType type) {
        this.setType(type);
        return this;
    }

    public void setType(MissionType type) {
        this.type = type;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public Mission created(ZonedDateTime created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Mission createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public Mission updated(ZonedDateTime updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Mission updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<Quote> getQuotes() {
        return this.quotes;
    }

    public void setQuotes(Set<Quote> quotes) {
        if (this.quotes != null) {
            this.quotes.forEach(i -> i.setMission(null));
        }
        if (quotes != null) {
            quotes.forEach(i -> i.setMission(this));
        }
        this.quotes = quotes;
    }

    public Mission quotes(Set<Quote> quotes) {
        this.setQuotes(quotes);
        return this;
    }

    public Mission addQuote(Quote quote) {
        this.quotes.add(quote);
        quote.setMission(this);
        return this;
    }

    public Mission removeQuote(Quote quote) {
        this.quotes.remove(quote);
        quote.setMission(null);
        return this;
    }

    public Set<Language> getLanguages() {
        return this.languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Mission languages(Set<Language> languages) {
        this.setLanguages(languages);
        return this;
    }

    public Mission addLanguage(Language language) {
        this.languages.add(language);
        language.getMissions().add(this);
        return this;
    }

    public Mission removeLanguage(Language language) {
        this.languages.remove(language);
        language.getMissions().remove(this);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Mission client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mission)) {
            return false;
        }
        return id != null && id.equals(((Mission) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mission{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", created='" + getCreated() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updated='" + getUpdated() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
