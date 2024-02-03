package nvb.dev.officemanagement.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.mapper.OfficeMapper;
import nvb.dev.officemanagement.model.dto.OfficeDto;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import nvb.dev.officemanagement.service.OfficeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/officeManagement")
@AllArgsConstructor
public class OfficeController {

    private final OfficeService officeService;
    private final OfficeMapper officeMapper;

    @PostMapping(path = "/offices")
    public ResponseEntity<OfficeDto> createOffice(@RequestBody @Valid OfficeDto officeDto) {
        OfficeEntity officeEntity = officeMapper.toOfficeEntity(officeDto);
        OfficeEntity savedOffice = officeService.createOffice(officeEntity);
        return new ResponseEntity<>(officeMapper.toOfficeDto(savedOffice), HttpStatus.CREATED);
    }

    @GetMapping(path = "/offices")
    public ResponseEntity<List<OfficeDto>> getAllOffices() {
        List<OfficeEntity> allOffices = officeService.getAllOffices();
        List<OfficeDto> officeDtoList = allOffices.stream().map(officeMapper::toOfficeDto).toList();
        return new ResponseEntity<>(officeDtoList, HttpStatus.OK);
    }

    @GetMapping(path = "/offices/{officeId}")
    public ResponseEntity<OfficeDto> getOfficeById(@PathVariable long officeId) {
        Optional<OfficeEntity> foundOffice = officeService.getOfficeById(officeId);
        return foundOffice.map(officeEntity -> {
            OfficeDto officeDto = officeMapper.toOfficeDto(officeEntity);
            return new ResponseEntity<>(officeDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/officesByName/{officeName}")
    public ResponseEntity<OfficeDto> getOfficeByOfficeName(@PathVariable String officeName) {
        Optional<OfficeEntity> foundOffice = officeService.getOfficeByOfficeName(officeName);
        return foundOffice.map(officeEntity -> {
            OfficeDto officeDto = officeMapper.toOfficeDto(officeEntity);
            return new ResponseEntity<>(officeDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/offices/{officeId}")
    public ResponseEntity<OfficeDto> updateOffice(@PathVariable long officeId,
                                                  @RequestBody @Valid OfficeDto officeDto) {

        if (!officeService.isExists(officeId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OfficeEntity officeEntity = officeMapper.toOfficeEntity(officeDto);
        OfficeEntity updatedOffice = officeService.updateOffice(officeId, officeEntity);

        return new ResponseEntity<>(officeMapper.toOfficeDto(updatedOffice), HttpStatus.OK);
    }

    @PatchMapping(path = "/offices/{officeId}")
    public ResponseEntity<OfficeDto> partialUpdate(@PathVariable long officeId,
                                                   @RequestBody @Valid OfficeDto officeDto) {

        if (!officeService.isExists(officeId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        OfficeEntity officeEntity = officeMapper.toOfficeEntity(officeDto);
        OfficeEntity partialUpdate = officeService.partialUpdate(officeId, officeEntity);

        return new ResponseEntity<>(officeMapper.toOfficeDto(partialUpdate), HttpStatus.OK);
    }

    @DeleteMapping(path = "/offices/{officeId}")
    public ResponseEntity<HttpStatus> deleteOffice(@PathVariable long officeId) {
        officeService.deleteOffice(officeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/offices")
    public ResponseEntity<HttpStatus> deleteAllOffices() {
        officeService.deleteAllOffices();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
