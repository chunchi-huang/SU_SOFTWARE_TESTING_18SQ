package us.lindanrandy.cidrcalculator;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.robotium.solo.Solo;
import org.junit.Test;

public class CIDRCalculatorScenarioTesting extends ActivityInstrumentationTestCase2<CIDRCalculator> {

    private Solo solo;

    public CIDRCalculatorScenarioTesting() {
        super(CIDRCalculator.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.solo = new Solo(getInstrumentation(),getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    @Test
    public void testIpv4Calculator() {
        // Reset
        solo.clickOnButton(solo.getString(R.string.reset));
        //Input a valid IPv4 address
        solo.enterText((EditText) solo.getView(R.id.ipaddress),"192.168.1.1");
        //Do calculation
        solo.clickOnButton(solo.getString(R.string.calculate));

        solo.sleep(1000);

        //Check the calculation
        TextView addressRangeView = (TextView) solo.getView(R.id.address_range);
        assertEquals("192.168.1.0 - 192.168.1.255", (String) addressRangeView.getText());

        TextView maxAddressView = (TextView) solo.getView(R.id.maximum_addresses);
        assertEquals("254", (String) maxAddressView.getText());

        TextView wildcardView = (TextView) solo.getView(R.id.wildcard);
        assertEquals("0.0.0.255", (String) wildcardView.getText());

        TextView ipBinaryNetworkView = (TextView) solo.getView(R.id.ip_binary_network);
        assertEquals("11000000.10101000.00000001.", (String) ipBinaryNetworkView.getText());

        TextView ipBinaryHostView = (TextView) solo.getView(R.id.ip_binary_host);
        assertEquals("00000001", (String) ipBinaryHostView.getText());

        TextView ipBinaryMaskView = (TextView) solo.getView(R.id.ip_binary_netmask);
        assertEquals("11111111.11111111.11111111.00000000", (String) ipBinaryMaskView.getText());

        solo.sleep(1000);

        //Reset
        solo.clickOnButton(solo.getString(R.string.reset));
        //Input an invalid IPv4 address
        solo.enterText((EditText) solo.getView(R.id.ipaddress),"140.112.94.");
        solo.clickOnButton(solo.getString(R.string.calculate));

        solo.sleep(1000);
        //Check the calculation
        assertEquals("Bad IP format", (String) addressRangeView.getText());
        assertEquals("", (String) maxAddressView.getText());
        assertEquals("", (String) wildcardView.getText());
        assertEquals("", (String) ipBinaryNetworkView.getText());
        assertEquals("", (String) ipBinaryHostView.getText());
        assertEquals("", (String) ipBinaryMaskView.getText());

        solo.sleep(1000);

        solo.clickOnButton(solo.getString(R.string.reset));
        //Input a valid IPv4 address again
        solo.enterText((EditText) solo.getView(R.id.ipaddress),"140.112.94.6");
        // IP mask: "/30"
        solo.pressSpinnerItem(0, 6);
        solo.clickOnButton(solo.getString(R.string.calculate));
        //Check the calculation
        assertEquals("140.112.94.4 - 140.112.94.7", (String) addressRangeView.getText());
        assertEquals("2", (String) maxAddressView.getText());
        assertEquals("0.0.0.3", (String) wildcardView.getText());
        assertEquals("10001100.01110000.01011110.000001", (String) ipBinaryNetworkView.getText());
        assertEquals("10", (String) ipBinaryHostView.getText());
        assertEquals("11111111.11111111.11111111.11111100", (String) ipBinaryMaskView.getText());

        //solo.sendKey(Solo.MENU);
    }

    @Test
    public void testOpenConverter() {
        //Reset
        solo.clickOnButton(solo.getString(R.string.reset));
        //Input a valid IPv4 address
        solo.enterText((EditText) solo.getView(R.id.ipaddress),"192.168.1.3");
        //Do calculation
        solo.clickOnButton(solo.getString(R.string.calculate));
        //Send menu key
        solo.sendKey(Solo.MENU);
        //Select Converter
        solo.clickOnMenuItem("Converter");

        EditText converterIpaddressText = (EditText) solo.getView(R.id.converter_ipaddress);
        //Check whether the ip is the same with the ip of Calculator
        assertEquals("192.168.1.3", (String) converterIpaddressText.getText().toString());

        TextView ipBinaryView = (TextView) solo.getView(R.id.ipbinary);
        assertEquals("11000000.10101000.00000001.00000011", (String) ipBinaryView.getText().toString());

        TextView ipHexView = (TextView) solo.getView(R.id.iphex);
        assertEquals("c0.a8.01.03", (String) ipHexView.getText().toString());
    }

    @Test
    public void testOpenIPv6Calculator() {

        //Send menu key
        solo.sendKey(Solo.MENU);
        //Select IPv6 Calculator
        solo.clickOnMenuItem("IPv6");
        //Reset
        solo.clickOnButton(solo.getString(R.string.reset));
        //Input a valid IPv6 address
        solo.enterText((EditText) solo.getView(R.id.ipv6address),"0:0:0:0:0:ffff:c0a8:101");
        //Do calculation
        solo.clickOnButton(solo.getString(R.string.calculate));

        TextView ipv6AddressRangeView = (TextView) solo.getView(R.id.v6address_range);
        assertEquals("ffff:ffff:ffff:ffff:: - ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff", (String) ipv6AddressRangeView.getText().toString());

        TextView maxAddressesView = (TextView) solo.getView(R.id.v6maximum_addresses);
        assertEquals("18446744073709551614", (String) maxAddressesView.getText().toString());

        TextView ipv6InfoView = (TextView) solo.getView(R.id.v6info);
        assertEquals("Mapped IPv4", (String) ipv6InfoView.getText().toString());

        solo.sleep(1000);
        // IP mask: "/70"
        solo.pressSpinnerItem(0, 6);
        solo.clickOnButton(solo.getString(R.string.calculate));

        assertEquals("ffff:ffff:ffff:ffff:fc00:: - ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff", (String) ipv6AddressRangeView.getText().toString());
        assertEquals("288230376151711742", (String) maxAddressesView.getText().toString());
        assertEquals("Mapped IPv4", (String) ipv6InfoView.getText().toString());
    }
}
