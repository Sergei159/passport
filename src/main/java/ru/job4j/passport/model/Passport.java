package ru.job4j.passport.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "passport")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1000,
            message = "The series must consists of 4 digits")
    @Max(value = 9999,
            message = "The series must consists of 4 digits")
    private int series;
    @Min(value = 100_000,
            message = "The number must consists of 6 digits")
    @Max(value = 999_999,
            message = "The number must consists of 6 digits")
    private int number;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Calendar expired;

    public static Passport of(int series, int number, Calendar expired) {
        Passport passport = new Passport();
        passport.series = series;
        passport.number = number;
        passport.expired = expired;
        return  passport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Calendar getExpired() {
        return expired;
    }

    public void setExpired(Calendar expired) {
        this.expired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passport passport = (Passport) o;
        return id == passport.id && series == passport.series && number == passport.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, series, number);
    }


}
