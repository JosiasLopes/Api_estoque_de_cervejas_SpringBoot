package beerstock.berrstock.service;

import beerstock.berrstock.builder.BeerDtoBulider;
import beerstock.berrstock.dto.BeerDto;
import beerstock.berrstock.dto.MessageRequestDto;
import beerstock.berrstock.entity.Beer;
import beerstock.berrstock.mappers.BeerMapper;
import beerstock.berrstock.repository.BeerRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

    //esse é o mock
    @Mock
    private BeerRepository beerRepo;

    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    //injetamos os mocks na beerService para termos objetos criados e prontos pra usa
    @InjectMocks
    private BeerService beerService;  //essa a classe a ser testada



    @Test
    void testaObjetoPrenchido(){

        //dado
        BeerDto beerdto = BeerDtoBulider.builder().build().toBeerDto();
        Beer exptoSave = beerMapper.toBeer(beerdto);

        //quando
        lenient().when(beerRepo.findByBrand(beerdto.getBrand())).thenReturn(Optional.empty());
        lenient().when(beerRepo.save(exptoSave)).thenReturn(exptoSave);

        //então
        MatcherAssert.assertThat(beerdto.getId(),Matchers.equalTo(exptoSave.getId()));
    }

    @Test
    void whenObjectAlreadyExists(){

        //dado
        BeerDto beerdto = BeerDtoBulider.builder().build().toBeerDto();
        Beer duplicatedBeer = beerMapper.toBeer(beerdto);

        //quando
        lenient().when(beerRepo.findByBrand(beerdto.getBrand())).thenReturn(Optional.of(duplicatedBeer));

        MatcherAssert.assertThat(beerdto,Matchers.equalTo(duplicatedBeer));

    }


}
