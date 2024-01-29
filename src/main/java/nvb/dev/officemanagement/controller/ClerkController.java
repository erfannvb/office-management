package nvb.dev.officemanagement.controller;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.mapper.ClerkMapper;
import nvb.dev.officemanagement.model.dto.ClerkDto;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.service.ClerkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
@AllArgsConstructor
public class ClerkController {

    private final ClerkService clerkService;
    private final ClerkMapper clerkMapper;

    @PostMapping(path = "/clerk")
    public ResponseEntity<ClerkDto> createClerk(@RequestBody ClerkDto clerkDto) {
        ClerkEntity clerkEntity = clerkMapper.toClerkEntity(clerkDto);
        ClerkEntity savedClerk = clerkService.createClerk(clerkEntity);
        return new ResponseEntity<>(clerkMapper.toClerkDto(savedClerk), HttpStatus.CREATED);
    }

    @PutMapping(path = "/clerk/{id}")
    public ResponseEntity<ClerkDto> updateClerk(@PathVariable long id, @RequestBody ClerkDto clerkDto) {
        ClerkEntity clerkEntity = clerkMapper.toClerkEntity(clerkDto);
        ClerkEntity savedClerk = clerkService.updateClerk(id, clerkEntity);
        return new ResponseEntity<>(clerkMapper.toClerkDto(savedClerk), HttpStatus.OK);
    }

    @GetMapping(path = "/clerk/all")
    public ResponseEntity<List<ClerkDto>> findAllClerks() {
        List<ClerkEntity> clerkEntityList = clerkService.findAll();
        List<ClerkDto> clerkDtoList = clerkEntityList.stream().map(clerkMapper::toClerkDto).toList();
        return new ResponseEntity<>(clerkDtoList, HttpStatus.OK);
    }

    @GetMapping(path = "/clerk/{id}")
    public ResponseEntity<ClerkDto> findClerkById(@PathVariable long id) {
        Optional<ClerkEntity> foundClerk = clerkService.findClerkById(id);
        return foundClerk.map(clerkEntity -> {
            ClerkDto clerkDto = clerkMapper.toClerkDto(clerkEntity);
            return new ResponseEntity<>(clerkDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/clerk/{id}")
    public ResponseEntity<ClerkDto> partialUpdate(@PathVariable long id, @RequestBody ClerkDto clerkDto) {
        if (!clerkService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ClerkEntity clerkEntity = clerkMapper.toClerkEntity(clerkDto);
        ClerkEntity savedClerk = clerkService.createClerk(clerkEntity);

        return new ResponseEntity<>(clerkMapper.toClerkDto(savedClerk), HttpStatus.OK);
    }

    @DeleteMapping(path = "/clerk/{id}")
    public ResponseEntity<HttpStatus> deleteClerkById(@PathVariable long id) {
        clerkService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
