package com.karate;

import JiraXray.api.AccessJiraXray;
import JiraXray.util.LoggerUtil;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.ReportsUtil.generateCucumberReport;

/**
 * Created by NairaMasgo on 28/11/2023.
 * Role: QE Engineer
 */

public class ExamplesTest {
    private static final Logger logger = LoggerUtil.getLogger(ExamplesTest.class);

    @Test
    void karateTest() {
        Results results =
                Runner.path("classpath:com/karate")
                        .tags("@WebTest1")
                        .outputCucumberJson(true)
                        .parallel(2);
        generateCucumberReport(results.getReportDir());
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    @BeforeAll
    static void beforeClass() {
        logger.info("BEFORE >>>>>>>>>>>");
    }

    //Activar si se usa JiraXray.
    @AfterAll
    static void afterAll() throws IOException {
        logger.info("AFTER >>>>>>>>>>>");
        AccessJiraXray.saveResultsJiraXray();
    }

    //Opcion 2
    /*@Karate.Test
    Karate karateTest() {
        return Karate.run()
                .tags("@PETTEST")
                .relativeTo(getClass());
    }*/
}

