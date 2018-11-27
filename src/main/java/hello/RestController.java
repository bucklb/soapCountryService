package hello;

import io.spring.guides.gs_producing_web_service.Country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Basic rest controller stuff.
@RequestMapping("")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    CountryRepository countryRepository;

    // in case no link is requested, give them something to look at.  A clue as to what will respond would be good
    @RequestMapping(value="",method = RequestMethod.GET)
    public String doHome() {
        String s = "restServer";
        return s;
    }

    @RequestMapping(value="help",method = RequestMethod.GET)
    public String doHelp() {
        String s = "restServer - intended as basic framework to do REST stuff around";
        return s;
    }

    @RequestMapping(value="rest",method = RequestMethod.GET)
    public ResponseEntity<Country> doRest(@RequestParam(value="country", required = false) String countryName) {

        Country country=countryRepository.findCountry(countryName);
        if (country==null) {
            // not good
            return new ResponseEntity<>(country, HttpStatus.NOT_FOUND);
        }
        else
        {   // presumably a success
            return new ResponseEntity<>(country, HttpStatus.OK);
        }
    }

}
