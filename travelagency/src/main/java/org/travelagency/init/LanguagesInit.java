package org.travelagency.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.Language;
import org.travelagency.model.enums.LanguageName;
import org.travelagency.repository.LanguageRepository;

import java.util.Arrays;

@Component
@Order(5)
public class LanguagesInit implements CommandLineRunner {

    private final LanguageRepository languageRepository;

    public LanguagesInit(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public void run(String... args) {

        if (this.languageRepository.count() == 0) {

            Arrays.stream(LanguageName.values())
                    .forEach(languageName -> {
                        Language language = new Language();
                        language.setLanguageName(languageName);

                        String name = switch (languageName) {
                            case ENGLISH -> "Английски";
                            case SPANISH -> "Испански";
                            case FRENCH -> "Френски";
                            case GERMAN -> "Немски";
                            case ITALIAN -> "Италиански";
                            case RUSSIAN -> "Руски";
                            case CHINESE -> "Китайски";
                            case JAPANESE -> "Японски";
                            case ARABIC -> "Арабски";
                            case PORTUGUESE -> "Португалски";
                            case TURKISH -> "Турски";
                            case GREEK -> "Гръцки";
                            case DUTCH -> "Хидерландски";
                            case POLISH -> "Полски";
                            case HINDI -> "Хинди";
                        };

                        language.setName(name);
                        this.languageRepository.saveAndFlush(language);
                    });
        }
    }
}
