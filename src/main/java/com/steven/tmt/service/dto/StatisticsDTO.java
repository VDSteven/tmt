package com.steven.tmt.service.dto;

public class StatisticsDTO {
    private int monthNumber;

    private Double hours;

    private Long holidays;

    private Double expenses;

    public StatisticsDTO(int monthNumber, Long holidays, Double hours, Double expenses) {
        this.monthNumber = monthNumber;
        this.holidays = holidays;
        this.hours = hours;
        this.expenses = expenses;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public Long getHolidays() {

        return holidays;
    }

    public void setHolidays(Long holidays) {
        this.holidays = holidays;
    }

    @Override
    public String toString() {
        return "StatisticsDTO{" +
            "monthNumber='" + monthNumber + '\'' +
            ", hours='" + hours + '\'' +
            ", holidays='" + holidays + '\'' +
            ", expenses='" + expenses +
            "}";
    }
}
