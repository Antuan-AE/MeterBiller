package com.tonyapps.meterlogic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class TariffTest {

    final float delta = 0.00001f;
    Tariff tariff09;

    @Before
    public void setUp() {
        this.tariff09 = new Tariff();
        tariff09.addRange(new Range(0, 100, 0.09f));
        tariff09.addRange(new Range(101, 150, 0.3f));
        tariff09.addRange(new Range(151, 200, 0.4f));
        tariff09.addRange(new Range(201, 250, 0.6f));
        tariff09.addRange(new Range(251, 300, 0.8f));
        tariff09.addRange(new Range(301, 350, 1.5f));
        tariff09.addRange(new Range(351, 500, 1.8f));
        tariff09.addRange(new Range(501, 1000, 2.0f));
        tariff09.addRange(new Range(1001, 5000, 3.0f));
        tariff09.addRange(new Range(5001, 5.0f));
    }

    @Test
    public void TaxBillOn() {
        try {
            assertEquals(8.1f, this.tariff09.getTaxBillOn(90), delta);
            assertEquals(9.0f, this.tariff09.getTaxBillOn(100), delta);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void MaxAmountOnRange() {
        try {
            assertEquals(9.0f, this.tariff09.getMaximimAmountOnRange(0), delta);
            assertEquals(15.0, this.tariff09.getMaximimAmountOnRange(1), delta);
            assertEquals(20.0, this.tariff09.getMaximimAmountOnRange(2), delta);
            assertEquals(30.0f, this.tariff09.getMaximimAmountOnRange(3), delta);
            assertEquals(40.0f, this.tariff09.getMaximimAmountOnRange(4), delta);
            assertEquals(75.0f, this.tariff09.getMaximimAmountOnRange(5), delta);
            assertEquals(270.0f, this.tariff09.getMaximimAmountOnRange(6), delta);
            assertEquals(1000.0f, this.tariff09.getMaximimAmountOnRange(7), delta);
            assertEquals(12000.0f, this.tariff09.getMaximimAmountOnRange(8), delta);
            assertEquals(25005.0f, this.tariff09.getMaximimAmountOnRange(9), delta);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
