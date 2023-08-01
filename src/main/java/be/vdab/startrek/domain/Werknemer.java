package be.vdab.startrek.domain;

import be.vdab.startrek.exceptions.BudgetNietGenoegException;

import java.math.BigDecimal;

public class Werknemer {
    private long id;
    private final String voornaam;
    private final String familienaam;
    private BigDecimal budget;

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
    public void updateBudget(BigDecimal bedrag){
        if (bedrag.compareTo(budget) <= 0) {
            budget = budget.subtract(bedrag);
        } else {
            throw new BudgetNietGenoegException();
        }
    }
}
