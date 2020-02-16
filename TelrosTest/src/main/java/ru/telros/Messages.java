package ru.telros;

import com.vaadin.ui.UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Roman Bogdanov
 */

public class Messages {

    private static final TreeSet<String> baseNames = new TreeSet<>();

    public static void addBundle(String baseName) {
        baseNames.add(baseName);
    }

    public static String get(String key) {
        return baseNames.stream()
                .map(baseName -> ResourceBundle.getBundle(baseName, UI.getCurrent().getLocale()))
                .filter(bundle -> bundle.containsKey(key))
                .map(bundle -> bundle.getString(key))
                .findFirst().get();
    }

    public static Set<Locale> getAvailableLanguages(File directory) throws IOException {
        return Files.list(directory.toPath())
                .map(path -> path.toFile().getName())
                .filter(name -> name.endsWith(".properties") && name.contains("_"))
                .map(name -> new Locale(name.substring(name.lastIndexOf("_") + 1, name.lastIndexOf("."))))
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Locale::getLanguage))));
    }

}
