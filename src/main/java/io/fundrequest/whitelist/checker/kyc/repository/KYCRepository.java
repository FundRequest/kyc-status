package io.fundrequest.whitelist.checker.kyc.repository;

import io.fundrequest.whitelist.checker.infrastructure.JpaRepository;
import io.fundrequest.whitelist.checker.kyc.domain.KYCEntry;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KYCRepository extends JpaRepository<KYCEntry, Long> {

    @Query("select count(kycEntry) from KYCEntry kycEntry where lower(referredBy) like lower(:referredBy)")
    Long countAllByReferredBy(@Param("referredBy") final String referralKey);

    @Query("select kycEntry from KYCEntry kycEntry where lower(address) like lower(:address)")
    Optional<KYCEntry> findByAddress(@Param("address") final String address);

    @Query("delete from KYCEntry where id is not null")
    @Modifying
    void deleteAll();
}
