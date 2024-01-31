package nvb.dev.officemanagement.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
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
    public OfficeEntity createOffice(OfficeEntity office) {
        return officeRepository.save(office);
    }

    @Override
    public List<OfficeEntity> getAllOffices() {
        List<OfficeEntity> officeEntityList = officeRepository.findAll();
        if (!officeEntityList.isEmpty()) {
            return officeEntityList;
        } else {
            throw new NoDataFoundException();
        }
    }

    @Override
    public Optional<OfficeEntity> getOfficeById(long officeId) {
        return Optional.ofNullable(officeRepository.findById(officeId)
                .orElseThrow(() -> new EntityNotFoundException(OfficeEntity.class, officeId)));
    }

    @Override
    public Optional<OfficeEntity> getOfficeByOfficeName(String officeName) {
        return Optional.ofNullable(officeRepository.findByOfficeName(officeName)
                .orElseThrow(NoDataFoundException::new));
    }

    @Override
    public OfficeEntity updateOffice(long officeId, OfficeEntity office) {
        Optional<OfficeEntity> optionalOffice = officeRepository.findById(officeId);
        if (optionalOffice.isPresent()) {

            OfficeEntity currentOffice = optionalOffice.get();

            currentOffice.setOfficeName(office.getOfficeName());
            currentOffice.setOfficeCode(office.getOfficeCode());
            currentOffice.setOfficePhoneNumber(office.getOfficePhoneNumber());
            currentOffice.setAddress(office.getAddress());

            return officeRepository.save(currentOffice);

        } else {
            throw new EntityNotFoundException(OfficeEntity.class, officeId);
        }
    }

    @Override
    public OfficeEntity partialUpdate(long officeId, OfficeEntity office) {
        office.setId(officeId);

        return officeRepository.findById(officeId).map(existingOffice -> {

            Optional.ofNullable(office.getOfficeName()).ifPresent(existingOffice::setOfficeName);
            Optional.ofNullable(office.getOfficeCode()).ifPresent(existingOffice::setOfficeCode);
            Optional.ofNullable(office.getOfficePhoneNumber()).ifPresent(existingOffice::setOfficePhoneNumber);
            Optional.ofNullable(office.getAddress()).ifPresent(existingOffice::setAddress);

            return officeRepository.save(existingOffice);

        }).orElseThrow(() -> new EntityNotFoundException(OfficeEntity.class, officeId));
    }

    @Override
    public void deleteOffice(long officeId) {
        Optional<OfficeEntity> optionalOffice = officeRepository.findById(officeId);
        if (optionalOffice.isPresent()) {
            officeRepository.deleteById(officeId);
        } else {
            throw new EntityNotFoundException(OfficeEntity.class, officeId);
        }
    }

    @Override
    public void deleteAllOffices() {
        List<OfficeEntity> officeEntityList = officeRepository.findAll();
        if (!officeEntityList.isEmpty()) {
            officeRepository.deleteAll();
        } else {
            throw new NoDataFoundException();
        }
    }

    @Override
    public boolean isExists(long officeId) {
        return officeRepository.existsById(officeId);
    }

}
