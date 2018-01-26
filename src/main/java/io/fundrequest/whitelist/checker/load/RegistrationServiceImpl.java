package io.fundrequest.whitelist.checker.load;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import io.fundrequest.whitelist.checker.RegistrationService;
import io.fundrequest.whitelist.checker.kyc.domain.KYCEntry;
import io.fundrequest.whitelist.checker.kyc.dto.KYCStatusEnum;
import io.fundrequest.whitelist.checker.kyc.service.KYCService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
class RegistrationServiceImpl implements RegistrationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationServiceImpl.class);
    private static final String SHEET_RANGE = "A3:M";
    private static final int ROW_STATUS = 11;
    private static final int ROW_REFERRED_BY = 10;
    private static final int ROW_ADDRESS = 5;
    public static final int ROW_MESSAGE = 12;
    private String spreadsheetId;
    private String googleClientSecret;
    private KYCService kycService;

    public RegistrationServiceImpl(@Value("${io.fundrequest.tokensale.status.spreadsheet-id}") String spreadsheetId,
                                   @Value("${io.fundrequest.tokensale.status.google-sheets-client-secret}") String googleClientSecret,
                                   KYCService kycService) {
        this.spreadsheetId = spreadsheetId;
        this.googleClientSecret = googleClientSecret;
        this.kycService = kycService;
    }

    @Scheduled(fixedDelay = 300000L)
    @Override
    public void load() {
        try {
            importFromSheets();
            LOGGER.info("Imported new data");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("unable to load");
        }
    }

    private void importFromSheets() throws Exception {
        final ValueRange response = getSheetsService().spreadsheets().values()
                .get(spreadsheetId, SHEET_RANGE)
                .execute();
        final List<List<Object>> values = response.getValues();
        kycService.insert(
                values.stream()
                        .map(this::createKycEntry)
                        .collect(Collectors.toSet())
        );
    }

    private KYCEntry createKycEntry(List<Object> row) {
        final String address = getRowValue(row, ROW_ADDRESS);
        final String referredBy = getReferredBy(row, ROW_REFERRED_BY);
        String message = getRowValue(row, ROW_MESSAGE);
        return new KYCEntry()
                .setAddress(address)
                .setReferredBy(referredBy)
                .setReferralKey(address)
                .setStatus(getStatus(row))
                .setMessage(null);
    }

    private String getStatus(List<Object> row) {
        String status = getRowValue(row, ROW_STATUS);
        if (StringUtils.isNotBlank(status) && KYCStatusEnum.TO_CONTACT.getStatus().equalsIgnoreCase(status)) {
            return KYCStatusEnum.PENDING.getStatus();
        }
        return status;
    }

    private String getReferredBy(List<Object> row, int i) {
        String key = getRowValue(row, i);
        if (StringUtils.isNotBlank(key)) {
            key = key.replace("dke02sx6", "");
            key = key.replace("dke02sx", "");
            if (key.length() > 42) {
                key = key.substring(0, 42);
            }
            return key;
        }
        return null;
    }

    private String getRowValue(final List row, final int i) {
        return i < row.size() ? row.get(i).toString() : null;
    }

    private Sheets getSheetsService() throws Exception {
        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), authorize())
                .setApplicationName("TOKENSALE-STATUS")
                .build();
    }

    private Credential authorize() throws IOException {
        final List<String> scopes = Collections.singletonList(SheetsScopes.SPREADSHEETS);
        return GoogleCredential
                .fromStream(new ByteArrayInputStream(this.googleClientSecret.getBytes()))
                .createScoped(scopes);
    }

}
