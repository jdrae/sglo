package io.sglo;

import io.sglo.account.common.entity.User;
import io.sglo.account.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Profile("local")
public class InitDB {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationContextEvent.class)
    @Transactional
    public void initDB(){
        initTestUser();
    }

    private void initTestUser() {
        userRepository.save(new User("user", passwordEncoder.encode("user123!")));
    }
}
