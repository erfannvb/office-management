package nvb.dev.officemanagement;

import nvb.dev.officemanagement.model.entity.ClerkEntity;
import nvb.dev.officemanagement.model.entity.DocumentEntity;

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


}
