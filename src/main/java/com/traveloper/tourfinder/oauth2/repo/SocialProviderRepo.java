package com.traveloper.tourfinder.oauth2.repo;

import com.traveloper.tourfinder.oauth2.entity.SocialProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialProviderRepo extends JpaRepository<SocialProvider, Long> {
    Optional<SocialProvider> findSocialProviderBySocialProviderName(String socialProviderName);
}
