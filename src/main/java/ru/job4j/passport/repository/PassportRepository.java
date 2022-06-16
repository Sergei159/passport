package ru.job4j.passport.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.passport.model.Passport;

import java.util.Calendar;
import java.util.List;

@Repository
public interface PassportRepository
        extends CrudRepository<Passport, Integer> {


    @Query("from Passport p where p.created <= :expDate")
    public List<Passport> findExpired(@Param("expDate") Calendar expDate);

    @Query("from Passport p where p.created between :expDate and :replacedDate")
    public List<Passport> findReplaced(@Param("expDate") Calendar expDate,
                                       @Param("replacedDate") Calendar replacedDate);

    @Query("from Passport p where p.series = :series and p.number =:number")
    public List<Passport> findBySeriesAndNumber(
            @Param("series") int series,
            @Param("number") int number);
}
