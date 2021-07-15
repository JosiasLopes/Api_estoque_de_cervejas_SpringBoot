package beerstock.berrstock.builder;

import beerstock.berrstock.dto.BeerDto;
import beerstock.berrstock.enums.BeerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data               //automaticamente insere getter e setter
@Builder            //auxilia na contrução de objetos para o constructor
@AllArgsConstructor //gera um construtor com todos os argumentos
@NoArgsConstructor  //
public class BeerDtoBulider {




        @Builder.Default
        private Long id = 1L;

        @NotEmpty
        @Size(min=4,max=50)
        @Builder.Default
        private String brand = "Bhrama";

        @NotEmpty
        @Builder.Default
        private int max = 10;

        @NotEmpty
        @Builder.Default
        private int quantity = 50;

        @NotEmpty
        @Builder.Default
        private BeerType type = BeerType.LAGER;

        public BeerDto toBeerDto(){
                return new BeerDto(
                        id,brand,max,quantity,type
                );

        }

}
