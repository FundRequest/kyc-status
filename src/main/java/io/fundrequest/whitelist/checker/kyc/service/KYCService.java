package io.fundrequest.whitelist.checker.kyc.service;

import io.fundrequest.whitelist.checker.kyc.domain.KYCEntry;
import io.fundrequest.whitelist.checker.kyc.dto.KYCResultDto;
import io.fundrequest.whitelist.checker.kyc.repository.KYCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class KYCService {

    @Autowired
    private KYCRepository kycRepository;

    @Transactional(readOnly = true)
    public KYCResultDto search(final String address) {

    }

    @Transactional
    public void bulk(Iterable<KYCEntry> kycEntries) {
        kycRepository.deleteAll();
        kycRepository.save(kycEntries);
    }


}
