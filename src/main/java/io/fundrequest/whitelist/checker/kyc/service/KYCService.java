package io.fundrequest.whitelist.checker.kyc.service;

import io.fundrequest.whitelist.checker.kyc.domain.KYCEntry;
import io.fundrequest.whitelist.checker.kyc.dto.KYCResultDto;
import io.fundrequest.whitelist.checker.kyc.dto.KYCStatusEnum;
import io.fundrequest.whitelist.checker.kyc.repository.KYCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class KYCService {

    private static final Pattern ignoreCaseAddrPattern = Pattern.compile("(?i)^(0x)?[0-9a-f]{40}$");

    @Autowired
    private KYCRepository kycRepository;

    @Transactional(readOnly = true)
    public Optional<KYCResultDto> search(final String address) {
        final Optional<KYCEntry> byAddress = kycRepository.findByAddress(address);

        if (isValidAddress(address)) {
            return byAddress
                    .map(entry -> new KYCResultDto()
                            .setAddress(entry.getAddress())
                            .setStatus(KYCStatusEnum.fromStatus(entry.getStatus()).toDto())
                            .setMessage(entry.getMessage()))
                    .map(x -> x.setReferralCount(
                            kycRepository.countAllByReferredBy(x.getAddress()
                            ))
                    );
        } else {
            return Optional.empty();
        }
    }

    private boolean isValidAddress(final String address) {
        return ignoreCaseAddrPattern.matcher(address).find();
    }

    @Transactional
    public void insert(Iterable<KYCEntry> kycEntries) {
        kycRepository.deleteAll();
        kycRepository.save(kycEntries);
    }
}
