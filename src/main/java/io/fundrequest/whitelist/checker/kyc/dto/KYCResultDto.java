package io.fundrequest.whitelist.checker.kyc.dto;

public class KYCResultDto {

    private String address;
    private Long referralCount;
    private String message;
    private KYCStatus status;

    public String getAddress() {
        return address;
    }

    public KYCResultDto setAddress(final String address) {
        this.address = address;
        return this;
    }

    public Long getReferralCount() {
        return referralCount;
    }

    public KYCResultDto setReferralCount(final Long referralCount) {
        this.referralCount = referralCount;
        return this;
    }

    public KYCStatus getStatus() {
        return status;
    }

    public KYCResultDto setStatus(final KYCStatus status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public KYCResultDto setMessage(final String message) {
        this.message = message;
        return this;
    }
}
