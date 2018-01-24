package io.fundrequest.whitelist.checker.kyc.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "kyc_entries")
public class KYCEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "address")
    private String address;
    @Column(name = "referral_key")
    private String referralKey;
    @Column(name = "referred_by")
    private String referredBy;
    @Column(name = "status")
    private String status;
    @Column(name = "message")
    private String message;

    public Long getId() {
        return id;
    }

    public KYCEntry setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public KYCEntry setAddress(final String address) {
        this.address = address;
        return this;
    }


    public String getReferredBy() {
        return referredBy;
    }

    public KYCEntry setReferredBy(final String referredBy) {
        this.referredBy = referredBy;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public KYCEntry setStatus(final String status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KYCEntry kycEntry = (KYCEntry) o;
        return Objects.equals(address, kycEntry.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(address);
    }

    public String getReferralKey() {
        return referralKey;
    }

    public KYCEntry setReferralKey(final String referralKey) {
        this.referralKey = referralKey;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public KYCEntry setMessage(final String message) {
        this.message = message;
        return this;
    }
}
