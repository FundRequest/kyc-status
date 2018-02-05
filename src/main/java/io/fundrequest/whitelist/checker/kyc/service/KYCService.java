package io.fundrequest.whitelist.checker.kyc.service;

import io.fundrequest.whitelist.checker.kyc.domain.KYCEntry;
import io.fundrequest.whitelist.checker.kyc.dto.KYCResultDto;
import io.fundrequest.whitelist.checker.kyc.dto.KYCStatusEnum;
import io.fundrequest.whitelist.checker.kyc.dto.ProgressDto;
import io.fundrequest.whitelist.checker.kyc.repository.KYCRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


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

    @Transactional(readOnly = true)
    @Cacheable("progress")
    public ProgressDto kycProgress() {
        List<KYCEntry> all = kycRepository.findAll();
        Map<KYCStatusEnum, Long> statusCount = all.stream().collect(Collectors.groupingBy((x -> KYCStatusEnum.fromStatus(x.getStatus())), Collectors.counting()));
        int approved = calculatePercentage(statusCount, KYCStatusEnum.APPROVED, all.size());
        int declined = calculatePercentage(statusCount, KYCStatusEnum.DECLINED, all.size());
        int toContact = calculatePercentage(statusCount, KYCStatusEnum.TO_CONTACT, all.size());
        int pending = 100 - approved - declined - toContact;
        return new ProgressDto().setApproved(approved).setDeclined(declined).setToContact(toContact).setPending(pending);
    }

    private int calculatePercentage(Map<KYCStatusEnum, Long> statusCount, KYCStatusEnum status, int totalSize) {
        if (statusCount == null || status == null) {
            return 0;
        }
        Long result = statusCount.get(status);
        if (result == null || result == 0) {
            return 0;
        }
        return (int) Math.round((result.doubleValue() / totalSize) * 100);
    }

    private boolean isValidAddress(final String address) {
        return !StringUtils.isEmpty(address) && ignoreCaseAddrPattern.matcher(address.trim()).find();
    }

    @Transactional
    @CacheEvict(cacheNames = "progress", allEntries = true)
    public void insert(Iterable<KYCEntry> kycEntries) {
        kycRepository.deleteAll();
        kycRepository.save(kycEntries);
    }
}
