package JiraXray.api;
import JiraXray.controller.JiraXrayService;
import JiraXray.util.LoggerUtil;
import JiraXray.util.PropertiesReader;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * Created by NairaMasgo on 20/09/2023.
 * Role: QE Engineer
 */

public class AccessJiraXray {
    private static final Logger logger = LoggerUtil.getLogger(AccessJiraXray.class);

    public static boolean isJiraXrayEnabled() {
        return PropertiesReader.getBooleanProperty("jxray.update.evidence");
    }

    public static void saveResultsJiraXray() {
        if (isJiraXrayEnabled()) {
            importJiraXrayResult(Boolean.valueOf(isJiraXrayEnabled()));
        } else {
            logJiraXrayDisabled(Boolean.valueOf(isJiraXrayEnabled()));
        }
    }

    private static void importJiraXrayResult(Boolean v) {
        logger.info("--> Save JiraXray result = {}", v);
        try {
            JiraXrayService.getSingletonInstance().importTestResultExecutionBasic();
        } catch (IOException e) {
            logger.error("Ha ocurrido un error al importar los resultados a JiraXray", e);
        }
    }

    public static void importFeatureFileToJiraXray(String projectKey, String featurePath, String infoPath) {
        JiraXrayService.getSingletonInstance().importCucumberFeature(projectKey, featurePath, infoPath);
    }

    public static String getLastReponse() {
        JiraXrayService.getSingletonInstance();
        return JiraXrayService.getLastResponse();
    }

    private static void logJiraXrayDisabled(Boolean v) {
        logger.info("--> Save JiraXray result = {}", v);
    }

    public static void exportTestFeaturesByFilter(String filter) {
        JiraXrayService.getSingletonInstance().exportCucumberTestByFilter(filter);
    }

    public static void exportTestFeaturesByTestExecution(String keys) {
        JiraXrayService.getSingletonInstance().exportCucumberTestByTestExecution(keys);
    }

    public static void exportTestFeaturesByTestQueryParams(String queryParam) {
        JiraXrayService.getSingletonInstance().exportCucumberTestByQueryParam(queryParam);
    }
}

