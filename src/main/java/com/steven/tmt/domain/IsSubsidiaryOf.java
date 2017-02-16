package com.steven.tmt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A IsSubsidiaryOf.
 */
@Entity
@Table(name = "is_subsidiary_of", uniqueConstraints = @UniqueConstraint(columnNames = {"subsidiary_id", "employee_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IsSubsidiaryOf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private User subsidiary;

    @ManyToOne(optional = false)
    @NotNull
    private User employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSubsidiary() {
        return subsidiary;
    }

    public IsSubsidiaryOf subsidiary(User user) {
        this.subsidiary = user;
        return this;
    }

    public void setSubsidiary(User user) {
        this.subsidiary = user;
    }

    public User getEmployee() {
        return employee;
    }

    public IsSubsidiaryOf employee(User user) {
        this.employee = user;
        return this;
    }

    public void setEmployee(User user) {
        this.employee = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IsSubsidiaryOf isSubsidiaryOf = (IsSubsidiaryOf) o;
        if (isSubsidiaryOf.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, isSubsidiaryOf.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "IsSubsidiaryOf{" +
            "id=" + id +
            '}';
    }
}
