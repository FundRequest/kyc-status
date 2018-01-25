package io.fundrequest.whitelist.checker.kyc.dto;

public class KYCStatusDto {

    private String status;
    private String message;
    private String label;

    public KYCStatusDto(final String status, final String message, final String label) {
        this.status = status;
        this.message = message;
        this.label = label;
    }

    public String getStatus() {
        return status;
    }

    public KYCStatusDto setStatus(final String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public KYCStatusDto setMessage(final String message) {
        this.message = message;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public KYCStatusDto setLabel(final String label) {
        this.label = label;
        return this;
    }
}
