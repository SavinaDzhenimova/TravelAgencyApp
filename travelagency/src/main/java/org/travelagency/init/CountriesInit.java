package org.travelagency.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.Continent;
import org.travelagency.model.entity.Country;
import org.travelagency.model.enums.ContinentName;
import org.travelagency.model.enums.CountryName;
import org.travelagency.repository.ContinentRepository;
import org.travelagency.repository.CountryRepository;

import java.util.Arrays;
import java.util.Optional;

@Component
@Order(2)
public class CountriesInit implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final ContinentRepository continentRepository;

    public CountriesInit(CountryRepository countryRepository, ContinentRepository continentRepository) {
        this.countryRepository = countryRepository;
        this.continentRepository = continentRepository;
    }

    @Override
    public void run(String... args) {

        if (this.countryRepository.count() == 0 && this.continentRepository.count() > 0) {

            Optional<Continent> europe = this.continentRepository.findByContinentName(ContinentName.EUROPE);
            Optional<Continent> asia = this.continentRepository.findByContinentName(ContinentName.ASIA);
            Optional<Continent> africa = this.continentRepository.findByContinentName(ContinentName.AFRICA);
            Optional<Continent> southAmerica = this.continentRepository.findByContinentName(ContinentName.SOUTH_AMERICA);
            Optional<Continent> northAmerica = this.continentRepository.findByContinentName(ContinentName.NORTH_AMERICA);
            Optional<Continent> australia = this.continentRepository.findByContinentName(ContinentName.AUSTRALIA);

            if (europe.isPresent() && asia.isPresent() && africa.isPresent() && australia.isPresent()
                    && southAmerica.isPresent() && northAmerica.isPresent()) {

                Arrays.stream(CountryName.values())
                        .forEach(countryName -> {
                            Country country = new Country();
                            country.setCountryName(countryName);

                            switch (countryName) {
                                case ALBANIA -> {
                                    country.setCapital("Тирана");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Албански лек");
                                    country.setTimeDifference("1 час назад");
                                }
                                case ENGLAND -> {
                                    country.setCapital("Лондон");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Британска лира");
                                    country.setTimeDifference("2 часа назад");
                                }
                                case BELGIUM -> {
                                    country.setCapital("Брюксел");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Белгийски франк");
                                    country.setTimeDifference("1 час назад");
                                }
                                case BOSNIA_AND_HERZEGOVINA -> {
                                    country.setCapital("Сараево");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Босненска конвертируема марка");
                                    country.setTimeDifference("1 час назад");
                                }
                                case GERMANY -> {
                                    country.setCapital("Берлин");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Германска марка");
                                    country.setTimeDifference("1 час назад");
                                }
                                case GREECE -> {
                                    country.setCapital("Атина");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Евро");
                                    country.setTimeDifference("няма");
                                }
                                case DENMARK -> {
                                    country.setCapital("Копенхаген");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Датска крона");
                                    country.setTimeDifference("1 час назад");
                                }
                                case IRELAND -> {
                                    country.setCapital("Дъблин");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Ирландска лира");
                                    country.setTimeDifference("2 часа назад");
                                }
                                case ICELAND -> {
                                    country.setCapital("Рейкявик");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Исландска крона");
                                    country.setTimeDifference("2 часа назад");
                                }
                                case SPAIN -> {
                                    country.setCapital("Мадрид");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Евро");
                                    country.setTimeDifference("1 час назад");
                                }
                                case ITALY -> {
                                    country.setCapital("Рим");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Италианска лира");
                                    country.setTimeDifference("1 час назад");
                                }
                                case CYPRUS -> {
                                    country.setCapital("Никозия");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Кипърска лира");
                                    country.setTimeDifference("няма");
                                }
                                case NORTH_MACEDONIA -> {
                                    country.setCapital("Скопие");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Македонски динар");
                                    country.setTimeDifference("1 час назад");
                                }
                                case NORWAY -> {
                                    country.setCapital("Осло");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Норвежка крона");
                                    country.setTimeDifference("1 час назад");
                                }
                                case ROMANIA -> {
                                    country.setCapital("Букурещ");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Румънска лея");
                                    country.setTimeDifference("няма");
                                }
                                case SLOVAKIA -> {
                                    country.setCapital("Братислава");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Словашка крона");
                                    country.setTimeDifference("1 час назад");
                                }
                                case SLOVENIA -> {
                                    country.setCapital("Любляна");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Евро");
                                    country.setTimeDifference("1 час назад");
                                }
                                case SERBIA -> {
                                    country.setCapital("Белград");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Сръбски динар");
                                    country.setTimeDifference("1 час назад");
                                }
                                case TURKEY -> {
                                    country.setCapital("Анкара");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Турска лира");
                                    country.setTimeDifference("2 часа назад");
                                }
                                case HUNGARY -> {
                                    country.setCapital("Будапеща");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Унгарски форинт");
                                    country.setTimeDifference("1 час назад");
                                }
                                case FRANCE -> {
                                    country.setCapital("Париж");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Френски франк");
                                    country.setTimeDifference("1 час назад");
                                }
                                case NETHERLANDS -> {
                                    country.setCapital("Амстердам");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Евро");
                                    country.setTimeDifference("1 час назад");
                                }
                                case CROATIA -> {
                                    country.setCapital("Загреб");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Хърватска куна");
                                    country.setTimeDifference("1 час назад");
                                }
                                case MONTENEGRO -> {
                                    country.setCapital("Подгорица");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Евро");
                                    country.setTimeDifference("1 час назад");
                                }
                                case CZECH_REPUBLIC -> {
                                    country.setCapital("Прага");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Чешка крона");
                                    country.setTimeDifference("1 час назад");
                                }
                                case SWITZERLAND -> {
                                    country.setCapital("Берн");
                                    country.setContinent(europe.get());
                                    country.setCurrency("Швейцарски франк");
                                    country.setTimeDifference("1 час назад");
                                }
                                case VIETNAM -> {
                                    country.setCapital("Ханой");
                                    country.setContinent(asia.get());
                                    country.setCurrency("Виетнамски донг");
                                    country.setTimeDifference("4 часа напред");
                                }
                                case UNITED_ARAB_EMIRATES -> {
                                    country.setCapital("Абу Даби");
                                    country.setContinent(asia.get());
                                    country.setCurrency("Дирхам");
                                    country.setTimeDifference("1 час напред");
                                }
                                case INDIA -> {
                                    country.setCapital("Ню Делхи");
                                    country.setContinent(asia.get());
                                    country.setCurrency("Индиска рупия");
                                    country.setTimeDifference("3 часа и половина напред");
                                }
                                case INDONESIA -> {
                                    country.setCapital("Нусантара");
                                    country.setContinent(asia.get());
                                    country.setCurrency("Индонезийска рупия");
                                    country.setTimeDifference("5 часа напред");
                                }
                                case IRAN -> {
                                    country.setCapital("Техеран");
                                    country.setContinent(asia.get());
                                    country.setCurrency("Ирански риал");
                                    country.setTimeDifference("1 час и половина напред");
                                }
                                case CHINA -> {
                                    country.setCapital("Пекин");
                                    country.setContinent(asia.get());
                                    country.setCurrency("Китайски юан");
                                    country.setTimeDifference("6 часа напред");
                                }
                                case MALDIVES -> {
                                    country.setCapital("Мале");
                                    country.setContinent(asia.get());
                                    country.setCurrency("Малдивска руфия");
                                    country.setTimeDifference("3 часа напред");
                                }
                                case THAILAND -> {
                                    country.setCapital("Банкок");
                                    country.setContinent(asia.get());
                                    country.setCurrency("Тайландският бат");
                                    country.setTimeDifference("5 часа напред");
                                }
                                case SRI_LANKA -> {
                                    country.setCapital("Шри Джаяварданапура Коте");
                                    country.setContinent(asia.get());
                                    country.setCurrency("Шриланкска рупия");
                                    country.setTimeDifference("3 часа и половина напред");
                                }
                                case JAPAN -> {
                                    country.setCapital("Токио");
                                    country.setContinent(asia.get());
                                    country.setCurrency("Японски йени");
                                    country.setTimeDifference("7 часа напред");
                                }
                                case EGYPT -> {
                                    country.setCapital("Кайро");
                                    country.setContinent(africa.get());
                                    country.setCurrency("Египетска лира");
                                    country.setTimeDifference("няма");
                                }
                                case MAURITIUS -> {
                                    country.setCapital("Порт Луи");
                                    country.setContinent(africa.get());
                                    country.setCurrency("Маврицийска рупия");
                                    country.setTimeDifference("2 часа напред");
                                }
                                case SEYCHELLES -> {
                                    country.setCapital("Виктория");
                                    country.setContinent(africa.get());
                                    country.setCurrency("Сейшелски рупии");
                                    country.setTimeDifference("2 часа напред");
                                }
                                case TUNISIA -> {
                                    country.setCapital("Тунис");
                                    country.setContinent(africa.get());
                                    country.setCurrency("Тунизийски динар");
                                    country.setTimeDifference("1 час назад");
                                }
                                case SOUTH_AFRICA -> {
                                    country.setCapital("Кейптаун");
                                    country.setContinent(africa.get());
                                    country.setCurrency("Южноафрикански ранд");
                                    country.setTimeDifference("няма");
                                }
                                case DOMINICAN_REPUBLIC -> {
                                    country.setCapital("Санто Доминго");
                                    country.setContinent(northAmerica.get());
                                    country.setCurrency("Доминиканско песо");
                                    country.setTimeDifference("7 часа назад");
                                }
                                case CANADA -> {
                                    country.setCapital("Отава");
                                    country.setContinent(northAmerica.get());
                                    country.setCurrency("Канадски долар");
                                    country.setTimeDifference("8 часа назад");
                                }
                                case USA -> {
                                    country.setCapital("Вашингтон");
                                    country.setContinent(northAmerica.get());
                                    country.setCurrency("Щатски долар");
                                    country.setTimeDifference("8 часа назад");
                                }
                                case ARGENTINA -> {
                                    country.setCapital("Буенос Айрес");
                                    country.setContinent(southAmerica.get());
                                    country.setCurrency("Аржентинско песо");
                                    country.setTimeDifference("6 часа назад");
                                }
                                case BRAZIL -> {
                                    country.setCapital("Бразилия");
                                    country.setContinent(southAmerica.get());
                                    country.setCurrency("Бразилски реал");
                                    country.setTimeDifference("6 часа назад");
                                }
                                case ECUADOR_AND_GALAPAGOS_ISLANDS -> {
                                    country.setCapital("Кито");
                                    country.setContinent(southAmerica.get());
                                    country.setCurrency("Щатски долар");
                                    country.setTimeDifference("9 часа назад");
                                }
                                case COLOMBIA -> {
                                    country.setCapital("Богота");
                                    country.setContinent(southAmerica.get());
                                    country.setCurrency("Колумбийско песо");
                                    country.setTimeDifference("7 часа назад");
                                }
                                case CUBA -> {
                                    country.setCapital("Хавана");
                                    country.setContinent(southAmerica.get());
                                    country.setCurrency("Кубинско песо");
                                    country.setTimeDifference("7 часа назад");
                                }
                                case AUSTRALIA -> {
                                    country.setCapital("Канбера");
                                    country.setContinent(australia.get());
                                    country.setCurrency("Австралийски долар");
                                    country.setTimeDifference("7 часа и половина назад");
                                }
                            }

                            this.countryRepository.saveAndFlush(country);
                        });
            }
        }
    }
}