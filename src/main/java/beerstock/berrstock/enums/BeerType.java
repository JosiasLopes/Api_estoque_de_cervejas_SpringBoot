package beerstock.berrstock.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BeerType {
    LAGER("Lager"),
    MALZBEER("Malzberr"),
    WITBBER("Witbeer"),
    STOUT("stout"),
    ALEC("Alec");

   private final String description;
}
