package com.zebra.felicadptest

object FelicaCommands {

    //06.00.FF.FF.01.00
    val pollingCommand = byteArrayOf(
        0x06.toByte(), // Length
        0x00.toByte(), // Command code
        0xFF.toByte(), 0xFF.toByte(), // System Code
        0x01.toByte(), // Request Code
        0x00.toByte()  // Time Slot
    )

    object DPTest {
        //04.FA.01.00.DD53
        val positiveEncodingCommand = byteArrayOf(
            0x04.toByte(), // Length
            0xFA.toByte(), // Command code
            0x01.toByte(), // System Code
            0x00.toByte(), // Request Code
            0xDD.toByte(), 0x53.toByte()  // CRC
        )

        //04.FA.01.FF.C3A3
        val negativeEncodingCommand = byteArrayOf(
            0x04.toByte(), // Length
            0xFA.toByte(), // Command code
            0x01.toByte(), // System Code
            0xFF.toByte(), // Request Code
            0xC3.toByte(), 0xA3.toByte()  // CRC
        )

        //06.00.FF.FF.01.03.0A73
        val command1 = byteArrayOf(
            0x06.toByte(), // Length
            0x00.toByte(), // Command code
            0xFF.toByte(), 0xFF.toByte(), // System Code
            0x01.toByte(), // Request Code
            0x03.toByte(),  // Time Slot
            0x0A.toByte(), 0x73.toByte()  // CRC
        )

        //06.00.FF.FF.01.00.3A10
        val command3 = byteArrayOf(
            0x06.toByte(), // Length
            0x00.toByte(), // Command code
            0xFF.toByte(), 0xFF.toByte(), // System Code
            0x01.toByte(), // Request Code
            0x00.toByte(), // Time Slot
            0x3A.toByte(), 0x10.toByte()  // CRC
        )

        //10.06.02.FE.11.22.33.44.05.06.01.0B.00.01.80.04.9E58
        val command5 = byteArrayOf(
            0x10.toByte(), // Length
            0x06.toByte(), // Command code
            0x02.toByte(), 0xFE.toByte(), // System Code
            0x11.toByte(), 0x22.toByte(), 0x33.toByte(), 0x44.toByte(), 0x05.toByte(), 0x06.toByte(), 0x01.toByte(), 0x0B.toByte(), 0x00.toByte(), 0x01.toByte(), 0x80.toByte(), // Request Code
            0x04.toByte(),  // Time Slot
            0x9E.toByte(), 0x58.toByte()  // CRC
        )

        //10.06.02.FE.11.22.33.44.05.06.01.0B.00.01.80.02.FE9E
        val command7 = byteArrayOf(
            0x10.toByte(), // Length
            0x06.toByte(), // Command code
            0x02.toByte(), 0xFE.toByte(), // System Code
            0x11.toByte(), 0x22.toByte(), 0x33.toByte(), 0x44.toByte(), 0x05.toByte(), 0x06.toByte(), 0x01.toByte(), 0x0B.toByte(), 0x00.toByte(), 0x01.toByte(), 0x80.toByte(), // Request Code
            0x02.toByte(),  // Time Slot
            0xFE.toByte(), 0x9E.toByte()  // CRC
        )

        //10.06.02.FE.11.22.33.44.05.06.01.0B.00.01.80.05.8E79
        val command9 = byteArrayOf(
            0x10.toByte(), // Length
            0x06.toByte(), // Command code
            0x02.toByte(), 0xFE.toByte(), // System Code
            0x11.toByte(), 0x22.toByte(), 0x33.toByte(), 0x44.toByte(), 0x05.toByte(), 0x06.toByte(), 0x01.toByte(), 0x0B.toByte(), 0x00.toByte(), 0x01.toByte(), 0x80.toByte(), // Request Code
            0x05.toByte(),  // Time Slot
            0x8E.toByte(), 0x79.toByte()  // CRC
        )

        //10.06.02.FE.11.22.33.44.05.06.01.0B.00.01.80.09.4FF5
        val command11 = byteArrayOf(
            0x10.toByte(), // Length
            0x06.toByte(), // Command code
            0x02.toByte(), 0xFE.toByte(), // System Code
            0x11.toByte(), 0x22.toByte(), 0x33.toByte(), 0x44.toByte(), 0x05.toByte(), 0x06.toByte(), 0x01.toByte(), 0x0B.toByte(), 0x00.toByte(), 0x01.toByte(), 0x80.toByte(), // Request Code
            0x09.toByte(),  // Time Slot
            0x4F.toByte(), 0xF5.toByte()  // CRC
        )
    }
}