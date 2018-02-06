package io.fundrequest.whitelist.checker.kyc.rest;

import io.fundrequest.whitelist.checker.kyc.dto.KYCResultDto;
import io.fundrequest.whitelist.checker.kyc.dto.ProgressDto;
import io.fundrequest.whitelist.checker.kyc.service.KYCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kyc")
public class KYCController {

    @Autowired
    private KYCService kycService;

    @GetMapping("/progress")
    @CrossOrigin(origins = "*")
    public ProgressDto progress() {
        return kycService.kycProgress();
    }

    @GetMapping("/status/{address}")
    @CrossOrigin(origins = "*")
    public KYCResultDto search(@PathVariable("address") final String address) {
        return kycService.search(address)
                .orElse(null);
    }
}
