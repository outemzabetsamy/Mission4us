package com.sundev.mission4us.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Provider.
 */
@Entity
@Table(name = "provider")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "social_reason")
    private String socialReason;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated")
    private ZonedDateTime updated;

    @Column(name = "updated_by")
    private Long updatedBy;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "provider")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "provider", "mission" }, allowSetters = true)
    private Set<Quote> quotes = new HashSet<>();

    @OneToMany(mappedBy = "provider")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client", "provider" }, allowSetters = true)
    private Set<Attachment> attachments = new HashSet<>();

    @OneToMany(mappedBy = "provider")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "provider" }, allowSetters = true)
    private Set<Experience> experiences = new HashSet<>();

    @OneToMany(mappedBy = "provider")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "provider" }, allowSetters = true)
    private Set<SkillAndHobby> skillAndHobbies = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_provider__language",
        joinColumns = @JoinColumn(name = "provider_id"),
        inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "providers", "missions" }, allowSetters = true)
    private Set<Language> languages = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_provider__job",
        joinColumns = @JoinColumn(name = "provider_id"),
        inverseJoinColumns = @JoinColumn(name = "job_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "providers" }, allowSetters = true)
    private Set<Job> jobs = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_provider__driver_licence",
        joinColumns = @JoinColumn(name = "provider_id"),
        inverseJoinColumns = @JoinColumn(name = "driver_licence_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "providers" }, allowSetters = true)
    private Set<DriverLicence> driverLicences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Provider id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Provider firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Provider lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Provider dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStreet() {
        return this.street;
    }

    public Provider street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return this.city;
    }

    public Provider city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public Provider country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSocialReason() {
        return this.socialReason;
    }

    public Provider socialReason(String socialReason) {
        this.setSocialReason(socialReason);
        return this;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Provider phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public Provider email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getCreated() {
        return this.created;
    }

    public Provider created(ZonedDateTime created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public Provider createdBy(Long createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getUpdated() {
        return this.updated;
    }

    public Provider updated(ZonedDateTime updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(ZonedDateTime updated) {
        this.updated = updated;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public Provider updatedBy(Long updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Provider user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Quote> getQuotes() {
        return this.quotes;
    }

    public void setQuotes(Set<Quote> quotes) {
        if (this.quotes != null) {
            this.quotes.forEach(i -> i.setProvider(null));
        }
        if (quotes != null) {
            quotes.forEach(i -> i.setProvider(this));
        }
        this.quotes = quotes;
    }

    public Provider quotes(Set<Quote> quotes) {
        this.setQuotes(quotes);
        return this;
    }

    public Provider addQuote(Quote quote) {
        this.quotes.add(quote);
        quote.setProvider(this);
        return this;
    }

    public Provider removeQuote(Quote quote) {
        this.quotes.remove(quote);
        quote.setProvider(null);
        return this;
    }

    public Set<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        if (this.attachments != null) {
            this.attachments.forEach(i -> i.setProvider(null));
        }
        if (attachments != null) {
            attachments.forEach(i -> i.setProvider(this));
        }
        this.attachments = attachments;
    }

    public Provider attachments(Set<Attachment> attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public Provider addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setProvider(this);
        return this;
    }

    public Provider removeAttachment(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setProvider(null);
        return this;
    }

    public Set<Experience> getExperiences() {
        return this.experiences;
    }

    public void setExperiences(Set<Experience> experiences) {
        if (this.experiences != null) {
            this.experiences.forEach(i -> i.setProvider(null));
        }
        if (experiences != null) {
            experiences.forEach(i -> i.setProvider(this));
        }
        this.experiences = experiences;
    }

    public Provider experiences(Set<Experience> experiences) {
        this.setExperiences(experiences);
        return this;
    }

    public Provider addExperience(Experience experience) {
        this.experiences.add(experience);
        experience.setProvider(this);
        return this;
    }

    public Provider removeExperience(Experience experience) {
        this.experiences.remove(experience);
        experience.setProvider(null);
        return this;
    }

    public Set<SkillAndHobby> getSkillAndHobbies() {
        return this.skillAndHobbies;
    }

    public void setSkillAndHobbies(Set<SkillAndHobby> skillAndHobbies) {
        if (this.skillAndHobbies != null) {
            this.skillAndHobbies.forEach(i -> i.setProvider(null));
        }
        if (skillAndHobbies != null) {
            skillAndHobbies.forEach(i -> i.setProvider(this));
        }
        this.skillAndHobbies = skillAndHobbies;
    }

    public Provider skillAndHobbies(Set<SkillAndHobby> skillAndHobbies) {
        this.setSkillAndHobbies(skillAndHobbies);
        return this;
    }

    public Provider addSkillAndHobby(SkillAndHobby skillAndHobby) {
        this.skillAndHobbies.add(skillAndHobby);
        skillAndHobby.setProvider(this);
        return this;
    }

    public Provider removeSkillAndHobby(SkillAndHobby skillAndHobby) {
        this.skillAndHobbies.remove(skillAndHobby);
        skillAndHobby.setProvider(null);
        return this;
    }

    public Set<Language> getLanguages() {
        return this.languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Provider languages(Set<Language> languages) {
        this.setLanguages(languages);
        return this;
    }

    public Provider addLanguage(Language language) {
        this.languages.add(language);
        language.getProviders().add(this);
        return this;
    }

    public Provider removeLanguage(Language language) {
        this.languages.remove(language);
        language.getProviders().remove(this);
        return this;
    }

    public Set<Job> getJobs() {
        return this.jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public Provider jobs(Set<Job> jobs) {
        this.setJobs(jobs);
        return this;
    }

    public Provider addJob(Job job) {
        this.jobs.add(job);
        job.getProviders().add(this);
        return this;
    }

    public Provider removeJob(Job job) {
        this.jobs.remove(job);
        job.getProviders().remove(this);
        return this;
    }

    public Set<DriverLicence> getDriverLicences() {
        return this.driverLicences;
    }

    public void setDriverLicences(Set<DriverLicence> driverLicences) {
        this.driverLicences = driverLicences;
    }

    public Provider driverLicences(Set<DriverLicence> driverLicences) {
        this.setDriverLicences(driverLicences);
        return this;
    }

    public Provider addDriverLicence(DriverLicence driverLicence) {
        this.driverLicences.add(driverLicence);
        driverLicence.getProviders().add(this);
        return this;
    }

    public Provider removeDriverLicence(DriverLicence driverLicence) {
        this.driverLicences.remove(driverLicence);
        driverLicence.getProviders().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Provider)) {
            return false;
        }
        return id != null && id.equals(((Provider) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Provider{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", street='" + getStreet() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", socialReason='" + getSocialReason() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", created='" + getCreated() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updated='" + getUpdated() + "'" +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
