package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.model.entity.OfficeEntity;
import nvb.dev.officemanagement.repository.OfficeRepository;
import nvb.dev.officemanagement.service.OfficeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository officeRepository;

    @Override
    public OfficeEntity createUpdateOffice(long id, OfficeEntity office) {
        office.setId(id);
        return officeRepository.save(office);
    }

    @Override
    public List<OfficeEntity> findAll() {
        return officeRepository.findAll();
    }

    @Override
    public Optional<OfficeEntity> findOfficeById(long id) {
        return officeRepository.findById(id);
    }

    @Override
    public boolean isExists(long id) {
        return officeRepository.existsById(id);
    }

    @Override
    public OfficeEntity partialUpdate(long id, OfficeEntity office) {
        office.setId(id);

        return officeRepository.findById(id).map(existingOffice -> {

            Optional.ofNullable(office.getOfficeName()).ifPresent(existingOffice::setOfficeName);
            Optional.ofNullable(office.getOfficeCode()).ifPresent(existingOffice::setOfficeCode);
            Optional.ofNullable(office.getOfficePhoneNumber()).ifPresent(existingOffice::setOfficePhoneNumber);
            Optional.ofNullable(office.getAddress()).ifPresent(existingOffice::setAddress);

            return officeRepository.save(existingOffice);

        }).orElseThrow();
    }

    @Override
    public void deleteById(long id) {
        officeRepository.deleteById(id);
    }
}
