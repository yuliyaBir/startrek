package be.vdab.startrek.services;

import be.vdab.startrek.domain.Bestelling;
import be.vdab.startrek.exceptions.WerknemerNietGevondenException;
import be.vdab.startrek.repositories.BestellingRepository;
import be.vdab.startrek.repositories.WerknemerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BestellingService {
    private final BestellingRepository bestellingRepository;
    private final WerknemerRepository werknemerRepository;

    public BestellingService(BestellingRepository bestellingRepository, WerknemerRepository werknemerRepository) {
        this.bestellingRepository = bestellingRepository;
        this.werknemerRepository = werknemerRepository;
    }

    public List<Bestelling> findByWerknemerId(long werknemerId){
        return bestellingRepository.findByWerknemerId(werknemerId);
    }
    @Transactional
    public long create(Bestelling bestelling){
        var werknemerId = bestelling.getWerknemerId();
        var bedrag = bestelling.getBedrag();
        var werknemer = werknemerRepository.findEnLockById(werknemerId)
                .orElseThrow(()-> new WerknemerNietGevondenException(werknemerId));
        werknemer.updateBudget(bedrag);
        werknemerRepository.updateBudget(werknemerId, bedrag);
        return bestellingRepository.create(bestelling);
    }

}
