package io.fundrequest.whitelist.checker.kyc.dto;

public class ProgressDto {
    private int approved;
    private int pending;
    private int declined;
    private int toContact;

    public int getApproved() {
        return approved;
    }

    public ProgressDto setApproved(int approved) {
        this.approved = approved;
        return this;
    }

    public int getPending() {
        return pending;
    }

    public ProgressDto setPending(int pending) {
        this.pending = pending;
        return this;
    }

    public int getDeclined() {
        return declined;
    }

    public ProgressDto setDeclined(int declined) {
        this.declined = declined;
        return this;
    }

    public int getToContact() {
        return toContact;
    }

    public ProgressDto setToContact(int toContact) {
        this.toContact = toContact;
        return this;
    }
}
