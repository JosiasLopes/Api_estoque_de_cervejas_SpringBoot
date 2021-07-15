package beerstock.berrstock.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class AlreadyExistException extends Exception{






        public AlreadyExistException(String name){
            super("Beer Already Exist: "+name);
        }
        public AlreadyExistException(Long id){
            super("Beer Already Exist: "+id);
        }



}
