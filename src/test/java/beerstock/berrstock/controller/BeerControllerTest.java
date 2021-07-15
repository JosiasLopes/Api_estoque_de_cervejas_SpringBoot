package beerstock.berrstock.controller;

import beerstock.berrstock.builder.BeerDtoBulider;
import beerstock.berrstock.dto.BeerDto;
import beerstock.berrstock.exceptions.BeerNotFoundException;
import beerstock.berrstock.service.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.Optional;

import static beerstock.berrstock.utils.JsonConvertionUtils.asJsonString;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.core.Is.is;


@ExtendWith(MockitoExtension.class)
public class BeerControllerTest {

    private static final String BEER_API_URL_PATH = "/api/v1/beer";
    private static final long VALID_BEER_ID = 1L;
    private static final long INVALID_BEER_ID = 2L;
    private static final String BEER_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final String BEER_API_SUBPATH_DECREMENT_URL = "/decrement";

    @Mock
    private BeerService beerServ;

    @InjectMocks        //injeta o mock da classe service dentro da classe de controle
    BeerController beerctrl;

    private MockMvc  mockmvc;

    //o metodo setup usa uma classe controle e pode ser onfiguravel
    @BeforeEach
    void setUp() {
          mockmvc = MockMvcBuilders.standaloneSetup(beerctrl).setCustomArgumentResolvers(
                new PageableHandlerMethodArgumentResolver()
        ).setViewResolvers((s,locale)->new MappingJackson2JsonView()).build();
    }

    @Test
    void WhenPostIsCalledAndBeerCreated() throws Exception {
        //dado
        BeerDto beerdto = BeerDtoBulider.builder().build().toBeerDto();

        //quando
        lenient().when(beerServ.createBeerTest(beerdto)).thenReturn(beerdto);
        mockmvc.perform(post(BEER_API_URL_PATH).contentType(MediaType.APPLICATION_JSON)
                .contentType(asJsonString(beerdto))).andExpect(status().isCreated())

                .andExpect(jsonPath("$.brand", is(beerdto.getBrand())))
                .andExpect(jsonPath("$.type", is(beerdto.getType().toString())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        BeerDto beerDTO = BeerDtoBulider.builder().build().toBeerDto();
        beerDTO.setBrand(null);

        // then
        mockmvc.perform(post(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(beerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        BeerDto beerDTO = BeerDtoBulider.builder().build().toBeerDto();

        //when
        lenient().when(beerServ.findByName(beerDTO.getBrand())).thenReturn(beerDTO);

        // then
        mockmvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH + "/brand/" + beerDTO.getBrand())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
                .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        // given
        BeerDto beerDTO = BeerDtoBulider.builder().build().toBeerDto();

        //when
        lenient().when(beerServ.findByName(beerDTO.getBrand())).thenThrow(BeerNotFoundException.class);

        // then
        mockmvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH + "/brand/" + beerDTO.getBrand())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithBeersIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        BeerDto beerDTO = BeerDtoBulider.builder().build().toBeerDto();

        //when
        lenient().when(beerServ.retrieveAll()).thenReturn(Collections.singletonList(beerDTO));

        // then
        mockmvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].brand", is(beerDTO.getBrand())))
                .andExpect(jsonPath("$[0].type", is(beerDTO.getType().toString())));
    }

    @Test
    void whenGETListWithoutBeersIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        BeerDto beerDTO = BeerDtoBulider.builder().build().toBeerDto();

        //when
        lenient().when(beerServ.retrieveAll()).thenReturn(Collections.singletonList(beerDTO));

        // then
        mockmvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
