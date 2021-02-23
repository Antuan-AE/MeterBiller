package com.tonyapps.meterlogic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Meter {

    private Tariff tariff2009;
    private Tariff tariff2021;
    private Tariff tariff;

    public enum TariffType {
        OLD_TARIFF,
        NEW_TARIFF_2021
    }

    private static TariffType tariffType;

    public Meter(){
        tariff2009 = new Tariff(10);
        tariff2009.addRange(new Range(0,100,0.09f));
        tariff2009.addRange(new Range(101, 150, 0.3f));
        tariff2009.addRange(new Range(151, 200, 0.4f));
        tariff2009.addRange(new Range(201, 250, 0.6f));
        tariff2009.addRange(new Range(251, 300, 0.8f));
        tariff2009.addRange(new Range(301, 350, 1.5f));
        tariff2009.addRange(new Range(351, 500, 1.8f));
        tariff2009.addRange(new Range(501, 1000, 2.0f));
        tariff2009.addRange(new Range(1001, 5000, 3.0f));
        tariff2009.addRange(new Range(5001, 5.0f));

        tariff2021 = new Tariff();
        tariff2021.addRange(new Range(0, 100, 0.3278f));
        tariff2021.addRange(new Range(101, 150, 1.0656f));
        tariff2021.addRange(new Range(151, 200, 1.4344f));
        tariff2021.addRange(new Range(201, 250, 2.4588f));
        tariff2021.addRange(new Range(251, 300, 3.00f));
        tariff2021.addRange(new Range(301, 350, 4.00f));
        tariff2021.addRange(new Range(351, 400, 5.00f));
        tariff2021.addRange(new Range(401, 450, 6.00f));
        tariff2021.addRange(new Range(451, 500, 7.00f));
        tariff2021.addRange(new Range(501, 600, 9.20f));
        tariff2021.addRange(new Range(601, 700, 9.45f));
        tariff2021.addRange(new Range(701, 1000, 9.85f));
        tariff2021.addRange(new Range(1001, 1800, 10.80f));
        tariff2021.addRange(new Range(1801, 2600, 11.80f));
        tariff2021.addRange(new Range(2601, 3400, 12.90f));
        tariff2021.addRange(new Range(3401, 4200, 13.95f));
        tariff2021.addRange(new Range(4201, 5000, 15.00f));
        tariff2021.addRange(new Range(5001, 20.0f));

        this.tariff = new Tariff();
    }

    public Meter(TariffType type) {
        this();
        tariffType = type;
    }

    public Meter(JSONObject ob) throws JSONException {
        if(ob != null) { ///TODO instantiate object with JSON file content
            JSONArray items = ob.names();
            int itemMany = ob.length();
            this.tariff2009 = new Tariff(itemMany);
            this.tariff = new Tariff(ob.length());
            Integer count = 0;
            if(items != null) {
                for(int index = 0; index < itemMany; index++) {
                   JSONObject value = ob.getJSONObject(items.getString(index));
                   float tax = (float)value.getDouble("tax");
                   int minVal = value.getInt("minValue");
                   int maxVal = value.getInt("maxValue");
                   boolean isLast = value.getBoolean("isLast");
                   this.tariff2009.addRange((isLast) ? new Range(minVal, tax)
                                                     : new Range(minVal, maxVal, tax));
                   count = this.tariff2009.getRangeAmount();

                   this.tariff.addRange((value.getBoolean("isLast")) ?
                                         new Range(value.getInt("minValue"),
                                                   (float)value.getDouble("tax")) :
                                         new Range(value.getInt("minValue"),
                                                   value.getInt("maxValue"),
                                                   (float)value.getDouble("tax")));
                   count = this.tariff.getRangeAmount();
                }
            }
        }
    }

    public float getBill(Integer intake) {
        return (tariffType  == TariffType.OLD_TARIFF)   ?
                tariff2009.getTaxBillOn(intake)         :
                tariff2021.getTaxBillOn(intake);
    }

    public boolean getAmount(final Integer lastReading, final Integer currentReading, float amount) {
        boolean retVal = false;
        if(lastReading > currentReading)
            return retVal;
        amount = this.getBill(currentReading - lastReading);
        return retVal;
    }

    public static void setTariffType(TariffType type) {
        tariffType = type;
    }

    public static TariffType getTariffType() {
        return tariffType;
    }

    private Integer getRange(Integer value){
        return tariff2021.findRange(value);
    }

    private float getData(Integer value){
        return tariff2021.getMaximimAmountOnRange(value);
    }
}
