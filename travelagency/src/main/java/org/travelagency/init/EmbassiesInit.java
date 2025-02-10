package org.travelagency.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.Country;
import org.travelagency.model.entity.Embassy;
import org.travelagency.repository.CountryRepository;
import org.travelagency.repository.EmbassyRepository;

import java.util.Optional;

@Component
@Order(3)
public class EmbassiesInit implements CommandLineRunner {

    private final EmbassyRepository embassyRepository;
    private final CountryRepository countryRepository;

    public EmbassiesInit(EmbassyRepository embassyRepository, CountryRepository countryRepository) {
        this.embassyRepository = embassyRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public void run(String... args) {

        createEmbassy("Албания",
                "Rruga Skenderbeu 12, Tirana, Albania",
                "+355 4 22 32 906",
                "+355 4 22 32 272",
                "Embassy.Tirana@mfa.bg",
                "www.mfa.bg/embassies/albania",
                "+355 4 22 33 155");
        createEmbassy("Англия",
                "186-188 Queen’s Gate, London SW7 5HL, UK",
                "+44 20 7581 3144",
                "+44 20 7584 4948",
                "embassy.london@mfa.bg",
                "www.mfa.bg/embassies/uk",
                "+442075849400");
        createEmbassy("Белгия",
                "Avenue Moscicki 7, 1180 Bruxelles",
                "+32 2 374 47 88",
                "+32 2 374 08 66",
                "embassy.brussels@mfa.bg",
                "www.mfa.bg/embassies/belgium",
                "+32 473 981 042");
        createEmbassy("Босна и Херцеговина",
                "71000 Sarajevo, 30 Radnička Str., BiH",
                "+387 33 879 955/956",
                "+387 33 879 958",
                "Embassy.Sarajevo@mfa.bg",
                "www.mfa.bg/embassies/bosniaherzegovina",
                "+387 61599493");
        createEmbassy("Германия",
                "Mauerstrasse 11, 10117 Berlin",
                "+49 30 201 09 22",
                "+49 30 208 68 38",
                "Embassy.Berlin@mfa.bg",
                "www.mfa.bg/embassies/germany",
                "+49 30 201 09 22");
        createEmbassy("Гърция",
                "33 Α, Stratigou Kallari Str., 154 52 Paleo Psychiκo, Athens, Greece",
                "+30 210 67 48 106",
                "+30 210 67 48 130",
                "embassy.athens@mfa.bg",
                "www.mfa.bg/embassies/greece",
                "+30 210 67 48 107");
        createEmbassy("Дания",
                "Gamlehave Alle 7, 2920 Charlottenlund, Copenhagen, Kingdom of Denmark",
                "+45 39 64 24 84",
                "+45 39 63 49 23",
                "Embassy.Copenhagen@mfa.bg",
                "www.mfa.bg/embassies/denmark",
                "+45 41 276251");
        createEmbassy("Ирландия",
                "22 Burlington Road, Ballsbridge, Dublin 4",
                "+353 1 660 32 93",
                "+353 1 660 3915",
                "Embassy.Dublin@mfa.bg",
                "www.mfa.bg/embassies/greece",
                "+353 876 830 566");
        createEmbassy("Исландия",
                "Tidemands gate 11, 02 44 Oslo",
                "+47 22 55 40 40",
                "+47 22 55 40 40",
                "Embassy.Oslo@mfa.bg",
                "www.mfa.bg/embassies/norway",
                "+47 480 89 793");
        createEmbassy("Испания",
                "Travesía de Santa María Magdalena 15, 28016 Madrid",
                "+34 91 345 5761",
                "+34 91 345 6651",
                "Embassy.Madrid@mfa.bg",
                "www.mfa.bg/embassies/spain",
                "+34 678 013 846");
        createEmbassy("Италия",
                "Via Pietro Paolo Rubens, 21, 00197 ROMA",
                "+39 06 322 46 40",
                "+39 06 322 61 22",
                "Embassy.Rome@mfa.bg",
                "www.mfa.bg/embassies/italy",
                "+39 349 492 7764");
        createEmbassy("Кипър",
                "13, Konstantinou Paleologou, 2406, Engomi, Nicosia",
                "+357 22 67 24 86",
                "+357 22 67 65 98",
                "Embassy.Nicosia@mfa.bg",
                "www.mfa.bg/embassies/cyprus",
                "+357 22 67 24 86");
        createEmbassy("Северна Македония",
                "Zlatko Shnajder, № 3, Skopje",
                "+389 2 3229 444",
                "+389 2 3246 493",
                "Embassy.Skopje@mfa.bg",
                "www.mfa.bg/embassies/macedonia",
                "+389  75 280 013");
        createEmbassy("Норвегия",
                "Tidemands gate 11, 02 44 Oslo",
                "+47 22 55 40 40",
                "+47 22 55 40 40",
                "Embassy.Oslo@mfa.bg",
                "www.mfa.bg/embassies/norway",
                "+47 480 89 793");
        createEmbassy("Румъния",
                "Bucuresti, sector 1, str. Rabat  nr. 5",
                "+40 21 230 21 50",
                "+40 21 230 21 59",
                "Embassy.Bucharest@mfa.bg",
                "www.mfa.bg/embassies/romania",
                "+40 21 230 21 50");
        createEmbassy("Словакия",
                "Kuzmanyho str.1, 811 06 Bratislava, Slovak Republic",
                "(00421 2) 544-15-308",
                "(00421 2) 544-12-404",
                "Embassy.Bratislava@mfa.bg",
                "www.mfa.bg/embassies/slovakia",
                "(00421)911-284-229");
        createEmbassy("Словения",
                "№ 9а/ Slovenia, 1000 Ljubljana, Stara Ježica 9a",
                "+386 1 28 32 899",
                "+386 1 28 32 901",
                "Embassy.Ljubljana@mfa.bg",
                "www.mfa.bg/embassies/slovenia",
                "+ 386 41 722 238");
        createEmbassy("Сърбия",
                "Belgrade, Bircaninovastr. No. 26",
                "00381 11 3613980",
                "00381 11 3620116",
                "Embassy.Belgrade@mfa.bg",
                "www.mfa.bg/embassies/serbia",
                "+381 11 3613980");
        createEmbassy("Турция",
                "Atatürk Bulvarı № 124, 06680 Kavıklıdere-Çankaya, Ankara, Republic of Türkiye",
                "(+90312) 467-20-71",
                "467-25-74",
                "Embassy.Ankara@mfa.bg",
                "www.mfa.bg/embassies/turkey",
                "(+90312) 467-20-71");
        createEmbassy("Унгария",
                "Andrassy ut. 115, 1062 Budapest, Hungary",
                "+36 1 322 08 24",
                "+36 1 322 52 15",
                "Embassy.Budapest@mfa.bg",
                "www.mfa.bg/embassies/hungary",
                "+36 20 534 4559");
        createEmbassy("Франция",
                "Ambassade de Bulgarie; 1, avenue Rapp; 75007 Paris",
                "+33 1 45 51 85 90",
                "+33 1 45 56 97 50",
                "Embassy.Paris@mfa.bg",
                "www.mfa.bg/embassies/france",
                "+33 6 21 84 95 15");
        createEmbassy("Нидерландия",
                "The Netherlands, The Hague, Duinroosweg 9, 2597 KJ, Den Haag",
                "+31 70 358 46 88",
                "+31 70 350 30 51",
                "Embassy.Hague@mfa.bg",
                "www.mfa.bg/embassies/netherlands",
                "+31686213612");
        createEmbassy("Хърватия",
                "15 Dvorničićeva str., 10000 Zagreb, Croatia",
                "+385 1 2755095",
                "+385 1 2755095",
                "Embassy.Zagreb@mfa.bg",
                "www.mfa.bg/embassies/croatia",
                "+385 984 69 808");
        createEmbassy("Черна гора",
                "Bul. Ivana Crnojevića 99/2, 81000 Podgorica",
                "+382 20 655 009",
                "+382 20 655 008",
                "Embassy.Podgorica@mfa.bg",
                "www.mfa.bg/embassies/montenegro",
                "+382 69 133 616");
        createEmbassy("Чехия",
                "Krakovská 6, 110 00 Praha 1, Česká republika",
                "00420 222-212-011",
                "00420 222-211-728",
                "embassy.prague@mfa.bg",
                "www.mfa.bg/embassies/czech",
                "+ 420 774 790 991");
        createEmbassy("Швейцария",
                "Bernastrasse 2, CH-3005 Bern",
                "+41 31 351 14 55",
                "+41 31 351 00 64",
                "Embassy.Bern@mfa.bg",
                "www.mfa.bg/embassies/switzerland",
                "+41 79 824 38 22");
        createEmbassy("Виетнам",
                "No. 5, Lane 294 Kim Ma Street, Ba Dinh District, Hanoi, Vietnam",
                "+84 24 3845 2908",
                "+84 24 3846 0856",
                "Embassy.Hanoi@mfa.bg",
                "www.mfa.bg/embassies/vietnam",
                "+84 83 940 9129");
        createEmbassy("Дубай - ОАЕ",
                "Al-Nahayan Camp Area, Jafn Street, Villa 6, Abu Dhabi, P.O.Box 73541",
                "00971 2 6443381",
                "00971 2 6443383",
                "Embassy.Abu.Dhabi@mfa.bg",
                "www.mfa.bg/embassies/uae",
                "00971 2 6443384");
        createEmbassy("Индия",
                "16/17 Chandragupta Marg, Chanakyapuri, New Delhi – 110021",
                "+911126115550",
                "+911126876190",
                "Embassy.Delhi@mfa.bg",
                "www.mfa.bg/embassies/india",
                "+911126115550");
        createEmbassy("Индонезия",
                "Jakarta 10310, 34-36, Jl. Imam Bonjol",
                "+62 21 390 40 48",
                "+62 21 390 40 49",
                "embassy.jakarta@mfa.bg",
                "www.mfa.bg/embassies/indonesia",
                "+62 81296000691");
        createEmbassy("Иран",
                "Vali-ye Asr Ave, Tavanir Ave, Nezami-ye Ganjavi Str. 40, Tehran",
                "+98 21 8877 5662",
                "+98 21 8877 9680",
                "Embassy.Tehran@mfa.bg",
                "www.mfa.bg/embassies/iran",
                "+98 930 568 34 09");
        createEmbassy("Китай",
                "Xiu Shui Bei Jie 4, Beijing - 100600, China",
                "+86 10 6532 1916",
                "+86 10 6532 4502",
                "Embassy.Beijing@mfa.bg",
                "www.mfa.bg/embassies/china",
                "+86 156 4658 8195");
        createEmbassy("Малдиви",
                "16/17 Chandragupta Marg, Chanakyapuri, New Delhi – 110021",
                "+911126115550",
                "+911126876190",
                "Embassy.Delhi@mfa.bg",
                "www.mfa.bg/embassies/india",
                "+911126115550");
        createEmbassy("Виетнам",
                "No. 5, Lane 294 Kim Ma Street, Ba Dinh District, Hanoi, Vietnam",
                "+84 24 3845 2908",
                "+84 24 3846 0856",
                "Embassy.Hanoi@mfa.bg",
                "www.mfa.bg/embassies/vietnam",
                "+84 83 940 9129");
        createEmbassy("Шри Ланка",
                "16/17 Chandragupta Marg, Chanakyapuri, New Delhi – 110021",
                "+911126115550",
                "+911126876190",
                "Embassy.Delhi@mfa.bg",
                "www.mfa.bg/embassies/india",
                "+911126115550");
        createEmbassy("Япония",
                "Yoyogi 5-36-3, Shibuya-ku, Tokyo 151-0053",
                "+81 3 3465 1021",
                "+81 3 3465 1031",
                "Embassy.Tokyo@mfa.bg",
                "www.mfa.bg/embassies/japan",
                "+81 80 7966 5608");
        createEmbassy("Египет",
                "6, El Malek Al Afdal str., Zamalek 11211, Cairo, Arab Republic of Egypt",
                "+202 273 63 025",
                "+202 273 63 826",
                "Embassy.Cairo@mfa.bg",
                "www.mfa.bg/embassies/egypt",
                "+20 12 231 71 991");
        createEmbassy("Мавриций",
                "1071 Stanza Bopape str.0083 Hatfield, Pretoria, Republic of South Africa",
                "+27 12 342 37 20",
                "+2712 342 37 21",
                "Embassy.Pretoria@mfa.bg",
                "www.mfa.bg/embassies/southafrica",
                "+27 79 893 7336");
        createEmbassy("Сейшели",
                "Bole KK, Kebele 04/06/07, Addis Ababa, Ethiopia, P.O.Box 987",
                "00251 – 911252514",
                "00251 – 911252514",
                "bul.addis@gmail.com",
                "www.mfa.bg/embassies/ethiopia",
                "00251 – 911252514");
        createEmbassy("Тунис",
                "5 Rue Ryhane, Cité Mahrajène, 1082 Tunis",
                "+216 71 798 962",
                "+216 71 800 980",
                "Embassy.Tunis@mfa.bg",
                "www.mfa.bg/embassies/tunisia",
                "+216 55 813 135");
        createEmbassy("Южна Африка",
                "1071 Stanza Bopape str.0083 Hatfield, Pretoria, Republic of South Africa",
                "+27 12 342 37 20",
                "+2712 342 37 21",
                "Embassy.Pretoria@mfa.bg",
                "www.mfa.bg/embassies/southafrica",
                "+27 79 893 7336");
        createEmbassy("Доминикана",
                " 5-ta Avenida № 6407, esquina calle 66, Miramar, Playa, Ciudad de La Habana",
                "+537 204 67 66",
                "+537 204 67 66",
                "Embassy.Havana@mfa.bg",
                "www.mfa.bg/embassies/kuba",
                "+535 286 24 49");
        createEmbassy("Канада",
                "325 Stewart Street, Ottawa, Ontario, K1N 6K5, Canada",
                "+1 613 789 3215",
                "+1 613 7893524",
                "Embassy.Ottawa@mfa.bg",
                "www.mfa.bg/embassies/canada",
                "+1 613 316 1631");
        createEmbassy("САЩ",
                "1621 22nd Street, NW, Dimitar Peshev Plaza, Washington, DC 20008, USA",
                "+1 202 387 0174",
                "+1 202 234 7973",
                "Embassy.Washington@mfa.bg",
                "www.mfa.bg/embassies/usa",
                "+1 (202) 387-5770");
        createEmbassy("Аржентина",
                "Sucre 1568, C1428DUT, Ciudad Autónoma de Buenos Aires, Argentina",
                "+54 11 4781 8644",
                "+54 11 4786 6273",
                "bg.buenosaires@mfa.bg",
                "www.mfa.bg/embassies/argentina",
                "+54 9 11 3874 3170");
        createEmbassy("Бразилия",
                "SEN 803, Asa Norte, CEP: 70800-911, Brasilia, DF, Brasil",
                "+55 61 32236193",
                "+55 61 33233285",
                "Embassy.Brasilia@mfa.bg",
                "www.mfa.bg/embassies/brazil",
                "+55 61 32239849");
        createEmbassy("Еквадор и Галапагоските острови",
                "SEN 803, Asa Norte, CEP: 70800-911, Brasilia, DF, Brasil",
                "+55 61 32236193",
                "+55 61 33233285",
                "Embassy.Brasilia@mfa.bg",
                "www.mfa.bg/embassies/brazil",
                "+55 61 32239849");
        createEmbassy("Колумбия",
                "SEN 803, Asa Norte, CEP: 70800-911, Brasilia, DF, Brasil",
                "+55 61 32236193",
                "+55 61 33233285",
                "Embassy.Brasilia@mfa.bg",
                "www.mfa.bg/embassies/brazil",
                "+55 61 32239849");
        createEmbassy("Куба",
                "5-ta Avenida № 6407, esquina calle 66, Miramar, Playa, Ciudad de La Habana",
                "+537 204 67 66",
                "+537 204 67 66",
                "Embassy.Havana@mfa.bg",
                "www.mfa.bg/embassies/kuba",
                "+535 286 24 49");
        createEmbassy("Австралия",
                "29 Pindari Crescent, O’Malley, ACT 2606",
                "+612 62869700",
                "+612 62869600",
                "Embassy.Canberra@mfa.bg",
                "www.mfa.bg/embassies/australia",
                "+61 420 744 840");
    }

    private void createEmbassy(String name, String address, String phoneNumber, String fax, String email, String webpage, String dutyPhone) {
        Optional<Country> optionalCountry = this.countryRepository.findByName(name);

        if (optionalCountry.isPresent()) {
            Country country = optionalCountry.get();

            Optional<Embassy> existingEmbassy = this.embassyRepository.findByName(name);

            if (existingEmbassy.isEmpty()) {
                Embassy embassy = new Embassy();
                embassy.setName(name);
                embassy.setAddress(address);
                embassy.setPhoneNumber(phoneNumber);
                embassy.setFax(fax);
                embassy.setEmail(email);
                embassy.setWebpage(webpage);
                embassy.setDutyPhone(dutyPhone);
                embassy.setCountry(country);

                this.embassyRepository.saveAndFlush(embassy);

                country.setEmbassy(embassy);
                this.countryRepository.saveAndFlush(country);
            }
        }
    }
}
