package JiraXray.util;

/**
 * Created by NairaMasgo on 20/09/2023.
 * Role: QE Engineer
 */
public final class EndPoints {
    public static final String IMPORT_CUCUMBER_EXECUTION = "/rest/raven/1.0/import/execution/cucumber";

    public static final String TEST_EXECUTION_ATTACHMENTS = "/rest/api/2/issue/%s/attachments";

    public static final String IMPORT_CUCUMBER_FEATURE = "/rest/raven/1.0/import/feature?projectKey=";

    public static final String EXPORT_CUCUMBER_TEST = "/rest/raven/1.0/export/test";

    public static final String ISSUE = "/rest/api/2/issue/";
}
