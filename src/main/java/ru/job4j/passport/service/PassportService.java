package ru.job4j.passport.service;

import org.springframework.stereotype.Service;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class PassportService {

    private final PassportRepository repository;

    public static final int PASSPORT_VALIDITY_PERIOD = 20;
    public static final int PASSPORT_TO_CHANGE_PERIOD = 3;

    public PassportService(PassportRepository repository) {
        this.repository = repository;
    }

    public Passport save(Passport passport) {
        return repository.save(passport);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public Optional<Passport> findById(int id) {
        return repository.findById(id);
    }

    public List<Passport> findAll() {
        return (List<Passport>) repository.findAll();
    }

    public List<Passport> findBySeriesAndNumber(int series, int number) {
        return repository.findBySeriesAndNumber(series, number);
    }

    public boolean update(int id, Passport passport) {
        boolean result = false;
        Optional<Passport> optionalPassport = findById(id);
        if (optionalPassport.isPresent()) {
            repository.save(passport);
            result = true;
        }
        return result;
    }

    public List<Passport> findExpired() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -PASSPORT_VALIDITY_PERIOD);
        return repository.findExpired(calendar);
    }

    public List<Passport> findReplaced() {
        Calendar expired = Calendar.getInstance();
        Calendar replaced = Calendar.getInstance();
        expired.add(Calendar.YEAR, -PASSPORT_VALIDITY_PERIOD);
        replaced.add(Calendar.MONTH, PASSPORT_TO_CHANGE_PERIOD);
        return repository.findReplaced(expired, replaced);
    }





}
