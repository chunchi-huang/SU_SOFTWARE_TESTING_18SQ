package us.lindanrandy.cidrcalculator;

import org.junit.After;
import org.junit.Before;
//import org.junit.Rule;
import org.junit.Test;
//import org.junit.rules.ExpectedException;

import java.net.InetAddress;

//import static org.hamcrest.EasyMock2Matchers.equalTo;
import static org.junit.Assert.*;

public class CIDRCalculatorUnitTesting {

    @Before
    public void setUp() throws Exception {
        //System.out.println("Preparing for CIDRCalculatorUnitTesting");
    }

    @After
    public void tearDown() throws Exception {
        //System.out.println("Completed CIDRCalculatorUnitTesting");
    }

    @Test
    public void stringIPtoInt_192_168_1_1() throws Exception {
        //arrange
        String inputIP = "192.168.1.1";
        int expectedOutput = -1062731519;
        //action
        int actualOutput = CIDRCalculator.stringIPtoInt(inputIP);
        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test (expected = Exception.class)
    public void stringIPtoInt_192_168_1_() throws Exception {
        //arrange
        String inputIP = "192.168.1";
        //action
        int actualOutput = CIDRCalculator.stringIPtoInt(inputIP);
        //assert
        // use  ""(expected = Exception.class) to catch an exception
    }

    @Test (expected = Exception.class)
    public void stringIPtoInt_192_168__1() throws Exception {
        //arrange
        String inputIP = "192.168..1";
        //action
        int actualOutput = CIDRCalculator.stringIPtoInt(inputIP);
        //assert
        // use  ""(expected = Exception.class) to catch an exception
    }

    @Test (expected = Exception.class)
    public void stringIPtoInt_192_168_1_256() throws Exception {
        //arrange
        String inputIP = "192.168.1.256";
        //action
        int actualOutput = CIDRCalculator.stringIPtoInt(inputIP);
        //assert
        // use  ""(expected = Exception.class) to catch an exception
    }

    @Test (expected = Exception.class)
    public void stringIPtoInt_192_168_1_N1() throws Exception {
        //arrange
        String inputIP = "192.168.1.-1";
        //action
        int actualOutput = CIDRCalculator.stringIPtoInt(inputIP);
        //assert
        // use  ""(expected = Exception.class) to catch an exception
    }

    @Test (expected = Exception.class)
    public void stringIPtoInt_192_168_1_C() throws Exception {
        //arrange
        String inputIP = "192.168.1.,";
        //action
        int actualOutput = CIDRCalculator.stringIPtoInt(inputIP);
        //assert
        // use  ""(expected = Exception.class) to catch an exception
    }

    @Test
    public void stringIPtoInt_min_max() throws Exception {
        //arrange
        String inputIPMax = "255.255.255.255";
        String inputIPMin = "0.0.0.0";
        int expectedOutputMax = -1;
        int expectedOutputMin = 0;
        //action
        int actualOutputMax = CIDRCalculator.stringIPtoInt(inputIPMax);
        int actualOutputMin = CIDRCalculator.stringIPtoInt(inputIPMin);
        //assert
        assertEquals(expectedOutputMax, actualOutputMax);
        assertEquals(expectedOutputMin, actualOutputMin);
    }

    @Test
    public void convertIPIntDec2StringBinary_Normal()  throws Exception {
        //arrange
        int inputIP = -1062731519;
        String expectedOutput = "11000000.10101000.00000001.00000001";
        //action
        String actualOutput = Converter.convertIPIntDec2StringBinary(inputIP);
        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void convertIPIntDec2StringBinary_Output_MinMax()  throws Exception {
        //arrange
        int inputIPMin = 0;
        int inputIPMax = -1;
        String expectedOutputMin = "00000000.00000000.00000000.00000000";
        String expectedOutputMax = "11111111.11111111.11111111.11111111";
        //action
        String actualOutputMin = Converter.convertIPIntDec2StringBinary(inputIPMin);
        String actualOutputMax = Converter.convertIPIntDec2StringBinary(inputIPMax);
        //assert
        assertEquals(expectedOutputMin, actualOutputMin);
        assertEquals(expectedOutputMax, actualOutputMax);
    }

    @Test
    public void convertIPIntDec2StringBinary_Input_MinMax()  throws Exception {
        //arrange
        int inputIPMax = 2147483647;
        int inputIPMin = -2147483648;
        String expectedOutputMax = "01111111.11111111.11111111.11111111";
        String expectedOutputMin = "10000000.00000000.00000000.00000000";
        //action
        String actualOutputMax = Converter.convertIPIntDec2StringBinary(inputIPMax);
        String actualOutputMin = Converter.convertIPIntDec2StringBinary(inputIPMin);
        //assert
        assertEquals(expectedOutputMax, actualOutputMax);
        assertEquals(expectedOutputMin, actualOutputMin);
    }

    @Test
    public void convertIPIntDec2StringHex_Normal() throws Exception {
        //arrange
        int inputIP = -1062731519;
        String expectedOutput = "c0.a8.01.01";
        //action
        String actualOutput = Converter.convertIPIntDec2StringHex(inputIP);
        //assert
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void convertIPIntDec2StringHex_Input_MinMax() throws Exception {
        //arrange
        int inputIPMin = 2147483647;
        int inputIPMax = -2147483648;

        String expectedOutputMin = "7f.ff.ff.ff";
        String expectedOutputMax = "80.00.00.00";
        //action
        String actualOutputMin = Converter.convertIPIntDec2StringHex(inputIPMin);
        String actualOutputMax = Converter.convertIPIntDec2StringHex(inputIPMax);
        //assert
        assertEquals(expectedOutputMin, actualOutputMin);
        assertEquals(expectedOutputMax, actualOutputMax);
    }

    @Test
    public void convertIPIntDec2StringHex_Output_MinMax() throws Exception {
        //arrange
        int inputIPMin = 0;
        int inputIPMax = -1;

        String expectedOutputMin = "00.00.00.00";
        String expectedOutputMax = "ff.ff.ff.ff";
        //action
        String actualOutputMin = Converter.convertIPIntDec2StringHex(inputIPMin);
        String actualOutputMax = Converter.convertIPIntDec2StringHex(inputIPMax);
        //assert
        assertEquals(expectedOutputMin, actualOutputMin);
        assertEquals(expectedOutputMax, actualOutputMax);
    }

    @Test
    public void isMappedIPv4Address_TrueCase() throws Exception  {
        //arrange
        String ipString = "0000:0000:0000:0000:0000:ffff:c0a8:0101"; // 192.168.1.1
        boolean expectedResult = true;
        //action
        boolean actualResult = InetAddresses.isMappedIPv4Address(ipString);
        //assert
        assertTrue(expectedResult == actualResult);
    }

    @Test
    public void isMappedIPv4Address_FalseCase1() throws Exception  {
        //arrange
        String ipString = "1000:0000:0000:0000:0000:ffff:c0a8:0101"; // 192.168.1.1
        boolean expectedResult = false;
        //action
        boolean actualResult = InetAddresses.isMappedIPv4Address(ipString);
        //assert
        assertTrue(expectedResult == actualResult);
    }

    @Test
    public void isMappedIPv4Address_FalseCase2() throws Exception  {
        //arrange
        String ipString = "0000:0000:0000:0000:0000:fff1:c0a8:0101"; // 192.168.1.1
        boolean expectedResult = false;
        //action
        boolean actualResult = InetAddresses.isMappedIPv4Address(ipString);
        //assert
        assertTrue(expectedResult == actualResult);
    }

    @Test
    public void isMappedIPv4Address_FalseCase_Invaild_Input() throws Exception  {
        //arrange
        String ipString = ",000:0000:0000:0000:0000:fff1:c0a8:0101"; // 192.168.1.1
        boolean expectedResult = false;
        //action
        boolean actualResult = InetAddresses.isMappedIPv4Address(ipString);
        //assert
        assertTrue(expectedResult == actualResult);
    }

    @Test
    public void ipStringToBytes_Valid_IPv4() throws Exception  {
        //arrange
        String inputIP = "192.168.1.1";
        byte[] expectedOutput = {-64,-88,1,1};
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void ipStringToBytes_Valid_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0:0:0:0:ffff:c0a8:0101";
        byte[] expectedOutput = {0,0,0,0,0,0,0,0,0,0,-1,-1,-64,-88,1,1};
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void ipStringToBytes_Invalid_IPv4() throws Exception  {
        //arrange
        String inputIP = "192.168.1.-1";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void ipStringToBytes_Invalid_IPv6() throws Exception  {
        //arrange
        String inputIP = ",:0:0:0:0:ffff:c0a8:0101";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test
    public void toAddrString_IPv4() throws Exception {
        //arrange
        InetAddress inputIP = InetAddress.getByName("192.168.1.1");
        String expectedOutput = "192.168.1.1";
        //action
        String actualOutput = InetAddresses.toAddrString(inputIP);
        //assert
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void toAddrString_IPv6() throws Exception {
        //arrange
        InetAddress inputIP = InetAddress.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
        String expectedOutput = "2001:db8:85a3::8a2e:370:7334";
        //action
        String actualOutput = InetAddresses.toAddrString(inputIP);
        //assertt
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void toAddrString_Null() throws Exception {
        //arrange
        InetAddress inputIP = (InetAddress)null;
        String expectedOutput = null;
        //action
        String actualOutput = InetAddresses.toAddrString(inputIP);
        //assertt
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void fromBytes_Valid_IPv4_0_0_0_0() throws Exception  {
        //arrange
        byte[] input = {0,0,0,0};
        int expectedOutput = 0;
        //action
        int actualOutput = InetAddresses.fromBytes(input[0], input[1], input[2], input[3]);
        //assert
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void fromBytes_IPv4_Low_Boundary() throws Exception  {
        //arrange
        byte[] input = {-128,-128,-128,-128};
        int expectedOutput = -2139062144;
        //action
        int actualOutput = InetAddresses.fromBytes(input[0], input[1], input[2], input[3]);
        //assert
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void fromBytes_Invalid_IPv4_Up_Boundary() throws Exception  {
        //arrange
        byte[] input = {127,127,127,127};
        int expectedOutput = 2139062143;
        //action
        int actualOutput = InetAddresses.fromBytes(input[0], input[1], input[2], input[3]);
        //assert
        assertEquals(expectedOutput,actualOutput);
    }


    @Test //Structural Testing
    public void ipStringToBytes_Valid2_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0:0:0:0:ffff:192.168.1.1";
        byte[] expectedOutput = {0,0,0,0,0,0,0,0,0,0,-1,-1,-64,-88,1,1};
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid2_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0.0:0:0:ffff:192.168.1.1";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid3_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0:0:0:0:ffff:c0.a8.168.1.1";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid4_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0:0:0:0:ffff:c0.a8.1.1.1";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid5_IPv6() throws Exception  {
        //arrange
        String inputIP = "00000ffffc0a811";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid2_IPv4() throws Exception  {
        //arrange
        String inputIP = "192.168.c.1";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid6_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0:0:0:0:ffff:192:168:1:1:1";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid7_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0:0:0:0:ffff:::";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid8_IPv6() throws Exception  {
        //arrange
        String inputIP = ":0:0:0:0:ffff:c0a8::";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid9_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0:0:0:0:ffff:c0a8::1";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid10_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0:0:0:0:ffff::1:";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid11_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0:0:0:0:ffff:c0::";
        byte[] expectedOutput = {0,0,0,0,0,0,0,0,0,0,-1,-1,0,-64,0,0};;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid12_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0:0:0:0:ffff::ff";
        byte[] expectedOutput = {0,0,0,0,0,0,0,0,0,0,-1,-1,0,0,0,-1};;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid3_IPv4() throws Exception  {
        //arrange
        String inputIP = "255.255.255.256";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }

    @Test //Structural Testing
    public void ipStringToBytes_Invalid13_IPv6() throws Exception  {
        //arrange
        String inputIP = "0:0:0:0:0:ffff1::ff";
        byte[] expectedOutput = null;
        //action
        byte[] actualOutput = InetAddresses.ipStringToBytes(inputIP);
        //assert
        assertArrayEquals(expectedOutput, actualOutput);
    }
}