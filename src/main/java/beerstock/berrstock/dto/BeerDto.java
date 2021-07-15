package beerstock.berrstock.dto;

import beerstock.berrstock.enums.BeerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data               //automaticamente insere getter e setter
@Builder            //auxilia na contrução de objetos para o constructor
@AllArgsConstructor //gera um construtor com todos os argumentos
@NoArgsConstructor  //
public class BeerDto {


    private Long id;

    @NotEmpty
    @Size(min=4,max=50)
    private String brand;

    @NotEmpty
    private int max;

    @NotEmpty
    private int quantity;

    @NotEmpty
    private BeerType type;
}
