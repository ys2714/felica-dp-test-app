package com.zebra.felicadptest

object FelicaResponse {

    object DPTest {

        //14.01.02.FE.01.02.03.04.05.66.FF.FF.FF.FF.FF.FF.FF.EE.12.FC.C59F
        val response2 = byteArrayOf(
            0x14.toByte(), // Length
            0x01.toByte(), // Command code
            0x02.toByte(), 0xFE.toByte(), // System Code
            0x01.toByte(), 0x02.toByte(), 0x03.toByte(), 0x04.toByte(), 0x05.toByte(), 0x66.toByte(), // Request Code
            0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), // Request Code
            0xEE.toByte(), 0x12.toByte(), 0xFC.toByte(), // Request Code
            0xC5.toByte(), 0x9F.toByte()  // CRC
        )

        //14.01.02.FE.11.22.33.44.05.06.FF.FF.FF.FF.FF.AB.CD.FF.12.FC.BD20
        val response4 = byteArrayOf(
            0x14.toByte(), // Length
            0x01.toByte(), // Command code
            0x02.toByte(), 0xFE.toByte(), // System Code
            0x11.toByte(), 0x22.toByte(), 0x33.toByte(), 0x44.toByte(), 0x05.toByte(), 0x06.toByte(), // Request Code
            0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), // Request Code
            0xAB.toByte(), 0xCD.toByte(), 0xFF.toByte(), 0x12.toByte(), 0xFC.toByte(), // Request Code
            0xBD.toByte(), 0x20.toByte()  // CRC
        )

        //1D.07.02.FE.11.22.33.44.05.06.00.00.01.44.44.44.44.44.44.44.44.44.44.44.44.44.44.44.44.6889
        val response6 = byteArrayOf(
            0x1D.toByte(), // Length
            0x07.toByte(), // Command code
            0x02.toByte(), 0xFE.toByte(), // System Code
            0x11.toByte(), 0x22.toByte(), 0x33.toByte(), 0x44.toByte(), 0x05.toByte(), 0x06.toByte(), // Request Code
            0x00.toByte(), 0x00.toByte(), 0x01.toByte(),
            0x44.toByte(), 0x44.toByte(), 0x44.toByte(), 0x44.toByte(),
            0x44.toByte(), 0x44.toByte(), 0x44.toByte(), 0x44.toByte(),
            0x44.toByte(), 0x44.toByte(), 0x44.toByte(), 0x44.toByte(),
            0x44.toByte(), 0x44.toByte(), 0x44.toByte(), 0x44.toByte(),
            0x68.toByte(), 0x89.toByte()  // CRC
        )

        //1D.0702FE11223344050600000122222222222222222222222222222222.98BC
        val response8 = byteArrayOf(
            0x14.toByte(), // Length
            0x01.toByte(), // Command code
            0x02.toByte(), 0xFE.toByte(), // System Code
            0x01.toByte(), 0x02.toByte(), 0x03.toByte(), 0x04.toByte(), 0x05.toByte(), 0x66.toByte(), // Request Code
            0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), // Request Code
            0xEE.toByte(), 0x12.toByte(), 0xFC.toByte(), // Request Code
            0xC5.toByte(), 0x9F.toByte()  // CRC
        )

        //1D.0702FE11223344050600000155555555555555555555555555555555.388F
        val response10 = byteArrayOf(
            0x14.toByte(), // Length
            0x01.toByte(), // Command code
            0x02.toByte(), 0xFE.toByte(), // System Code
            0x01.toByte(), 0x02.toByte(), 0x03.toByte(), 0x04.toByte(), 0x05.toByte(), 0x66.toByte(), // Request Code
            0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), // Request Code
            0xEE.toByte(), 0x12.toByte(), 0xFC.toByte(), // Request Code
            0xC5.toByte(), 0x9F.toByte()  // CRC
        )

        //1D.0702FE11223344050600000199999999999999999999999999999999.C8C4
        val response12 = byteArrayOf(
            0x14.toByte(), // Length
            0x01.toByte(), // Command code
            0x02.toByte(), 0xFE.toByte(), // System Code
            0x01.toByte(), 0x02.toByte(), 0x03.toByte(), 0x04.toByte(), 0x05.toByte(), 0x66.toByte(), // Request Code
            0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), // Request Code
            0xEE.toByte(), 0x12.toByte(), 0xFC.toByte(), // Request Code
            0xC5.toByte(), 0x9F.toByte()  // CRC
        )
    }
}