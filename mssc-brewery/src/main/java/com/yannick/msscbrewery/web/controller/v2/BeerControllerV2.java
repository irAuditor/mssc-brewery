package com.yannick.msscbrewery.web.controller.v2;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yannick.msscbrewery.services.BeerService;
import com.yannick.msscbrewery.web.model.BeerDto;

//requestmapping creates the basis uri, everything else will be anotated

@RequestMapping("/api/v2/beer")
@RestController
public class BeerControllerV2 {
	
	@Autowired
	private BeerService beerService;
	
	public BeerService getBeerService() {
		return beerService;
	}

	@GetMapping({"/{beerId}"})
	public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId) {
		
		return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity handlePost(@Valid @RequestBody BeerDto beerDto) {
		BeerDto beerSaved = beerService.saveNewBeer(beerDto);
		HttpHeaders headers = new HttpHeaders();
		// to do, make full URL in the location
		headers.add("Location", "/api/v1/beer/"+beerSaved.getId().toString());
		
		return new ResponseEntity(headers, HttpStatus.CREATED);
	}
	
	@PutMapping({"/{beerId}"})
	public ResponseEntity handlePut(@PathVariable("beerId") UUID beerId,@RequestBody  BeerDto beerDto) {
		beerService.updateBeer(beerId, beerDto);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
		
	}
	
	@DeleteMapping({"/{beerId}"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void handleDelete(@PathVariable("beerId") UUID beerId) {
		beerService.deleteBeer(beerId);
	}

}
