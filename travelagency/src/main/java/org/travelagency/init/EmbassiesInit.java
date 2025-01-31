package org.travelagency.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.Country;
import org.travelagency.model.entity.Embassy;
import org.travelagency.model.enums.CountryName;
import org.travelagency.repository.CountryRepository;
import org.travelagency.repository.EmbassyRepository;

import java.util.Arrays;
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

        if (this.embassyRepository.count() == 0) {

            Arrays.stream(CountryName.values())
                    .forEach(countryName -> {
                        Embassy embassy = new Embassy();
                        Optional<Country> optionalCountry = this.countryRepository.findCountryByCountryName(countryName);

                        if (optionalCountry.isPresent()) {
                            Country country = optionalCountry.get();

                            switch (countryName) {
                                case ALBANIA -> {
                                    embassy.setAddress("Rruga Skenderbeu 12, Tirana, Albania");
                                    embassy.setPhoneNumber("+355 4 22 32 906");
                                    embassy.setFax("+355 4 22 32 272");
                                    embassy.setEmail("Embassy.Tirana@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/albania");
                                    embassy.setDutyPhone("+355 4 22 33 155");
                                    embassy.setCountry(country);
                                }
                                case ENGLAND -> {
                                    embassy.setAddress("186-188 Queen’s Gate, London SW7 5HL, UK");
                                    embassy.setPhoneNumber("+44 20 7581 3144");
                                    embassy.setFax("+44 20 7584 4948");
                                    embassy.setEmail("embassy.london@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/uk");
                                    embassy.setDutyPhone("+442075849400");
                                    embassy.setCountry(country);
                                }
                                case BELGIUM -> {
                                    embassy.setAddress("Avenue Moscicki 7, 1180 Bruxelles");
                                    embassy.setPhoneNumber("+32 2 374 47 88");
                                    embassy.setFax("+32 2 374 08 66");
                                    embassy.setEmail("embassy.brussels@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/belgium");
                                    embassy.setDutyPhone("+32 473 981 042");
                                    embassy.setCountry(country);
                                }
                                case BOSNIA_AND_HERZEGOVINA -> {
                                    embassy.setAddress("71000 Sarajevo, 30 Radnička Str., BiH");
                                    embassy.setPhoneNumber("+387 33 879 955/956");
                                    embassy.setFax("+387 33 879 958");
                                    embassy.setEmail("Embassy.Sarajevo@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/bosniaherzegovina");
                                    embassy.setDutyPhone("+387 61599493");
                                    embassy.setCountry(country);
                                }
                                case GERMANY -> {
                                    embassy.setAddress("Mauerstrasse 11, 10117 Berlin");
                                    embassy.setPhoneNumber("+49 30 201 09 22");
                                    embassy.setFax("+49 30 208 68 38");
                                    embassy.setEmail("Embassy.Berlin@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/germany");
                                    embassy.setDutyPhone("+49 30 201 09 22");
                                    embassy.setCountry(country);
                                }
                                case GREECE -> {
                                    embassy.setAddress("33 Α, Stratigou Kallari Str., 154 52 Paleo Psychiκo, Athens, Greece");
                                    embassy.setPhoneNumber("+30 210 67 48 106");
                                    embassy.setFax("+30 210 67 48 130");
                                    embassy.setEmail("embassy.athens@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/greece");
                                    embassy.setDutyPhone("+30 210 67 48 107");
                                    embassy.setCountry(country);
                                }
                                case DENMARK -> {
                                    embassy.setAddress("Gamlehave Alle 7, 2920 Charlottenlund, Copenhagen, Kingdom of Denmark");
                                    embassy.setPhoneNumber("+45 39 64 24 84");
                                    embassy.setFax("+45 39 63 49 23");
                                    embassy.setEmail("Embassy.Copenhagen@mfa.bg ");
                                    embassy.setWebpage("www.mfa.bg/embassies/denmark");
                                    embassy.setDutyPhone("+45 41 276251");
                                    embassy.setCountry(country);
                                }
                                case IRELAND -> {
                                    embassy.setAddress("22 Burlington Road, Ballsbridge, Dublin 4");
                                    embassy.setPhoneNumber("+353 1 660 32 93");
                                    embassy.setFax("+353 1 660 3915");
                                    embassy.setEmail("Embassy.Dublin@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/greece");
                                    embassy.setDutyPhone("+353 876 830 566");
                                    embassy.setCountry(country);
                                }
                                case ICELAND -> {
                                    embassy.setAddress("Tidemands gate 11, 02 44 Oslo");
                                    embassy.setPhoneNumber("+47 22 55 40 40");
                                    embassy.setFax("+47 22 55 40 40");
                                    embassy.setEmail("Embassy.Oslo@mfa.bg ");
                                    embassy.setWebpage("www.mfa.bg/embassies/norway");
                                    embassy.setDutyPhone("+47 480 89 793");
                                    embassy.setCountry(country);
                                }
                                case SPAIN -> {
                                    embassy.setAddress("Travesía de Santa María Magdalena 15, 28016 Madrid");
                                    embassy.setPhoneNumber("+34 91 345 5761");
                                    embassy.setFax("+34 91 345 6651");
                                    embassy.setEmail("Embassy.Madrid@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/spain");
                                    embassy.setDutyPhone("+34 678 013 846");
                                    embassy.setCountry(country);
                                }
                                case ITALY -> {
                                    embassy.setAddress("Via Pietro Paolo Rubens, 21, 00197 ROMA");
                                    embassy.setPhoneNumber("+39 06 322 46 40");
                                    embassy.setFax("+39 06 322 61 22");
                                    embassy.setEmail("Embassy.Rome@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/italy");
                                    embassy.setDutyPhone("+39 349 492 7764");
                                    embassy.setCountry(country);
                                }
                                case CYPRUS -> {
                                    embassy.setAddress("13, Konstantinou Paleologou, 2406, Engomi, Nicosia");
                                    embassy.setPhoneNumber("+357 22 67 24 86");
                                    embassy.setFax("+357 22 67 65 98");
                                    embassy.setEmail("Embassy.Nicosia@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/cyprus");
                                    embassy.setDutyPhone("+357 22 67 24 86");
                                    embassy.setCountry(country);
                                }
                                case NORTH_MACEDONIA -> {
                                    embassy.setAddress("Zlatko Shnajder, № 3, Skopje");
                                    embassy.setPhoneNumber("+389 2 3229 444");
                                    embassy.setFax("+389 2 3246 493");
                                    embassy.setEmail("Embassy.Skopje@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/macedonia");
                                    embassy.setDutyPhone("+389  75 280 013");
                                    embassy.setCountry(country);
                                }
                                case NORWAY -> {
                                    embassy.setAddress("Tidemands gate 11, 02 44 Oslo");
                                    embassy.setPhoneNumber("+47 22 55 40 40");
                                    embassy.setFax("+47 22 55 40 40");
                                    embassy.setEmail("Embassy.Oslo@mfa.bg ");
                                    embassy.setWebpage("www.mfa.bg/embassies/norway");
                                    embassy.setDutyPhone("+47 480 89 793");
                                    embassy.setCountry(country);
                                }
                                case ROMANIA -> {
                                    embassy.setAddress("Bucuresti, sector 1, str. Rabat  nr. 5");
                                    embassy.setPhoneNumber("+40 21 230 21 50");
                                    embassy.setFax("+40 21 230 21 59");
                                    embassy.setEmail("Embassy.Bucharest@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/romania");
                                    embassy.setDutyPhone("+40 21 230 21 50");
                                    embassy.setCountry(country);
                                }
                                case SLOVAKIA -> {
                                    embassy.setAddress("Kuzmanyho str.1, 811 06 Bratislava, Slovak Republic");
                                    embassy.setPhoneNumber("(00421 2) 544-15-308");
                                    embassy.setFax("(00421 2) 544-12-404");
                                    embassy.setEmail("Embassy.Bratislava@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/slovakia");
                                    embassy.setDutyPhone("(00421)911-284-229");
                                    embassy.setCountry(country);
                                }
                                case SLOVENIA -> {
                                    embassy.setAddress("№ 9а/ Slovenia, 1000 Ljubljana, Stara Ježica 9a");
                                    embassy.setPhoneNumber("+386 1 28 32 899");
                                    embassy.setFax("+386 1 28 32 901");
                                    embassy.setEmail("Embassy.Ljubljana@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/slovenia");
                                    embassy.setDutyPhone("+ 386 41 722 238");
                                    embassy.setCountry(country);
                                }
                                case SERBIA -> {
                                    embassy.setAddress("Belgrade, Bircaninovastr. No. 26");
                                    embassy.setPhoneNumber("00381 11 3613980");
                                    embassy.setFax("00381 11 3620116");
                                    embassy.setEmail("Embassy.Belgrade@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/serbia");
                                    embassy.setDutyPhone("+381 11 3613980");
                                    embassy.setCountry(country);
                                }
                                case TURKEY -> {
                                    embassy.setAddress("Atatürk Bulvarı № 124, 06680 Kavıklıdere-Çankaya, Ankara, Republic of Türkiye");
                                    embassy.setPhoneNumber("(+90312) 467-20-71");
                                    embassy.setFax("467-25-74");
                                    embassy.setEmail("Embassy.Ankara@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/turkey");
                                    embassy.setDutyPhone("(+90312) 467-20-71");
                                    embassy.setCountry(country);
                                }
                                case HUNGARY -> {
                                    embassy.setAddress("Andrassy ut. 115, 1062 Budapest, Hungary");
                                    embassy.setPhoneNumber("+36 1 322 08 24");
                                    embassy.setFax("+36 1 322 52 15");
                                    embassy.setEmail("Embassy.Budapest@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/hungary");
                                    embassy.setDutyPhone("+36 20 534 4559");
                                    embassy.setCountry(country);
                                }
                                case FRANCE -> {
                                    embassy.setAddress("Ambassade de Bulgarie; 1, avenue Rapp; 75007 Paris");
                                    embassy.setPhoneNumber("+33 1 45 51 85 90");
                                    embassy.setFax("+33 1 45 56 97 50");
                                    embassy.setEmail("Embassy.Paris@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/france");
                                    embassy.setDutyPhone("+33 6 21 84 95 15");
                                    embassy.setCountry(country);
                                }
                                case NETHERLANDS -> {
                                    embassy.setAddress("The Netherlands, The Hague, Duinroosweg 9, 2597 KJ, Den Haag");
                                    embassy.setPhoneNumber("+31 70 358 46 88");
                                    embassy.setFax("+31 70 350 30 51");
                                    embassy.setEmail("Embassy.Hague@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/netherlands");
                                    embassy.setDutyPhone("+31686213612");
                                    embassy.setCountry(country);
                                }
                                case CROATIA -> {
                                    embassy.setAddress("15 Dvorničićeva str., 10000 Zagreb, Croatia");
                                    embassy.setPhoneNumber("+385 1 2755095");
                                    embassy.setFax("+385 1 2755095");
                                    embassy.setEmail("Embassy.Zagreb@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/croatia");
                                    embassy.setDutyPhone("+385 984 69 808");
                                    embassy.setCountry(country);
                                }
                                case MONTENEGRO -> {
                                    embassy.setAddress("Bul. Ivana Crnojevića 99/2, 81000 Podgorica");
                                    embassy.setPhoneNumber("+382 20 655 009");
                                    embassy.setFax("+382 20 655 008");
                                    embassy.setEmail("Embassy.Podgorica@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/montenegro");
                                    embassy.setDutyPhone("+382 69 133 616");
                                    embassy.setCountry(country);
                                }
                                case CZECH_REPUBLIC -> {
                                    embassy.setAddress("");
                                    embassy.setPhoneNumber("00420 222-212-011");
                                    embassy.setFax("00420 222-211-728");
                                    embassy.setEmail("embassy.prague@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/czech");
                                    embassy.setDutyPhone("+ 420 774 790 991");
                                    embassy.setCountry(country);
                                }
                                case SWITZERLAND -> {
                                    embassy.setAddress("Bernastrasse 2, CH-3005 Bern");
                                    embassy.setPhoneNumber("+41 31 351 14 55");
                                    embassy.setFax("+41 31 351 00 64");
                                    embassy.setEmail("Embassy.Bern@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/switzerland");
                                    embassy.setDutyPhone("+41 79 824 38 22");
                                    embassy.setCountry(country);
                                }
                                case VIETNAM -> {
                                    embassy.setAddress("No. 5, Lane 294 Kim Ma Street, Ba Dinh District, Hanoi, Vietnam");
                                    embassy.setPhoneNumber("+84 24 3845 2908");
                                    embassy.setFax("+84 24 3846 0856");
                                    embassy.setEmail("Embassy.Hanoi@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/vietnam");
                                    embassy.setDutyPhone("+84 83 940 9129");
                                    embassy.setCountry(country);
                                }
                                case UNITED_ARAB_EMIRATES -> {
                                    embassy.setAddress("Al-Nahayan Camp Area, Jafn Street, Villa 6, Abu Dhabi, P.O.Box 73541");
                                    embassy.setPhoneNumber("00971 2 6443381");
                                    embassy.setFax("00971 2 6443383");
                                    embassy.setEmail("Embassy.Abu.Dhabi@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/uae");
                                    embassy.setDutyPhone("00971 2 6443384");
                                    embassy.setCountry(country);
                                }
                                case INDIA -> {
                                    embassy.setAddress("16/17 Chandragupta Marg, Chanakyapuri, New Delhi – 110021");
                                    embassy.setPhoneNumber("+911126115550");
                                    embassy.setFax("+911126876190");
                                    embassy.setEmail("Embassy.Delhi@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/india");
                                    embassy.setDutyPhone("+911126115550");
                                    embassy.setCountry(country);
                                }
                                case INDONESIA -> {
                                    embassy.setAddress("Jakarta 10310, 34-36, Jl. Imam Bonjol");
                                    embassy.setPhoneNumber("+62 21 390 40 48");
                                    embassy.setFax("+62 21 390 40 49");
                                    embassy.setEmail("embassy.jakarta@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/indonesia");
                                    embassy.setDutyPhone("+62 81296000691");
                                    embassy.setCountry(country);
                                }
                                case IRAN -> {
                                    embassy.setAddress("Vali-ye Asr Ave, Tavanir Ave, Nezami-ye Ganjavi Str. 40, Tehran");
                                    embassy.setPhoneNumber("+98 21 8877 5662");
                                    embassy.setFax("+98 21 8877 9680");
                                    embassy.setEmail("Embassy.Tehran@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/iran");
                                    embassy.setDutyPhone("+98 930 568 34 09");
                                    embassy.setCountry(country);
                                }
                                case CHINA -> {
                                    embassy.setAddress("Xiu Shui Bei Jie 4, Beijing - 100600, China");
                                    embassy.setPhoneNumber("+86 10 6532 1916");
                                    embassy.setFax("+86 10 6532 4502");
                                    embassy.setEmail("Embassy.Beijing@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/china");
                                    embassy.setDutyPhone("+86 156 4658 8195");
                                    embassy.setCountry(country);
                                }
                                case MALDIVES -> {
                                    embassy.setAddress("16/17 Chandragupta Marg, Chanakyapuri, New Delhi – 110021");
                                    embassy.setPhoneNumber("+911126115550");
                                    embassy.setFax("+911126876190");
                                    embassy.setEmail("Embassy.Delhi@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/india");
                                    embassy.setDutyPhone("+911126115550");
                                    embassy.setCountry(country);
                                }
                                case THAILAND -> {
                                    embassy.setAddress("No. 5, Lane 294 Kim Ma Street, Ba Dinh District, Hanoi, Vietnam");
                                    embassy.setPhoneNumber("+84 24 3845 2908");
                                    embassy.setFax("+84 24 3846 0856");
                                    embassy.setEmail("Embassy.Hanoi@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/vietnam");
                                    embassy.setDutyPhone("+84 83 940 9129");
                                    embassy.setCountry(country);
                                }
                                case SRI_LANKA -> {
                                    embassy.setAddress("16/17 Chandragupta Marg, Chanakyapuri, New Delhi – 110021");
                                    embassy.setPhoneNumber("+911126115550");
                                    embassy.setFax("+911126876190");
                                    embassy.setEmail("Embassy.Delhi@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/india");
                                    embassy.setDutyPhone("+911126115550");
                                    embassy.setCountry(country);
                                }
                                case JAPAN -> {
                                    embassy.setAddress("Yoyogi 5-36-3, Shibuya-ku, Tokyo 151-0053");
                                    embassy.setPhoneNumber("+81 3 3465 1021");
                                    embassy.setFax("+81 3 3465 1031");
                                    embassy.setEmail("Embassy.Tokyo@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/japan");
                                    embassy.setDutyPhone("+81 80 7966 5608");
                                    embassy.setCountry(country);
                                }
                                case EGYPT -> {
                                    embassy.setAddress("6, El Malek Al Afdal str., Zamalek 11211, Cairo, Arab Republic of Egypt");
                                    embassy.setPhoneNumber("+202 273 63 025");
                                    embassy.setFax("+202 273 63 826");
                                    embassy.setEmail("Embassy.Cairo@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/egypt");
                                    embassy.setDutyPhone("+20 12 231 71 991");
                                    embassy.setCountry(country);
                                }
                                case MAURITIUS -> {
                                    embassy.setAddress("1071 Stanza Bopape str.0083 Hatfield, Pretoria, Republic of South Africa");
                                    embassy.setPhoneNumber("+27 12 342 37 20");
                                    embassy.setFax("+2712 342 37 21");
                                    embassy.setEmail("Embassy.Pretoria@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/southafrica");
                                    embassy.setDutyPhone("+27 79 893 7336");
                                    embassy.setCountry(country);
                                }
                                case SEYCHELLES -> {
                                    embassy.setAddress("Bole KK, Kebele 04/06/07, Addis Ababa, Ethiopia, P.O.Box 987");
                                    embassy.setPhoneNumber("00251 – 911252514");
                                    embassy.setFax("00251 – 911252514");
                                    embassy.setEmail("bul.addis@gmail.com");
                                    embassy.setWebpage("www.mfa.bg/embassies/ethiopia");
                                    embassy.setDutyPhone("00251 – 911252514");
                                    embassy.setCountry(country);
                                }
                                case TUNISIA -> {
                                    embassy.setAddress("5 Rue Ryhane, Cité Mahrajène, 1082 Tunis");
                                    embassy.setPhoneNumber("+216 71 798 962");
                                    embassy.setFax("+216 71 800 980");
                                    embassy.setEmail("Embassy.Tunis@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/tunisia");
                                    embassy.setDutyPhone("+216 55 813 135");
                                    embassy.setCountry(country);
                                }
                                case SOUTH_AFRICA -> {
                                    embassy.setAddress("1071 Stanza Bopape str.0083 Hatfield, Pretoria, Republic of South Africa");
                                    embassy.setPhoneNumber("+27 12 342 37 20");
                                    embassy.setFax("+2712 342 37 21");
                                    embassy.setEmail("Embassy.Pretoria@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/southafrica");
                                    embassy.setDutyPhone("+27 79 893 7336");
                                    embassy.setCountry(country);
                                }
                                case DOMINICAN_REPUBLIC -> {
                                    embassy.setAddress(" 5-ta Avenida № 6407, esquina calle 66, Miramar, Playa, Ciudad de La Habana");
                                    embassy.setPhoneNumber("+537 204 67 66");
                                    embassy.setFax("+537 204 67 66");
                                    embassy.setEmail("Embassy.Havana@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/kuba");
                                    embassy.setDutyPhone("+535 286 24 49");
                                    embassy.setCountry(country);
                                }
                                case CANADA -> {
                                    embassy.setAddress("325 Stewart Street, Ottawa, Ontario, K1N 6K5, Canada");
                                    embassy.setPhoneNumber("+1 613 789 3215");
                                    embassy.setFax("+1 613 7893524");
                                    embassy.setEmail("Embassy.Ottawa@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/canada");
                                    embassy.setDutyPhone("+1 613 316 1631");
                                    embassy.setCountry(country);
                                }
                                case USA -> {
                                    embassy.setAddress("1621 22nd Street, NW, Dimitar Peshev Plaza, Washington, DC 20008, USA");
                                    embassy.setPhoneNumber("+1 202 387 0174");
                                    embassy.setFax("+1 202 234 7973");
                                    embassy.setEmail("Embassy.Washington@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/usa");
                                    embassy.setDutyPhone("+1 (202) 387-5770");
                                    embassy.setCountry(country);
                                }
                                case ARGENTINA -> {
                                    embassy.setAddress("Sucre 1568, C1428DUT, Ciudad Autónoma de Buenos Aires, Argentina");
                                    embassy.setPhoneNumber("+54 11 4781 8644");
                                    embassy.setFax("+54 11 4786 6273");
                                    embassy.setEmail("bg.buenosaires@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/argentina");
                                    embassy.setDutyPhone("+54 9 11 3874 3170");
                                    embassy.setCountry(country);
                                }
                                case BRAZIL -> {
                                    embassy.setAddress("SEN 803, Asa Norte, CEP: 70800-911, Brasilia, DF, Brasil");
                                    embassy.setPhoneNumber("+55 61 32236193");
                                    embassy.setFax("+55 61 33233285");
                                    embassy.setEmail("Embassy.Brasilia@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/brazil");
                                    embassy.setDutyPhone("+55 61 32239849");
                                    embassy.setCountry(country);
                                }
                                case ECUADOR_AND_GALAPAGOS_ISLANDS -> {
                                    embassy.setAddress("SEN 803, Asa Norte, CEP: 70800-911, Brasilia, DF, Brasil");
                                    embassy.setPhoneNumber("+55 61 32236193");
                                    embassy.setFax("+55 61 33233285");
                                    embassy.setEmail("Embassy.Brasilia@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/brazil");
                                    embassy.setDutyPhone("+55 61 32239849");
                                    embassy.setCountry(country);
                                }
                                case COLOMBIA -> {
                                    embassy.setAddress("SEN 803, Asa Norte, CEP: 70800-911, Brasilia, DF, Brasil");
                                    embassy.setPhoneNumber("+55 61 32236193");
                                    embassy.setFax("+55 61 33233285");
                                    embassy.setEmail("Embassy.Brasilia@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/brazil");
                                    embassy.setDutyPhone("+55 61 32239849");
                                    embassy.setCountry(country);
                                }
                                case CUBA -> {
                                    embassy.setAddress(" 5-ta Avenida № 6407, esquina calle 66, Miramar, Playa, Ciudad de La Habana");
                                    embassy.setPhoneNumber("+537 204 67 66");
                                    embassy.setFax("+537 204 67 66");
                                    embassy.setEmail("Embassy.Havana@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/kuba");
                                    embassy.setDutyPhone("+535 286 24 49");
                                    embassy.setCountry(country);
                                }
                                case AUSTRALIA -> {
                                    embassy.setAddress("29 Pindari Crescent, O’Malley, ACT 2606");
                                    embassy.setPhoneNumber("+612 62869700");
                                    embassy.setFax("+612 62869600");
                                    embassy.setEmail("Embassy.Canberra@mfa.bg");
                                    embassy.setWebpage("www.mfa.bg/embassies/australia");
                                    embassy.setDutyPhone("+61 420 744 840");
                                    embassy.setCountry(country);
                                }
                            }

                            this.embassyRepository.saveAndFlush(embassy);
                            country.setEmbassy(embassy);
                            this.countryRepository.saveAndFlush(country);
                        }
                    });
        }
    }
}
