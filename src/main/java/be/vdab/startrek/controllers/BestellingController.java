package be.vdab.startrek.controllers;

import be.vdab.startrek.domain.Bestelling;
import be.vdab.startrek.dto.NieuweBestelling;
import be.vdab.startrek.exceptions.BestellingNietGevondenException;
import be.vdab.startrek.services.BestellingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("bestellingen")
public class BestellingController {
    private final BestellingService bestellingService;

    public BestellingController(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }
    @GetMapping("{werknemerId}")
    public List<Bestelling> findByWerknemerId(@PathVariable long werknemerId){
        var bestellingen = bestellingService.findByWerknemerId(werknemerId);
        if (bestellingen.stream().count() == 0){
            throw new BestellingNietGevondenException();
        }
        return bestellingen;
    }
    @PostMapping("{werknemerId}/nieuweBestelling")
    public long create (@PathVariable long werknemerId, @RequestBody @Valid NieuweBestelling nieuweBestelling){
        var bestelling = new Bestelling(werknemerId, nieuweBestelling.omschrijving(),
                nieuweBestelling.bedrag(), LocalDateTime.now());
        return bestellingService.create(bestelling);
    }
}
