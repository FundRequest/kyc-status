package io.fundrequest.whitelist.checker.load;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import io.fundrequest.whitelist.checker.kyc.domain.KYCEntry;
import io.fundrequest.whitelist.checker.kyc.service.KYCService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class RegistrationServiceImpl implements RegistrationService {

    private String spreadsheetId;
    private String googleClientSecret;
    private KYCService kycService;

    public RegistrationServiceImpl(@Value("${io.fundrequest.tokensale.status.spreadsheet-id}") String spreadsheetId, @Value("${io.fundrequest.tokensale.status.google-sheets-client-secret}") String googleClientSecret, KYCService kycService) {
        this.spreadsheetId = spreadsheetId;
        this.googleClientSecret = googleClientSecret;
        this.kycService = kycService;
    }

    @Override
    public void load() {
        try {
            importFromSheets();
        } catch (Exception e) {
            throw new RuntimeException("unable to load");
        }
    }

    private void importFromSheets() throws Exception {
        String range = "A3:L";
        ValueRange response = getSheetsService().spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        Set<KYCEntry> entries = values.stream().map(this::createKycEntry).collect(Collectors.toSet());
        kycService.bulk(entries);
    }

    private KYCEntry createKycEntry(List<Object> row) {
        return new KYCEntry()
                .setAddress(getRowValue(row, 5))
                .setReferredBy(getRowValue(row, 10))
                .setRefferalKey("key")
                .setStatus(getRowValue(row, 11));
    }

    private String getRowValue(List row, int i) {
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
        List<String> scopes = Collections.singletonList(SheetsScopes.SPREADSHEETS);
        return GoogleCredential
                .fromStream(new ByteArrayInputStream(this.googleClientSecret.getBytes()))
                .createScoped(scopes);
    }

}
