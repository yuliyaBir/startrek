package be.vdab.startrek.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record NieuweBestelling (@NotBlank String omschrijving, @Positive BigDecimal bedrag){
}
