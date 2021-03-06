package ru.job4j.passport.service;

import org.springframework.stereotype.Service;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PassportService {

    private final PassportRepository repository;

    public static final int PASSPORT_TO_CHANGE_PERIOD = 3;

    public PassportService(PassportRepository repository) {
        this.repository = repository;
    }

    public Passport save(Passport passport) {
        Optional<Passport> optPassport = findBySeriesAndNumber(
                        passport.getSeries(),
                        passport.getNumber());
        if (optPassport.isPresent()) {
            throw new IllegalArgumentException("Passport already exists");
        }
        return repository.save(passport);
    }

    public void deleteById(int id) {
        Optional<Passport> optPassport = findById(id);
        if (optPassport.isEmpty()) {
            throw new NoSuchElementException(
                    "Passport with this id doesn't exist");
        }
        repository.deleteById(id);
    }

    public Optional<Passport> findById(int id) {
        return repository.findById(id);
    }

    public List<Passport> findAll() {
        return (List<Passport>) repository.findAll();
    }

    public Optional<Passport> findBySeriesAndNumber(int series, int number) {
        return repository.findBySeriesAndNumber(series, number);
    }

    public boolean update(int id, Passport passport) {
        boolean result = false;
        Optional<Passport> optionalPassport = findById(id);
        if (optionalPassport.isPresent()) {
            passport.setId(id);
            repository.save(passport);
            result = true;
        }
        return result;
    }

    public List<Passport> findExpired() {
        Calendar calendarNow = Calendar.getInstance();
        return repository.findExpired(calendarNow);
    }

    /**
     *
     * @return ???????????? ???????? ??????????????????, ?????????????? ???????????????????? ????????????????
     * ?????????? ???????????? PASSPORT_TO_CHANGE_PERIOD = 3 ????????????
     */
    public List<Passport> findReplaced() {
        Calendar calendarNow = Calendar.getInstance();
        Calendar replaced = Calendar.getInstance();
        replaced.add(Calendar.MONTH, PASSPORT_TO_CHANGE_PERIOD);
        return repository.findReplaced(calendarNow, replaced);
    }





}
