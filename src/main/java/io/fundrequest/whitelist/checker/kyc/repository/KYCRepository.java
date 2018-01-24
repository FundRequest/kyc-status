package io.fundrequest.whitelist.checker.kyc.repository;

import io.fundrequest.whitelist.checker.infrastructure.JpaRepository;
import io.fundrequest.whitelist.checker.kyc.domain.KYCEntry;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KYCRepository extends JpaRepository<KYCEntry, Long> {

    Long countAllByReferredBy(@Param("referredBy") final String referralKey);

    Optional<KYCEntry> findByAddress(@Param("address") final String address);
}
