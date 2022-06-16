package ru.job4j.passport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.passport.model.Passport;
import ru.job4j.passport.handlers.Operation;;
import ru.job4j.passport.service.PassportService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/passport")
public class PassportController {
    private PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @PostMapping("/save")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Passport> create(@Valid @RequestBody Passport passport) {
        Optional<Passport> optPassport
                = passportService.findById(passport.getId());
        if (optPassport.isPresent()) {
            return new ResponseEntity<>(
                    passportService.save(passport),
                    HttpStatus.CREATED
            );
        } else {
            return new ResponseEntity<>(
                    new Passport(),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    @Validated(Operation.OnDelete.class)
    public ResponseEntity<Void> delete(@Valid @PathVariable int id) {
        Optional<Passport> optPassport
                = passportService.findById(id);
        if (optPassport.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find")
    public List<Passport> findAll() {
        return passportService.findAll();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Passport passport) {
        return passportService.update(id, passport) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/find/{series}-{number}")
    public ResponseEntity<List<Passport>> findSeries(@PathVariable int series,
                                                     @PathVariable int number) {
        return new ResponseEntity<>(
                passportService.findBySeriesAndNumber(series, number),
                HttpStatus.OK
        );
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Passport>> findExpired() {
        return new ResponseEntity<>(
                this.passportService.findExpired(),
                HttpStatus.OK
        );
    }

    @GetMapping("/find-replace")
    public ResponseEntity<List<Passport>> findReplaced() {
        return new ResponseEntity<>(
                this.passportService.findReplaced(),
                HttpStatus.OK
        );
    }
}