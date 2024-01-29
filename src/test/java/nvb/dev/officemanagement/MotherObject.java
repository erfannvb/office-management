package nvb.dev.officemanagement;

import nvb.dev.officemanagement.model.Address;
import nvb.dev.officemanagement.model.dto.ClerkDto;
import nvb.dev.officemanagement.model.dto.DocumentDto;
import nvb.dev.officemanagement.model.dto.ManagerDto;
import nvb.dev.officemanagement.model.dto.OfficeDto;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.model.entity.OfficeEntity;

import java.util.Set;

public class MotherObject {

    public static final Long ANY_ID = 1L;

    public static final String ANY_STRING = "dummy";
    public static final Integer ANY_NUMBER = 20;

    public static final String ANY_UPDATED_STRING = "updatedDummy";
    public static final Integer ANY_UPDATED_NUMBER = 25;

    public static ClerkEntity anyValidClerk() {
        return ClerkEntity.builder()
                .firstName(ANY_STRING)
                .lastName(ANY_STRING)
                .department(ANY_STRING)
                .age(ANY_NUMBER)
                .office(new OfficeEntity())
                .manager(new ManagerEntity())
                .documents(Set.of(new DocumentEntity()))
                .build();
    }

    public static ClerkDto anyValidClerkDto() {
        return ClerkDto.builder()
                .id(ANY_ID)
                .firstName(ANY_STRING)
                .lastName(ANY_STRING)
                .department(ANY_STRING)
                .age(ANY_NUMBER)
                .office(new OfficeEntity())
                .manager(new ManagerEntity())
                .documents(Set.of(new DocumentEntity()))
                .build();
    }

    public static ClerkEntity anyValidUpdatedClerk() {
        return ClerkEntity.builder()
                .id(ANY_ID)
                .firstName(ANY_UPDATED_STRING)
                .lastName(ANY_UPDATED_STRING)
                .department(ANY_UPDATED_STRING)
                .age(ANY_UPDATED_NUMBER)
                .build();
    }

    public static DocumentEntity anyValidDocument() {
        return DocumentEntity.builder()
                .id(ANY_ID)
                .title(ANY_STRING)
                .description(ANY_STRING)
                .managers(Set.of(new ManagerEntity()))
                .clerks(Set.of(new ClerkEntity()))
                .office(new OfficeEntity())
                .build();
    }

    public static DocumentDto anyValidDocumentDto() {
        return DocumentDto.builder()
                .id(ANY_ID)
                .title(ANY_STRING)
                .description(ANY_STRING)
                .managers(Set.of(new ManagerEntity()))
                .clerks(Set.of(new ClerkEntity()))
                .office(new OfficeEntity())
                .build();
    }

    public static DocumentEntity anyValidUpdatedDocument() {
        return DocumentEntity.builder()
                .id(ANY_ID)
                .title(ANY_UPDATED_STRING)
                .description(ANY_UPDATED_STRING)
                .build();
    }

    public static ManagerEntity anyValidManager() {
        return ManagerEntity.builder()
                .id(ANY_ID)
                .firstName(ANY_STRING)
                .lastName(ANY_STRING)
                .department(ANY_STRING)
                .age(ANY_NUMBER)
                .office(new OfficeEntity())
                .clerks(Set.of(new ClerkEntity()))
                .documents(Set.of(new DocumentEntity()))
                .build();
    }

    public static ManagerDto anyValidManagerDto() {
        return ManagerDto.builder()
                .id(ANY_ID)
                .firstName(ANY_STRING)
                .lastName(ANY_STRING)
                .department(ANY_STRING)
                .age(ANY_NUMBER)
                .office(new OfficeEntity())
                .clerks(Set.of(new ClerkEntity()))
                .documents(Set.of(new DocumentEntity()))
                .build();
    }

    public static ManagerEntity anyValidUpdatedManager() {
        return ManagerEntity.builder()
                .id(ANY_ID)
                .firstName(ANY_UPDATED_STRING)
                .lastName(ANY_UPDATED_STRING)
                .department(ANY_UPDATED_STRING)
                .age(ANY_UPDATED_NUMBER)
                .build();
    }

    public static OfficeEntity anyValidOffice() {
        return OfficeEntity.builder()
                .id(ANY_ID)
                .officeName(ANY_STRING)
                .officeCode(ANY_STRING)
                .officePhoneNumber(ANY_STRING)
                .address(anyValidAddress())
                .managers(Set.of(new ManagerEntity()))
                .clerks(Set.of(new ClerkEntity()))
                .documents(Set.of(new DocumentEntity()))
                .build();
    }

    public static OfficeDto anyValidOfficeDto() {
        return OfficeDto.builder()
                .id(ANY_ID)
                .officeName(ANY_STRING)
                .officeCode(ANY_STRING)
                .officePhoneNumber(ANY_STRING)
                .address(anyValidAddress())
                .managers(Set.of(new ManagerEntity()))
                .clerks(Set.of(new ClerkEntity()))
                .documents(Set.of(new DocumentEntity()))
                .build();
    }

    public static OfficeEntity anyValidUpdatedOffice() {
        return OfficeEntity.builder()
                .id(ANY_ID)
                .officeName(ANY_UPDATED_STRING)
                .officeCode(ANY_UPDATED_STRING)
                .officePhoneNumber(ANY_UPDATED_STRING)
                .build();
    }

    public static Address anyValidAddress() {
        return Address.builder()
                .city(ANY_STRING)
                .country(ANY_STRING)
                .build();
    }

}
