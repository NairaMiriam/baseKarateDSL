package JiraXray.constans;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by NairaMasgo on 20/09/2023.
 * Role: QE Engineer
 */
public class ConstantsConf {
    public static final String PROPERTIES_PATH = "automation.properties";

    public static final String BASIC = "Basic ";

    public static final String FORMAT_DATE = "yyyyMMddHHmmss.SSS";

    public static final String JXRAY_EVIDENCE = "jxray.update.evidence";

    public static final String JXRAY_CREDENTIALS_BASE64 = "jxray.credentials.base64";

    public static final String JXRAY_BASE_URL = "jxray.base.url";

    public static final String CUCUMBER_JSON_FILE_NAME = "cucumber.json";

    public static final String TARGET_DIRECTORY = "/target/";

    public static final String BUILDER_DIRECTORY = "build/";

    public static final Path JSON_FILE_DIRECTORY = Paths.get("target/karate-reports", new String[0]);

    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String CONTENT_TYPE_MULTIPART = "multipart/form-data";

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String FILE_TYPE_JSON = "json";
}
