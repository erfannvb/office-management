package nvb.dev.officemanagement;

import nvb.dev.officemanagement.model.Address;
import nvb.dev.officemanagement.model.dto.*;
import nvb.dev.officemanagement.model.entity.*;

import java.util.Set;

public class MotherObject {

    public static final Long ANY_ID = 1L;

    public static final String ANY_STRING = "dummy";
    public static final Integer ANY_NUMBER = 20;

    public static final String ANY_UPDATED_STRING = "updatedDummy";
    public static final Integer ANY_UPDATED_NUMBER = 25;

    public static ClerkEntity anyValidClerk() {
        return ClerkEntity.builder()
                .id(ANY_ID)
                .firstName(ANY_STRING)
                .lastName(ANY_STRING)
                .department(ANY_STRING)
                .age(ANY_NUMBER)
                .manager(new ManagerEntity(ANY_ID, ANY_STRING, ANY_STRING, ANY_STRING, ANY_NUMBER,
                        new OfficeEntity(ANY_ID, ANY_STRING, ANY_STRING, ANY_STRING, anyValidAddress())))
                .office(new OfficeEntity(ANY_ID, ANY_STRING, ANY_STRING, ANY_STRING, anyValidAddress()))
                .build();
    }

    public static ClerkDto anyValidClerkDto() {
        return ClerkDto.builder()
                .id(ANY_ID)
                .firstName(ANY_STRING)
                .lastName(ANY_STRING)
                .department(ANY_STRING)
                .age(ANY_NUMBER)
                .manager(new ManagerEntity(ANY_ID, ANY_UPDATED_STRING, ANY_UPDATED_STRING,
                        ANY_UPDATED_STRING, ANY_UPDATED_NUMBER,
                        new OfficeEntity(ANY_ID, ANY_STRING, ANY_STRING, ANY_STRING, anyValidAddress())))
                .office(new OfficeEntity(ANY_ID, ANY_STRING, ANY_STRING, ANY_STRING, anyValidAddress()))
                .build();
    }

    public static ClerkEntity anyValidUpdatedClerk() {
        return ClerkEntity.builder()
                .id(ANY_ID)
                .firstName(ANY_UPDATED_STRING)
                .lastName(ANY_UPDATED_STRING)
                .department(ANY_UPDATED_STRING)
                .age(ANY_UPDATED_NUMBER)
                .manager(new ManagerEntity(ANY_ID, ANY_UPDATED_STRING, ANY_UPDATED_STRING,
                        ANY_UPDATED_STRING, ANY_UPDATED_NUMBER,
                        new OfficeEntity(ANY_ID, ANY_STRING, ANY_STRING, ANY_STRING, anyValidAddress())))
                .office(new OfficeEntity(ANY_ID, ANY_UPDATED_STRING,
                        ANY_UPDATED_STRING, ANY_UPDATED_STRING, anyValidAddress()))
                .build();
    }

    public static DocumentEntity anyValidDocument() {
        return DocumentEntity.builder()
                .id(ANY_ID)
                .title(ANY_STRING)
                .description(ANY_STRING)
                .office(new OfficeEntity())
                .build();
    }

    public static DocumentDto anyValidDocumentDto() {
        return DocumentDto.builder()
                .id(ANY_ID)
                .title(ANY_STRING)
                .description(ANY_STRING)
                .office(new OfficeEntity())
                .build();
    }

    public static DocumentEntity anyValidUpdatedDocument() {
        return DocumentEntity.builder()
                .id(ANY_ID)
                .title(ANY_UPDATED_STRING)
                .description(ANY_UPDATED_STRING)
                .office(new OfficeEntity())
                .build();
    }

    public static ManagerEntity anyValidManager() {
        return ManagerEntity.builder()
                .id(ANY_ID)
                .firstName(ANY_STRING)
                .lastName(ANY_STRING)
                .department(ANY_STRING)
                .age(ANY_NUMBER)
                .office(new OfficeEntity(ANY_ID, ANY_STRING, ANY_STRING, ANY_STRING, anyValidAddress()))
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
                .build();
    }

    public static ManagerEntity anyValidUpdatedManager() {
        return ManagerEntity.builder()
                .id(ANY_ID)
                .firstName(ANY_UPDATED_STRING)
                .lastName(ANY_UPDATED_STRING)
                .department(ANY_UPDATED_STRING)
                .age(ANY_UPDATED_NUMBER)
                .office(new OfficeEntity(ANY_ID, ANY_UPDATED_STRING,
                        ANY_UPDATED_STRING, ANY_UPDATED_STRING, anyValidAddress()))
                .build();
    }

    public static OfficeEntity anyValidOffice() {
        return OfficeEntity.builder()
                .id(ANY_ID)
                .officeName(ANY_STRING)
                .officeCode(ANY_STRING)
                .officePhoneNumber(ANY_STRING)
                .address(anyValidAddress())
                .build();
    }

    public static OfficeDto anyValidOfficeDto() {
        return OfficeDto.builder()
                .id(ANY_ID)
                .officeName(ANY_STRING)
                .officeCode(ANY_STRING)
                .officePhoneNumber(ANY_STRING)
                .address(anyValidAddress())
                .build();
    }

    public static OfficeEntity anyValidUpdatedOffice() {
        return OfficeEntity.builder()
                .id(ANY_ID)
                .officeName(ANY_UPDATED_STRING)
                .officeCode(ANY_UPDATED_STRING)
                .officePhoneNumber(ANY_UPDATED_STRING)
                .address(anyValidAddress())
                .build();
    }

    public static Address anyValidAddress() {
        return Address.builder()
                .city(ANY_STRING)
                .country(ANY_STRING)
                .build();
    }

    public static UserEntity anyValidUser() {
        return UserEntity.builder()
                .username(ANY_STRING)
                .password(ANY_STRING)
                .role(ANY_STRING)
                .build();
    }

    public static UserDto anyValidUserDto() {
        return UserDto.builder()
                .username(ANY_STRING)
                .password(ANY_STRING)
                .role(ANY_STRING)
                .build();
    }

    public static UserEntity anyValidUpdatedUser() {
        return UserEntity.builder()
                .username(ANY_UPDATED_STRING)
                .password(ANY_UPDATED_STRING)
                .role(ANY_UPDATED_STRING)
                .build();
    }

}
