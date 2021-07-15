package beerstock.berrstock.mappers;

import beerstock.berrstock.dto.BeerDto;
import beerstock.berrstock.entity.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.Mapping;

@Mapper()
public interface BeerMapper {

    BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);


    Beer toBeer(BeerDto beerDto);

    BeerDto toBeerDTO(Beer beer);
}
