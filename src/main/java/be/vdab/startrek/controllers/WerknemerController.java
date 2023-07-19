package be.vdab.startrek.controllers;

import be.vdab.startrek.domain.Werknemer;
import be.vdab.startrek.exceptions.WerknemerNietGevondenException;
import be.vdab.startrek.services.WerknemerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("werknemers")
public class WerknemerController {
    private final WerknemerService werknemerService;

    public WerknemerController(WerknemerService werknemerService) {
        this.werknemerService = werknemerService;
    }
    private record WerknemerNaam(String voornaam, String familienaam){
        private WerknemerNaam(Werknemer werknemer) {
            this(werknemer.getVoornaam(), werknemer.getFamilienaam());
        }
    }
    @GetMapping
    List<Werknemer> findall(){
        return werknemerService.findAll();
//        return werknemerService.findAll().stream()
//                .map(werknemer -> new WerknemerNaam(werknemer));
    }
    @GetMapping("{id}")
    Werknemer findById(@PathVariable long id){
        return werknemerService.findById(id).orElseThrow(() -> new WerknemerNietGevondenException(id));
    }
}
