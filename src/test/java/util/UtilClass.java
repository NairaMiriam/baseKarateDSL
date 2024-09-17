package util;

import com.intuit.karate.driver.chrome.Chrome;

/**
 * Created by NairaMasgo on 25/09/2023.
 * Role: QE Engineer
 */
public class UtilClass {

    public static void seleccionarMenu(Chrome driver, String opcionMenu) {
        String scriptXpath = "//*[contains(text(), '" + opcionMenu + "')]";
        driver.click(scriptXpath);
        driver.delay(2000);
    }
}
