package beerstock.berrstock.service;


import beerstock.berrstock.dto.BeerDto;
import beerstock.berrstock.dto.MessageRequestDto;
import beerstock.berrstock.entity.Beer;
import beerstock.berrstock.exceptions.BeerNotFoundException;
import beerstock.berrstock.mappers.BeerMapper;
import beerstock.berrstock.repository.BeerRepository;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Builder
public class BeerService {

    private BeerRepository beerRepo;

    private final BeerMapper beerMapper = BeerMapper.INSTANCE;

    @Autowired
    public BeerService(BeerRepository beerRepo) {
        this.beerRepo = beerRepo;
    }



    public List<BeerDto> retrieveAll() {

        return beerRepo.findAll().stream().map(beerMapper::toBeerDTO).collect(Collectors.toList());
    }

    public BeerDto findByName(String name) throws BeerNotFoundException {
        List<Beer> lt= beerRepo.findAll().stream().filter(s->s.getBrand().equals(name)).collect(Collectors.toList());

        BeerDto encontrada = beerMapper.toBeerDTO(lt.get(0));
        return encontrada;
    }



    public MessageRequestDto createBeer(BeerDto cerveja) {


        Beer beertosave = beerMapper.toBeer(cerveja);
        Beer personsaved = this.beerRepo.save(beertosave);
        return createMessageRequestDto(personsaved.getId(),"Created Beer... ");
    }

    public BeerDto createBeerTest(BeerDto cerveja) {


        Beer beertosave = beerMapper.toBeer(cerveja);
        BeerDto personsaved = beerMapper.toBeerDTO(beertosave);
        return personsaved;
    }

    public BeerDto findById(Long id) throws BeerNotFoundException {
        Beer beer = BeerIsExists(id);
        return beerMapper.toBeerDTO(beer);
    }

    public BeerDto findByBrand(String name) throws BeerNotFoundException {
        Beer beer = beerRepo.findByBrand(name).orElseThrow(()->new BeerNotFoundException(name));
        return beerMapper.toBeerDTO(beer);
    }

    public Beer BeerIsExists(Long id) throws BeerNotFoundException {
        Beer beer = this.beerRepo.findById(id).orElseThrow(()->new BeerNotFoundException(id));
        return beer;
    }

    private MessageRequestDto createMessageRequestDto(Long id, String s) {
        return MessageRequestDto.builder().message(s+ id).build();
    }

    public MessageRequestDto updateById(Long id, BeerDto beerDto) throws BeerNotFoundException {
        Beer beerToUpdate = this.BeerIsExists(id);
        Beer atualizada = this.beerMapper.toBeer(beerDto);
        this.beerRepo.save(beerToUpdate);
        return createMessageRequestDto(atualizada.getId(),"Updated Beer... ");

    }

    public void deleteById(Long id) throws BeerNotFoundException {
        Beer beerToDelete = BeerIsExists(id);
        this.beerRepo.delete(beerToDelete);
    }
}
