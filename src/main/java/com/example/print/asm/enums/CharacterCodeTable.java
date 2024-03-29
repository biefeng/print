package com.example.print.asm.enums;

public enum CharacterCodeTable {

    PC437(0),
    USA(0),
    STANDARD_EUROPE(0),
    KATAKANA(1),
    PC850(2),
    MULTILINGUAL(2),
    PC860(3),
    PORTUGUESE(3),
    PC863(4),
    CANADIAN_FRENCH(4),
    PC865(5),
    NORDIC(5),
    HIRAGANA(6),
    KANJI_1(7),
    KANJI_2(8),
    PC851(11),
    GREEK_1(11),
    PC853(12),
    TURKISH_1(12),
    PC857(13),
    TURKISH_2(13),
    PC737(14),
    GREEK_2(14),
    ISO8859_7(15),
    GREEK_3(15),
    WPC1252(16),
    PC866(17),
    CYRILLIC_2(17),
    PC852(18),
    LATIN_2(18),
    PC858(19),
    EURO(19),
    THAI_42(20),
    THAI_11(21),
    THAI_13(22),
    THAI_14(23),
    THAI_16(24),
    THAI_17(25),
    THAI_18(26),
    TCVN_3_1(30),
    VIETNAMESE_1(30),
    TCVN_3_2(31),
    VIETNAMESE_2(31),
    PC720(32),
    ARABIC(32),
    WPC775(33),
    BALTIC_RIM(33),
    PC855(34),
    CYRILLIC(34),
    PC861(35),
    ICELANDIC(35),
    PC862(36),
    HEBREW(36),
    PC864(37),
    ARABIC_2(37),
    PC869(38),
    GREEK_4(38),
    ISO8859_2(39),
    ISO8859_15(40),
    PC1098(41),
    FARSI(41),
    PC1118(42),
    PC1119(43),
    PC1125(44),
    WPC1250(45),
    WPC1251(46),
    WPC1253(47),
    WPC1254(48),
    WPC1255(49),
    WPC1256(50),
    WPC1257(51),
    WPC1258(52),
    KZ_1048(53),
    DEVANAGARI(66),
    BENGALI(67),
    TAMIL(68),
    TELUGU(69),
    ASSAMESE(70),
    ORIYA(71),
    KANNADA(72),
    MALAYALAM(73),
    GUJARATI(74),
    PUNJABI(75),
    MARATHI(82),
    PAGE_254(254),
    PAGE_255(255);

    public final int code;

    CharacterCodeTable(int code) {
        this.code = code;
    }

}
