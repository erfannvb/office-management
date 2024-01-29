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
    public OfficeEntity updateOffice(long id, OfficeEntity office) {
        Optional<OfficeEntity> optionalOffice = officeRepository.findById(id);
        if (optionalOffice.isPresent()) {

            OfficeEntity currentOffice = optionalOffice.get();

            currentOffice.setOfficeName(office.getOfficeName());
            currentOffice.setOfficeCode(office.getOfficeCode());
            currentOffice.setOfficePhoneNumber(office.getOfficePhoneNumber());

            return officeRepository.save(currentOffice);

        } else {
            throw new EntityNotFoundException(OfficeEntity.class, id);
        }
    }

    @Override
    public List<OfficeEntity> findAll() {
        List<OfficeEntity> officeEntityList = officeRepository.findAll();
        if (officeEntityList.isEmpty()) throw new NoDataFoundException();
        return officeEntityList;
    }

    @Override
    public Optional<OfficeEntity> findOfficeById(long id) {
        return Optional.ofNullable(officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(OfficeEntity.class, id)));
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

        }).orElseThrow(() -> new EntityNotFoundException(OfficeEntity.class, id));
    }

    @Override
    public void deleteById(long id) {
        Optional<OfficeEntity> optionalOffice = officeRepository.findById(id);
        if (optionalOffice.isPresent()) {
            officeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(OfficeEntity.class, id);
        }
    }
}
