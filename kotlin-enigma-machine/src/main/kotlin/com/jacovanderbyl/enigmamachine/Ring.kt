package com.jacovanderbyl.enigmamachine

/**
 * Represents the ring setting on a rotor.
 *
 * From Wikipedia: The ring settings, or Ringstellung, are used to change the position of the alphabet ring relative
 * to the internal wiring. Notch and alphabet ring are fixed together. Changing the ring setting will therefore change
 * the positions of the wiring, relative to the turnover-point and start position.
 */
enum class Ring(val value: Int, val index: Int) {
    SETTING_1(1, 0),
    SETTING_2(2, 1),
    SETTING_3(3, 2),
    SETTING_4(4, 3),
    SETTING_5(5, 4),
    SETTING_6(6, 5),
    SETTING_7(7, 6),
    SETTING_8(8, 7),
    SETTING_9(9, 8),
    SETTING_10(10, 9),
    SETTING_11(11, 10),
    SETTING_12(12, 11),
    SETTING_13(13, 12),
    SETTING_14(14, 13),
    SETTING_15(15, 14),
    SETTING_16(16, 15),
    SETTING_17(17, 16),
    SETTING_18(18, 17),
    SETTING_19(19, 18),
    SETTING_20(20, 19),
    SETTING_21(21, 20),
    SETTING_22(22, 21),
    SETTING_23(23, 22),
    SETTING_24(24, 23),
    SETTING_25(25, 24),
    SETTING_26(26, 25);

    companion object {
        fun list() = entries
    }
}
