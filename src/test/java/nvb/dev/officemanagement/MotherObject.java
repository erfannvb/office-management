package nvb.dev.officemanagement;

import nvb.dev.officemanagement.model.dto.ClerkDto;
import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.model.entity.DocumentEntity;
import nvb.dev.officemanagement.model.entity.ManagerEntity;
import nvb.dev.officemanagement.model.entity.OfficeEntity;

import java.util.Set;

public class MotherObject {

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
                .office(anyValidOffice())
                .manager(anyValidManager())
                .documents(Set.of(anyValidDocument()))
                .build();
    }

    public static ClerkDto anyValidClerkDto() {
        return ClerkDto.builder()
                .firstName(ANY_STRING)
                .lastName(ANY_STRING)
                .department(ANY_STRING)
                .age(ANY_NUMBER)
                .office(anyValidOffice())
                .manager(anyValidManager())
                .documents(Set.of(anyValidDocument()))
                .build();
    }

    public static ClerkEntity anyValidUpdatedClerk() {
        return ClerkEntity.builder()
                .firstName(ANY_UPDATED_STRING)
                .lastName(ANY_UPDATED_STRING)
                .department(ANY_UPDATED_STRING)
                .age(ANY_UPDATED_NUMBER)
                .build();
    }

    public static DocumentEntity anyValidDocument() {
        return DocumentEntity.builder()
                .title(ANY_STRING)
                .description(ANY_STRING)
                .build();
    }

    public static DocumentEntity anyValidUpdatedDocument() {
        return DocumentEntity.builder()
                .title(ANY_UPDATED_STRING)
                .description(ANY_UPDATED_STRING)
                .build();
    }

    public static ManagerEntity anyValidManager() {
        return ManagerEntity.builder()
                .firstName(ANY_STRING)
                .lastName(ANY_STRING)
                .department(ANY_STRING)
                .age(ANY_NUMBER)
                .build();
    }

    public static ManagerEntity anyValidUpdatedManager() {
        return ManagerEntity.builder()
                .firstName(ANY_UPDATED_STRING)
                .lastName(ANY_UPDATED_STRING)
                .department(ANY_UPDATED_STRING)
                .age(ANY_UPDATED_NUMBER)
                .build();
    }

    public static OfficeEntity anyValidOffice() {
        return OfficeEntity.builder()
                .officeName(ANY_STRING)
                .officeCode(ANY_STRING)
                .officePhoneNumber(ANY_STRING)
                .build();
    }

    public static OfficeEntity anyValidUpdatedOffice() {
        return OfficeEntity.builder()
                .officeName(ANY_UPDATED_STRING)
                .officeCode(ANY_UPDATED_STRING)
                .officePhoneNumber(ANY_UPDATED_STRING)
                .build();
    }

}
