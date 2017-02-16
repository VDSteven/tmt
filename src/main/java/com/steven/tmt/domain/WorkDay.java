package com.steven.tmt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A WorkDay.
 */
@Entity
@Table(name = "work_day")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "day", nullable = false)
    private LocalDate day;

    @Column(name = "is_holiday")
    private Boolean isHoliday;

    @DecimalMin(value = "0")
    @Column(name = "hours")
    private Double hours;

    @Column(name = "hours_approved")
    private Boolean hoursApproved;

    @DecimalMin(value = "0")
    @Column(name = "expenses")
    private Double expenses;

    @Column(name = "expenses_approved")
    private Boolean expensesApproved;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public WorkDay day(LocalDate day) {
        this.day = day;
        return this;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Boolean isIsHoliday() {
        return isHoliday;
    }

    public WorkDay isHoliday(Boolean isHoliday) {
        this.isHoliday = isHoliday;
        return this;
    }

    public void setIsHoliday(Boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

    public Double getHours() {
        return hours;
    }

    public WorkDay hours(Double hours) {
        this.hours = hours;
        return this;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Boolean isHoursApproved() {
        return hoursApproved;
    }

    public WorkDay hoursApproved(Boolean hoursApproved) {
        this.hoursApproved = hoursApproved;
        return this;
    }

    public void setHoursApproved(Boolean hoursApproved) {
        this.hoursApproved = hoursApproved;
    }

    public Double getExpenses() {
        return expenses;
    }

    public WorkDay expenses(Double expenses) {
        this.expenses = expenses;
        return this;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public Boolean isExpensesApproved() {
        return expensesApproved;
    }

    public WorkDay expensesApproved(Boolean expensesApproved) {
        this.expensesApproved = expensesApproved;
        return this;
    }

    public void setExpensesApproved(Boolean expensesApproved) {
        this.expensesApproved = expensesApproved;
    }

    public User getUser() {
        return user;
    }

    public WorkDay user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public WorkDay project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkDay workDay = (WorkDay) o;
        if (workDay.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workDay.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkDay{" +
            "id=" + id +
            ", day='" + day + "'" +
            ", isHoliday='" + isHoliday + "'" +
            ", hours='" + hours + "'" +
            ", hoursApproved='" + hoursApproved + "'" +
            ", expenses='" + expenses + "'" +
            ", expensesApproved='" + expensesApproved + "'" +
            '}';
    }
}
