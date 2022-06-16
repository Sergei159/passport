package ru.job4j.passport.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import ru.job4j.passport.handlers.Operation;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "passport")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must not be null",
            groups = {Operation.OnUpdate.class, Operation.OnDelete.class})
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
    private Calendar created;

    public static Passport of(int series, int number, Calendar created) {
        Passport passport = new Passport();
        passport.series = series;
        passport.number = number;
        passport.created = created;
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

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
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
