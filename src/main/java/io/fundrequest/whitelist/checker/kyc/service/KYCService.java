package io.fundrequest.whitelist.checker.kyc.service;

import io.fundrequest.whitelist.checker.kyc.dto.KYCResultDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class KYCService {

    @Transactional(readOnly = true)
    public KYCResultDto search(final String address) {

    }


}
