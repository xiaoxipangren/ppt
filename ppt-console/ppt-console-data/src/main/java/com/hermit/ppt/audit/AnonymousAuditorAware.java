package com.hermit.ppt.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AnonymousAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Anonymous");
    }
}
