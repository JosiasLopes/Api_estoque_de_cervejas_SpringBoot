package beerstock.berrstock.controller;

import beerstock.berrstock.dto.BeerDto;
import beerstock.berrstock.dto.MessageRequestDto;
import beerstock.berrstock.entity.Beer;
import beerstock.berrstock.exceptions.BeerNotFoundException;
import beerstock.berrstock.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@NoArgsConstructor
@Builder
@RestController
@RequestMapping("api/v1/beer")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerController {

    private BeerService beerServ;

    @GetMapping
    public List<BeerDto> retrieveAll(){
        return beerServ.retrieveAll();
    }


    @GetMapping("/brand/{name}")
    public BeerDto findByName(@PathVariable @Valid String name) throws BeerNotFoundException {
        return beerServ.findByName(name);
       // return beerServ.findByBrand(name);
    }



    @GetMapping("/{id}")
    public BeerDto findById(@PathVariable Long id) throws BeerNotFoundException {
        return beerServ.findById(id);

    }


    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public MessageRequestDto createBeer(@RequestBody @Valid BeerDto cerveja){
        return beerServ.createBeer(cerveja);
    }

    @PutMapping("/{id}")
    public MessageRequestDto Update(@PathVariable Long id, @RequestBody @Valid BeerDto beerDto) throws BeerNotFoundException {
        return beerServ.updateById(id,beerDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  //garante que retorna nada
    public void deleteById(@PathVariable Long id) throws BeerNotFoundException {
        this.beerServ.deleteById(id);
    }


}
