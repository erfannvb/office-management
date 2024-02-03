package nvb.dev.officemanagement.controller;

import jakarta.validation.Valid;
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

    @PostMapping(path = "/offices/{officeId}/managers/{managerId}/clerks")
    public ResponseEntity<ClerkDto> createClerk(@PathVariable long officeId,
                                                @PathVariable long managerId,
                                                @RequestBody @Valid ClerkDto clerkDto) {
        ClerkEntity clerkEntity = clerkMapper.toClerkEntity(clerkDto);
        ClerkEntity savedClerk = clerkService.createClerk(officeId, managerId, clerkEntity);
        return new ResponseEntity<>(clerkMapper.toClerkDto(savedClerk), HttpStatus.CREATED);
    }

    @GetMapping(path = "/offices/{officeId}/managers/{managerId}/clerks")
    public ResponseEntity<List<ClerkDto>> getAllClerksByOfficeIdAndManagerId(@PathVariable long officeId,
                                                                             @PathVariable long managerId) {
        List<ClerkEntity> clerkEntityList = clerkService.getAllClerksByOfficeIdAndManagerId(officeId, managerId);
        List<ClerkDto> clerkDtoList = clerkEntityList.stream().map(clerkMapper::toClerkDto).toList();
        return new ResponseEntity<>(clerkDtoList, HttpStatus.OK);
    }

    @GetMapping(path = "/clerks/{clerkId}")
    public ResponseEntity<ClerkDto> getClerkById(@PathVariable long clerkId) {
        Optional<ClerkEntity> foundClerk = clerkService.getClerkById(clerkId);
        return foundClerk.map(clerkEntity -> {
            ClerkDto clerkDto = clerkMapper.toClerkDto(clerkEntity);
            return new ResponseEntity<>(clerkDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/clerks/{clerkId}")
    public ResponseEntity<ClerkDto> updateClerk(@PathVariable long clerkId,
                                                @RequestBody @Valid ClerkDto clerkDto) {
        if (!clerkService.isExists(clerkId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ClerkEntity clerkEntity = clerkMapper.toClerkEntity(clerkDto);
        ClerkEntity updatedClerk = clerkService.updateClerk(clerkId, clerkEntity);
        return new ResponseEntity<>(clerkMapper.toClerkDto(updatedClerk), HttpStatus.OK);
    }

    @PatchMapping(path = "/clerks/{clerkId}")
    public ResponseEntity<ClerkDto> partialUpdate(@PathVariable long clerkId,
                                                  @RequestBody @Valid ClerkDto clerkDto) {
        if (!clerkService.isExists(clerkId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ClerkEntity clerkEntity = clerkMapper.toClerkEntity(clerkDto);
        ClerkEntity partialUpdate = clerkService.partialUpdate(clerkId, clerkEntity);
        return new ResponseEntity<>(clerkMapper.toClerkDto(partialUpdate), HttpStatus.OK);
    }

    @DeleteMapping(path = "/clerks/{clerkId}")
    public ResponseEntity<HttpStatus> deleteClerk(@PathVariable long clerkId) {
        clerkService.deleteClerk(clerkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/managers/{managerId}/clerks")
    public ResponseEntity<HttpStatus> deleteAllClerksOfManager(@PathVariable long managerId) {
        clerkService.deleteAllClerksOfManager(managerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
