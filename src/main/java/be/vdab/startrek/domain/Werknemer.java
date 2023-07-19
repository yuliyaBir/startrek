package be.vdab.startrek.domain;

import java.math.BigDecimal;

public class Werknemer {
    private long id;
    private final String voornaam;
    private final String familienaam;
    private final BigDecimal budget;

    public Werknemer(long id, String voornaam, String familienaam, BigDecimal budget) {
        this.id = id;
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.budget = budget;
    }

    public Werknemer(String voornaam, String familienaam, BigDecimal budget) {
        this(0, voornaam, familienaam, budget);
    }

    public long getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public BigDecimal getBudget() {
        return budget;
    }
}
