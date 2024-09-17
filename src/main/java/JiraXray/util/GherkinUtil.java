package JiraXray.util;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by NairaMasgo on 20/09/2023.
 * Role: QE Engineer
 */
public class GherkinUtil {
    private static final Map<String, String> englishToSpanish = new HashMap<>();

    private static final Map<String, String> spanishToEnglish = new HashMap<>();

    private static String translateKeywords(String feature, String sourceLanguage, String targetLanguage) {
        Map<String, String> fromSourceLanguageToTargetLanguage;
        if (sourceLanguage == null || targetLanguage == null)
            return feature;
        englishToSpanish.put("Feature", "Caracter");
        englishToSpanish.put("Background", "Antecedentes");
        englishToSpanish.put("Scenario", "Escenario");
        englishToSpanish.put("Scenario Outline", "Esquema del escenario");
        englishToSpanish.put("Given", "Dado");
        englishToSpanish.put("When", "Cuando");
        englishToSpanish.put("Then", "Entonces");
        englishToSpanish.put("And", "Y");
        englishToSpanish.put("But", "Pero");
        englishToSpanish.put("Examples", "Ejemplos");
        for (Map.Entry<String, String> entry : englishToSpanish.entrySet())
            spanishToEnglish.put(entry.getValue(), entry.getKey());
        if (sourceLanguage.equals("en") && targetLanguage.equals("es")) {
            fromSourceLanguageToTargetLanguage = englishToSpanish;
        } else if (sourceLanguage.equals("es") && targetLanguage.equals("en")) {
            fromSourceLanguageToTargetLanguage = spanishToEnglish;
        } else {
            return feature;
        }
        for (Map.Entry<String, String> entry : fromSourceLanguageToTargetLanguage.entrySet())
            feature = feature.replaceAll("\\b" + (String) entry.getKey() + "\\b", entry.getValue());
        return feature;
    }

    public static void translateZip(String sourceZipFilename, String targetZipFilename, String sourceLanguage, String targetLanguage) {
        File currentDirectory = new File((new File(".")).getAbsolutePath());
        String absolutePath = null;
        try {
            absolutePath = currentDirectory.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(absolutePath + "/" + sourceZipFilename, new String[0]), new java.nio.file.OpenOption[0]));
             ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(Paths.get(absolutePath + "/" + targetZipFilename, new String[0]), new java.nio.file.OpenOption[0]))) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                if (!zipEntry.isDirectory() && zipEntry.getName().endsWith(".feature")) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zipInputStream.read(buffer)) > 0)
                        outputStream.write(buffer, 0, len);
                    String feature = outputStream.toString();
                    String translatedFeature = translateKeywords(feature, sourceLanguage, targetLanguage);
                    zipOutputStream.putNextEntry(new ZipEntry(zipEntry.getName()));
                    byte[] bytes = translatedFeature.getBytes();
                    zipOutputStream.write(bytes, 0, bytes.length);
                    outputStream.close();
                } else {
                    zipOutputStream.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zipInputStream.read(buffer)) > 0)
                        zipOutputStream.write(buffer, 0, len);
                }
                zipOutputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void translateFile(String sourceFilename, String targetFilename, String sourceLanguage, String targetLanguage) {
        try {
            String absolutePath = Paths.get(".", new String[0]).toAbsolutePath().normalize().toString();
            String sourcePath = Paths.get(absolutePath, new String[]{sourceFilename}).toString();
            String targetPath = Paths.get(absolutePath, new String[]{targetFilename}).toString();
            String content = new String(Files.readAllBytes(Paths.get(sourcePath, new String[0])));
            String translatedContent = translateKeywords(content, sourceLanguage, targetLanguage);
            Files.write(Paths.get(targetPath, new String[0]), translatedContent.getBytes(), new java.nio.file.OpenOption[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> findFiles(Path path, String fileExtension) throws IOException {
        if (Files.isDirectory(path, new java.nio.file.LinkOption[0])) {
            List<String> result;
            try (Stream<Path> walk = Files.walk(path, new java.nio.file.FileVisitOption[0])) {
                result = (List<String>) walk.filter(p -> !Files.isDirectory(p, new java.nio.file.LinkOption[0])).map(p -> p.toString().toLowerCase()).filter(f -> f.endsWith(fileExtension)).collect(Collectors.toList());
            }
            return result;
        }
        return null;
    }
}
