package nvb.dev.officemanagement.config;

import nvb.dev.officemanagement.model.entity.UserEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
public class UserProcessor implements ItemProcessor<UserEntity, UserEntity> {
    @Override
    public UserEntity process(@NonNull UserEntity userEntity) throws Exception {
        return userEntity;
    }
}
