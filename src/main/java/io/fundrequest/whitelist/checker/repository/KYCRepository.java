package io.fundrequest.whitelist.checker.repository;

import io.fundrequest.whitelist.checker.infrastructure.JpaRepository;
import io.fundrequest.whitelist.checker.kyc.domain.KYCEntry;
import org.springframework.data.repository.query.Param;

public interface KYCRepository extends JpaRepository<KYCEntry, Long> {

    Long countAllByReferredBy(@Param("referredBy") final String referralKey);
}
