package org.travelagency.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.Continent;
import org.travelagency.model.entity.Country;
import org.travelagency.model.enums.ContinentName;
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

                createCountry("Албания", "Тирана", europe.get(), "Албански лек", "1 час назад");
                createCountry("Англия", "Лондон", europe.get(), "Британска лира", "2 часа назад");
                createCountry("Белгия", "Брюксел", europe.get(), "Белгийски франк", "1 час назад");
                createCountry("Босна и Херцеговина", "Сараево", europe.get(), "Босненска конвертируема марка", "1 час назад");
                createCountry("Германия", "Берлин", europe.get(), "Германска марка", "1 час назад");
                createCountry("Гърция", "Атина", europe.get(),"Евро", "няма" );
                createCountry("Дания", "Копенхаген", europe.get(), "Датска крона", "1 час назад");
                createCountry("Ирландия", "Дъблин", europe.get(), "Ирландска лира", "2 часа назад");
                createCountry("Исландия", "Рейкявик", europe.get(), "Исландска лира", "2 часа назад");
                createCountry("Испания", "Мадрид", europe.get(), "Евро", "1 час назад");
                createCountry("Италия", "Рим", europe.get(), "Италианска лира", "1 час назад");
                createCountry("Кипър", "Никозия", europe.get(), "Кипърска лира", "няма");
                createCountry("Северна Македония", "Скопие", europe.get(), "Македонски динар", "1 час назад");
                createCountry("Норвегия", "Осло", europe.get(), "Норвежка крона", "1 час назад");
                createCountry("Румъния", "Букурещ", europe.get(), "Румънска лея", "няма");
                createCountry("Словакия", "Братислава", europe.get(), "Словашка крона", "1 час назад");
                createCountry("Словения", "Любляна", europe.get(), "Евро", "1 час назад");
                createCountry("Сърбия", "Белград", europe.get(), "Сръбски динар", "1 час назад");
                createCountry("Турция", "Анкара", europe.get(), "Турска лира", "няма");
                createCountry("Унгария", "Будапеща", europe.get(), "Унгарски форинт", "1 час назад");
                createCountry("Франция", "Париж", europe.get(), "Френски франк", "1 час назад");
                createCountry("Нидерландия", "Амстердам", europe.get(), "Евро", "1 час назад");
                createCountry("Хърватия", "Загреб", europe.get(), "Хърватска куна", "1 час назад");
                createCountry("Черна гора", "Подгорица", europe.get(), "Евро", "1 час назад");
                createCountry("Чехия", "Прага", europe.get(), "Чешка крона", "1 час назад");
                createCountry("Швейцария", "Берн", europe.get(), "Швейцарски франк", "1 час назад");
                createCountry("Виетнам", "Ханой", asia.get(), "Виетнамски донг", "4 часа напред");
                createCountry("Дубай - ОАЕ", "Абу Даби", asia.get(), "Дирхам", "1 час напред");
                createCountry("Индия", "Ню Делхи", asia.get(), "Индиска рупия", "3 часа и половина напред");
                createCountry("Индонезия", "Нусантара", asia.get(), "Индонезийска рупия", "5 часа напред");
                createCountry("Иран", "Техеран", asia.get(), "Ирански риал", "1 час и половина напред");
                createCountry("Китай", "Пекин", asia.get(), "Китайски юан", "6 часа напред");
                createCountry("Малдиви", "Мале", asia.get(), "Малдивска рупия", "3 часа напред");
                createCountry("Тайланд", "Банкок", asia.get(), "Тайландският бат", "5 часа напред");
                createCountry("Шри Ланка", "Шри Джаяварданапура Коте", asia.get(), "Шриланкска рупия", "3 часа и половина напред");
                createCountry("Япония", "Токио", asia.get(), "Японска йена", "7 часа напред");
                createCountry("Египет", "Кайро", africa.get(), "Египетска лира", "няма");
                createCountry("Мавриций", "Порт Луи", africa.get(), "Маврицийска рупия", "2 часа напред");
                createCountry("Сейшели", "Виктория", africa.get(), "Сейшелска рупия", "2 часа напред");
                createCountry("Тунис", "Тунис", africa.get(), "Тунизийски динар", "1 час назад");
                createCountry("Южна Африка", "Кейптаун", africa.get(), "Южноафрикански ранд", "няма");
                createCountry("Доминикана", "Санто Доминго", northAmerica.get(), "Доминиканско песо", "7 часа назад");
                createCountry("Канада", "Отава", northAmerica.get(), "Канадски долар", "8 часа назад");
                createCountry("САЩ", "Вашингтон", northAmerica.get(), "Щатски долар", "8 часа назад");
                createCountry("Аржентина", "Буенос Айрес", southAmerica.get(), "Аржентинско песо", "6 часа назад");
                createCountry("Бразилия", "Бразилия", southAmerica.get(), "Бразилски реал", "6 часа назад");
                createCountry("Еквадор и Галапагоските острови", "Кито", southAmerica.get(), "Щатски долар", "9 часа назад");
                createCountry("Колумбия", "Богота", southAmerica.get(), "Колумбийско песо", "7 часа назад");
                createCountry("Куба", "Хавана", southAmerica.get(), "Кубинско песо", "7 часа назад");
                createCountry("Австралия", "Канбера", australia.get(), "Австралийски долар", "7 часа и половина назад");
            }
        }
    }

    private void createCountry(String name, String capital, Continent continent, String currency, String timeDiff) {
        Optional<Country> existingCountry = countryRepository.findByName(name);

        if (existingCountry.isEmpty()) {
            Country country = new Country();
            country.setName(name);
            country.setCapital(capital);
            country.setContinent(continent);
            country.setCurrency(currency);
            country.setTimeDifference(timeDiff);

            countryRepository.saveAndFlush(country);
        }
    }
}