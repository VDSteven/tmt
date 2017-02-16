package com.steven.tmt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A IsHeadOf.
 */
@Entity
@Table(name = "is_head_of", uniqueConstraints = @UniqueConstraint(columnNames = {"head_id", "employee_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IsHeadOf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private User head;

    @ManyToOne(optional = false)
    @NotNull
    private User employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getHead() {
        return head;
    }

    public IsHeadOf head(User user) {
        this.head = user;
        return this;
    }

    public void setHead(User user) {
        this.head = user;
    }

    public User getEmployee() {
        return employee;
    }

    public IsHeadOf employee(User user) {
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
        IsHeadOf isHeadOf = (IsHeadOf) o;
        if (isHeadOf.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, isHeadOf.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "IsHeadOf{" +
            "id=" + id +
            '}';
    }
}
