package io.fundrequest.whitelist.checker.kyc.dto;

public class KYCResultDto {

    private String address;
    private Long referralCount;
    private String status;
    private String referralKey;
    private String message;

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

    public String getStatus() {
        return status;
    }

    public KYCResultDto setStatus(final String status) {
        this.status = status;
        return this;
    }

    public String getReferralKey() {
        return referralKey;
    }

    public KYCResultDto setReferralKey(final String referralKey) {
        this.referralKey = referralKey;
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
