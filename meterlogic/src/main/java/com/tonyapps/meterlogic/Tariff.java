package com.tonyapps.meterlogic;

import java.util.ArrayList;

/**
 *
 * @author antonio.iznaga
 * @brief Tariff
 */
public class Tariff {
    private static Integer rangeAmount = 0;
    private static final Integer ROUND_ON_CENTS = 20;

    private ArrayList observers;
    private Range[] ranges;

    public Integer getRangeAmount(){
        return rangeAmount;
    }

    public Tariff() {
        this(20);
    }

    public Tariff(Integer rangeCount){
        rangeAmount = 0;
        ranges = new Range[rangeCount];
    }

    public boolean addRange(Range anotherRange){
        this.ranges[getRangeAmount()] = anotherRange;
        rangeAmount++;
        return true;
    }

    public Integer findRange(Integer value){
        if(value < 0)
            return -1;
        Integer rangeValue = 0;
        for(Integer loop = 0; loop < rangeAmount; loop++){
            if(value <= ranges[loop].getMaximumValue()){
                rangeValue = loop;
                break;
            }
            rangeValue = loop;
        }
        return rangeValue;
    }

    public float getTaxBillOn(Integer intake) throws NullPointerException {
        Integer belongsToRange = this.findRange(intake);
        Integer finalDifference;
        if(belongsToRange == 0){
            finalDifference = intake - this.ranges[belongsToRange].getMinimumValue();
        }
        else{
            finalDifference = (this.ranges[belongsToRange].getMaximumValue() >= intake) ?
                    (intake - this.ranges[belongsToRange - 1].getMaximumValue()) :
                    (intake - this.ranges[belongsToRange].getMinimumValue());
        }

        float sum = 0.0f;
        for(Integer loop = 0; loop < belongsToRange; loop++){
            sum += this.ranges[loop].getMaximumTaxAmount();
        }
        sum += finalDifference * this.ranges[belongsToRange].getTax();
        float bill = this.roundTo(sum);
        return bill;//this.roundTo(sum);
    }

    public float getMaximimAmountOnRange(Integer range){
        if (range >= rangeAmount)
            return 0.0f;
        return this.ranges[range].getMaximumTaxAmount();
    }

    private float roundTo(float value){
        int presition = (Meter.getTariffType() == Meter.TariffType.NEW_TARIFF_2021) ? (ROUND_ON_CENTS  * 100) : ROUND_ON_CENTS;
        return (float)Math.round(value * presition) / presition;
    }
}
