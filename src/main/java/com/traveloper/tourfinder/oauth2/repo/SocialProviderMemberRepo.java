package com.traveloper.tourfinder.oauth2.repo;


import com.traveloper.tourfinder.oauth2.entity.SocialProviderMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialProviderMemberRepo extends JpaRepository<SocialProviderMember, Long> {
}
