package io.fundrequest.whitelist.checker.kyc.domain;

import javax.persistence.*;

@Entity
@Table(name = "kyc_entry")
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

    public String getRefferalKey() {
        return referralKey;
    }

    public KYCEntry setRefferalKey(final String referralKey) {
        this.referralKey = referralKey;
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
}
