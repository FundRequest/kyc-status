package io.fundrequest.whitelist.checker.kyc.service;

import io.fundrequest.whitelist.checker.kyc.domain.KYCEntry;
import io.fundrequest.whitelist.checker.kyc.dto.KYCResultDto;
import io.fundrequest.whitelist.checker.kyc.repository.KYCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class KYCService {

    @Autowired
    private KYCRepository kycRepository;

    @Transactional(readOnly = true)
    public Optional<KYCResultDto> search(final String address) {
        Optional<KYCEntry> byAddress = kycRepository.findByAddress(address);
        return byAddress
                .map(entry -> {
                    return new KYCResultDto()
                            .setAddress(entry.getAddress())
                            .setStatus(entry.getStatus())

                })

    }

    @Transactional
    public void bulk(Iterable<KYCEntry> kycEntries) {
        kycRepository.deleteAll();
        kycRepository.save(kycEntries);
    }


}
