package nvb.dev.officemanagement.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.mapper.ManagerMapper;
import nvb.dev.officemanagement.model.dto.ManagerDto;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/managerManagement")
@AllArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final ManagerMapper managerMapper;

    @PostMapping(path = "/offices/{officeId}/managers")
    public ResponseEntity<ManagerDto> createManager(@PathVariable long officeId,
                                                    @RequestBody @Valid ManagerDto managerDto) {
        ManagerEntity managerEntity = managerMapper.toManagerEntity(managerDto);
        ManagerEntity savedManager = managerService.createManager(officeId, managerEntity);
        return new ResponseEntity<>(managerMapper.toManagerDto(savedManager), HttpStatus.CREATED);
    }

    @GetMapping(path = "/managers/{managerId}")
    public ResponseEntity<ManagerDto> getManagerById(@PathVariable long managerId) {
        Optional<ManagerEntity> foundManager = managerService.getManagerById(managerId);
        return foundManager.map(managerEntity -> {
            ManagerDto managerDto = managerMapper.toManagerDto(managerEntity);
            return new ResponseEntity<>(managerDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/offices/{officeId}/managers")
    public ResponseEntity<List<ManagerDto>> getAllManagersByOfficeId(@PathVariable long officeId) {
        List<ManagerEntity> managerEntityList = managerService.getAllManagersByOfficeId(officeId);
        List<ManagerDto> managerDtoList = managerEntityList.stream().map(managerMapper::toManagerDto).toList();
        return new ResponseEntity<>(managerDtoList, HttpStatus.OK);
    }

    @PutMapping(path = "/managers/{managerId}")
    public ResponseEntity<ManagerDto> updateManager(@PathVariable long managerId,
                                                    @RequestBody @Valid ManagerDto managerDto) {

        if (!managerService.isExists(managerId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ManagerEntity managerEntity = managerMapper.toManagerEntity(managerDto);
        ManagerEntity updatedManager = managerService.updateManager(managerId, managerEntity);
        return new ResponseEntity<>(managerMapper.toManagerDto(updatedManager), HttpStatus.OK);
    }

    @PatchMapping(path = "/managers/{managerId}")
    public ResponseEntity<ManagerDto> partialUpdate(@PathVariable long managerId,
                                                    @RequestBody @Valid ManagerDto managerDto) {

        if (!managerService.isExists(managerId)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ManagerEntity managerEntity = managerMapper.toManagerEntity(managerDto);
        ManagerEntity partialUpdate = managerService.partialUpdate(managerId, managerEntity);
        return new ResponseEntity<>(managerMapper.toManagerDto(partialUpdate), HttpStatus.OK);
    }

    @DeleteMapping(path = "/managers/{managerId}")
    public ResponseEntity<HttpStatus> deleteManager(@PathVariable long managerId) {
        managerService.deleteManager(managerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/offices/{officeId}/managers")
    public ResponseEntity<HttpStatus> deleteAllManagersOfOffice(@PathVariable long officeId) {
        managerService.deleteAllManagersOfOffice(officeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
