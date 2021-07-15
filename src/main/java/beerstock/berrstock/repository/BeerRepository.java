package beerstock.berrstock.repository;

import beerstock.berrstock.entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeerRepository extends JpaRepository<Beer,Long> {

    Optional<Beer> findByBrand(String name);
}
