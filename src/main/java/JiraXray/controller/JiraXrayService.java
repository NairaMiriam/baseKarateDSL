package JiraXray.controller;

import JiraXray.constans.ConstantsConf;
import JiraXray.interfaces.TestResultsImporter;
import JiraXray.util.GherkinUtil;
import JiraXray.util.PropertiesReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by NairaMasgo on 20/09/2023.
 * Role: QE Engineer
 */
public class JiraXrayService implements TestResultsImporter {
    private static JiraXrayService jXrayServiceDom;

    private static String responseBody;

    public static JiraXrayService getSingletonInstance() {
        if (jXrayServiceDom == null)
            jXrayServiceDom = new JiraXrayService();
        return jXrayServiceDom;
    }

    public void importTestResultExecutionBasic() throws IOException {
        File currentDirectory = new File((new File(".")).getAbsolutePath());
        String absolutePath = currentDirectory.getCanonicalPath();
        String cucumberJsonPath = absolutePath + "/target/" + "build/" + "cucumber.json";
        List<String> files = null;
        files = GherkinUtil.findFiles(ConstantsConf.JSON_FILE_DIRECTORY, "json");
        if (files != null) {
            files.forEach(jsonPath -> {
                Response response = (Response)RestAssured.given().header("Authorization", "Basic " + PropertiesReader.getProperty("jxray.credentials.base64"), new Object[0]).header("Content-Type", "application/json", new Object[0]).body(Paths.get(jsonPath, new String[0]).toFile()).post(PropertiesReader.getProperty("jxray.base.url") + "/rest/raven/1.0/import/execution/cucumber", new Object[0]);
                response.prettyPrint();
            });
        } else {
            Response response = (Response)RestAssured.given().header("Authorization", "Basic " + PropertiesReader.getProperty("jxray.credentials.base64"), new Object[0]).header("Content-Type", "application/json", new Object[0]).body(new File(cucumberJsonPath)).post(PropertiesReader.getProperty("jxray.base.url") + "/rest/raven/1.0/import/execution/cucumber", new Object[0]);
            ((ValidatableResponse)response.then()).log().all();
        }
    }

    public void importCucumberFeature(String projectKey, String featurePath) {
        importCucumberFeature(projectKey, featurePath, null);
    }

    public void importCucumberFeature(String projectKey, String featurePath, String infoPath) {
        RestAssured.useRelaxedHTTPSValidation();
        try {
            RequestSpecification request = RestAssured.given().header("Authorization", "Basic " + PropertiesReader.getProperty("jxray.credentials.base64"), new Object[0]).header("Content-Type", "multipart/form-data", new Object[0]).multiPart("file", new File(featurePath));
            if (infoPath != null)
                request.multiPart("testInfo", new File(infoPath));
            Response response = (Response)RestAssured.given().spec(request).post(PropertiesReader.getProperty("jxray.base.url") + "/rest/raven/1.0/import/feature?projectKey=" + projectKey, new Object[0]);
            response.body().prettyPrint();
            responseBody = response.asPrettyString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLastResponse() {
        return responseBody;
    }

    public void exportCucumberTestByFilter(String filter) {
        exportCucumberTest(filter, null);
    }

    public void exportCucumberTestByTestExecution(String keys) {
        exportCucumberTest(null, keys);
    }

    private void exportCucumberTest(String filter, String keys) {
        RestAssured.useRelaxedHTTPSValidation();
        File currentDirectory = new File((new File(".")).getAbsolutePath());
        try {
            String absolutePath = currentDirectory.getCanonicalPath();
            RequestSpecification request = (RequestSpecification)RestAssured.given().header("Authorization", "Basic " + PropertiesReader.getProperty("jxray.credentials.base64"), new Object[0]).param("fz", new Object[] { Boolean.valueOf(true) }).log().all();
            if (filter != null)
                request.param("filter", new Object[] { filter });
            if (keys != null && !keys.isEmpty())
                request.param("keys", new Object[] { keys });
            Response response = (Response)request.when().get(PropertiesReader.getProperty("jxray.base.url") + "/rest/raven/1.0/export/test", new Object[0]);
            ((ValidatableResponse)response.then()).log().all();
            Path pathToFile = (new File(absolutePath + "/src/test/resources/export/export_data.zip")).toPath();
            Files.copy(response.asInputStream(), pathToFile, new java.nio.file.CopyOption[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportCucumberTestByQueryParam(String queryParam) {
        Response response = (Response)RestAssured.given().header("Authorization", "Basic " + PropertiesReader.getProperty("jxray.credentials.base64"), new Object[0]).contentType("application/json").queryParam("keys", new Object[] { queryParam }).get(PropertiesReader.getProperty("jxray.base.url") + "/rest/raven/1.0/export/test", new Object[0]);
        response.prettyPrint();
    }
}
