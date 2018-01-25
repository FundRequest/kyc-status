package io.fundrequest.whitelist.checker.kyc.dto;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Stream;

public enum KYCStatusEnum {

    APPROVED("approve", "Approved", "Your registration was successful and your account will be whitelisted."),
    DECLINED("decline", "Declined", "Your registration was declined because you failed the KYC-check"),
    TO_CONTACT("to contact", "More information needed", "You will be contacted because your KYC-process was incomplete."),
    PENDING("", "Pending", "Your registration was saved successfully, KYC information still needs to be validated.");

    private String status;
    private String label;
    private String message;

    KYCStatusEnum(final String status, final String label, final String message) {
        this.status = status;
        this.label = label;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getLabel() {
        return label;
    }

    public String getMessage() {
        return message;
    }

    public static KYCStatusEnum fromStatus(final String status) {
        if (StringUtils.isEmpty(status)) {
            return PENDING;
        } else {
            return Stream.of(values())
                    .filter(x -> x.getStatus().equalsIgnoreCase(status))
                    .findFirst()
                    .orElse(PENDING);
        }
    }

    public KYCStatusDto toDto() {
        return new KYCStatusDto(status, message, label);
    }
}
