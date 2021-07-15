package beerstock.berrstock.exceptions;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class BeerNotFoundException extends Exception  {

    public BeerNotFoundException(String name){
        super("Beer not Found: "+name);
    }
    public BeerNotFoundException(Long id){
        super("Beer not Found: "+id);
    }

}
