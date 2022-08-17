package com.example.releasetracker.controller;

import com.example.releasetracker.dto.ReleaseDto;
import com.example.releasetracker.entity.ReleaseEntity;
import com.example.releasetracker.service.impl.ReleaseServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/releases")
public class ReleaseController {

    private ReleaseServiceImpl releaseService;
    private ModelMapper modelMapper;

    public ReleaseController(ReleaseServiceImpl releaseService, ModelMapper modelMapper) {
        this.releaseService = releaseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getReleases(@RequestParam(required = false) String status,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false, name = "release_date") String releaseDate) throws ParseException {
        Date parsedDate = null;
        if(!StringUtils.isEmpty(releaseDate)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            parsedDate = dateFormat.parse(releaseDate);
        }
        List<ReleaseDto> releasesDto = this.releaseService.findAll(name, status, parsedDate).stream().map(release -> modelMapper.map(release, ReleaseDto.class))
                .collect(Collectors.toList());
        if(releasesDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(releasesDto, HttpStatus.OK);
    }

    @GetMapping(value = "/list", produces = "application/json")
    public ResponseEntity<?> getReleasesList(){
         List<ReleaseDto> releasesDto = releaseService.findAll().stream().map(release -> modelMapper.map(release, ReleaseDto.class))
                .collect(Collectors.toList());
         if(releasesDto == null){
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
         return new ResponseEntity<>(releasesDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getRelease(@PathVariable Long id){
        ReleaseEntity releaseEntity = this.releaseService.findById(id);
        if(releaseEntity == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ReleaseDto releaseDto = modelMapper.map(releaseEntity, ReleaseDto.class);
        return new ResponseEntity<>(releaseDto, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> addRelease(@RequestBody ReleaseDto releaseDto){

        ReleaseEntity releaseRequest = modelMapper.map(releaseDto, ReleaseEntity.class);
        if(releaseRequest.getId() != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ReleaseEntity releaseEntity = this.releaseService.save(releaseRequest);
        ReleaseDto releaseResponse = modelMapper.map(releaseEntity, ReleaseDto.class);

        return new ResponseEntity<>(releaseResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> updateRelease(@PathVariable Long id,
                                           @RequestBody ReleaseDto releaseDto){

        boolean bodyIdMatchesPathId = releaseDto.getId() == null || id.equals(releaseDto.getId());
        if(!bodyIdMatchesPathId){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ReleaseEntity releaseRequest = modelMapper.map(releaseDto, ReleaseEntity.class);
        ReleaseEntity releaseEntity = this.releaseService.save(releaseRequest);
        this.releaseService.save(releaseEntity);
        ReleaseDto releaseResponse = modelMapper.map(releaseEntity, ReleaseDto.class);

        return new ResponseEntity<>(releaseResponse, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<?> deleteRelease(@PathVariable Long id){

        this.releaseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
