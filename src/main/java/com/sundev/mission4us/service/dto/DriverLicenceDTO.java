package com.sundev.mission4us.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sundev.mission4us.domain.DriverLicence} entity.
 */
public class DriverLicenceDTO implements Serializable {

    private Long id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DriverLicenceDTO)) {
            return false;
        }

        DriverLicenceDTO driverLicenceDTO = (DriverLicenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, driverLicenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DriverLicenceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
