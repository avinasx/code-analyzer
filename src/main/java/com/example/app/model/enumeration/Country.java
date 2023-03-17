package com.example.app.model.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Country {
    NONE(0, "-"),
    AFGHANISTAN(1, "Afghanistan"),
    ALGERIA(2, "Algeria"),
    AMERICAN_SAMOA(3, "American Samoa"),
    ANGOLA(4, "Angola"),
    ANGUILLA(5, "Anguilla"),
    ARGENTINA(6, "Argentina"),
    ARMENIA(7, "Armenia"),
    AUSTRALIA(8, "Australia"),
    AUSTRIA(9, "Austria"),
    AZERBAIJAN(10, "Azerbaijan"),
    BAHRAIN(11, "Bahrain"),
    BANGLADESH(12, "Bangladesh"),
    BELARUS(13, "Belarus"),
    BOLIVIA(14, "Bolivia"),
    BRAZIL(15, "Brazil"),
    BRUNEI(16, "Brunei"),
    BULGARIA(17, "Bulgaria"),
    CAMBODIA(18, "Cambodia"),
    CAMEROON(19, "Cameroon"),
    CANADA(20, "Canada"),
    CHAD(21, "Chad"),
    CHILE(22, "Chile"),
    CHINA(23, "China"),
    COLOMBIA(24, "Colombia"),
    CONGO_THE_DEMOCRATIC_REPUBLIC_OF_THE(25, "Congo, The Democratic Republic of the"),
    CZECH_REPUBLIC(26, "Czech Republic"),
    DOMINICAN_REPUBLIC(27, "Dominican Republic"),
    ECUADOR(28, "Ecuador"),
    EGYPT(29, "Egypt"),
    ESTONIA(30, "Estonia"),
    ETHIOPIA(31, "Ethiopia"),
    FAROE_ISLANDS(32, "Faroe Islands"),
    FINLAND(33, "Finland"),
    FRANCE(34, "France"),
    FRENCH_GUIANA(35, "French Guiana"),
    FRENCH_POLYNESIA(36, "French Polynesia"),
    GAMBIA(37, "Gambia"),
    GERMANY(38, "Germany"),
    GREECE(39, "Greece"),
    GREENLAND(40, "Greenland"),
    HOLY_SEE_VATICAN_CITY_STATE(41, "Holy See (Vatican City State)"),
    HONG_KONG(42, "Hong Kong"),
    HUNGARY(43, "Hungary"),
    INDIA(44, "India"),
    INDONESIA(45, "Indonesia"),
    IRAN(46, "Iran"),
    IRAQ(47, "Iraq"),
    ISRAEL(48, "Israel"),
    ITALY(49, "Italy"),
    JAPAN(50, "Japan"),
    KAZAKHSTAN(51, "Kazakstan"),
    KENYA(52, "Kenya"),
    KUWAIT(53, "Kuwait"),
    LATVIA(54, "Latvia"),
    LIECHTENSTEIN(55, "Liechtenstein"),
    LITHUANIA(56, "Lithuania"),
    MADAGASCAR(57, "Madagascar"),
    MALAWI(58, "Malawi"),
    MALAYSIA(59, "Malaysia"),
    MEXICO(60, "Mexico"),
    MOLDOVA(61, "Moldova"),
    MOROCCO(62, "Morocco"),
    MOZAMBIQUE(63, "Mozambique"),
    MYANMAR(64, "Myanmar"),
    NAURU(65, "Nauru"),
    NEPAL(66, "Nepal"),
    NETHERLANDS(67, "Netherlands"),
    NEW_ZEALAND(68, "New Zealand"),
    NIGERIA(69, "Nigeria"),
    NORTH_KOREA(70, "North Korea"),
    OMAN(71, "Oman"),
    PAKISTAN(72, "Pakistan"),
    PARAGUAY(73, "Paraguay"),
    PERU(74, "Peru"),
    PHILIPPINES(75, "Philippines"),
    POLAND(76, "Poland"),
    PUERTO_RICO(77, "Puerto Rico"),
    ROMANIA(78, "Romania"),
    REUNION(79, "Réunion"),
    RUSSIAN_FEDERATION(80, "Russian Federation"),
    SAINT_VINCENT_AND_THE_GRENADINES(81, "Saint Vincent and the Grenadines"),
    SAUDI_ARABIA(82, "Saudi Arabia"),
    SENEGAL(83, "Senegal"),
    SLOVAKIA(84, "Slovakia"),
    SOUTH_AFRICA(85, "South Africa"),
    SOUTH_KOREA(86, "South Korea"),
    SPAIN(87, "Spain"),
    SRI_LANKA(88, "Sri Lanka"),
    SUDAN(89, "Sudan"),
    SWEDEN(90, "Sweden"),
    SWITZERLAND(91, "Switzerland"),
    TAIWAN(92, "Taiwan"),
    TANZANIA(93, "Tanzania"),
    THAILAND(94, "Thailand"),
    TONGA(95, "Tonga"),
    TUNISIA(96, "Tunisia"),
    TURKEY(97, "Turkey"),
    TURKMENISTAN(98, "Turkmenistan"),
    TUVALU(99, "Tuvalu"),
    UKRAINE(100, "Ukraine"),
    UNITED_ARAB_EMIRATES(101, "United Arab Emirates"),
    UNITED_KINGDOM(102, "United Kingdom"),
    UNITED_STATES(103, "United States"),
    VENEZUELA(104, "Venezuela"),
    VIETNAM(105, "Vietnam"),
    VIRGIN_ISLANDS_US(106, "Virgin Islands, U.S."),
    YEMEN(107, "Yemen"),
    YUGOSLAVIA(108, "Yugoslavia"),
    ZAMBIA(109, "Zambia");

    private final Integer id;
    private final String country;

    public static Country getCountryById(Integer id) {
        return Objects.requireNonNull(Stream.of(Country.values())
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null));
    }

    /**
     * Replace last country id in string to country name.
     * @param str string with country id
     * @return string with country name
     */
    public static String replaceCountryIdToCountryInString(String str) {
        var pattern = Pattern.compile("\\d+$");
        var matcher = pattern.matcher(str);
        if (matcher.find()) {
            var lastElement = matcher.group();
            var country = getCountryById(Integer.parseInt(lastElement));
            str = str.replaceAll("\\d+$", country.getCountry());
        }
        return str;
    }

    @JsonValue
    public String getCountry() {
        return country;
    }
}
