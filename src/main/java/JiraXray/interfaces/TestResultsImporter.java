package JiraXray.interfaces;

import java.io.IOException;

/**
 * Created by NairaMasgo on 20/09/2023.
 * Role: QE Engineer
 */
public interface TestResultsImporter {
    void importTestResultExecutionBasic() throws IOException;

    void importCucumberFeature(String paramString1, String paramString2);

    void importCucumberFeature(String paramString1, String paramString2, String paramString3);

    void exportCucumberTestByFilter(String paramString);
}
