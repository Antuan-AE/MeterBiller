package com.tonyapps.meterlogic;

public class Range {
    public Range(Integer lastValue, float taxValue){
        this(lastValue, 0, taxValue);
        lastRange = true;
    }

    public Range(Integer minValue, Integer maxValue, float taxValue) {
        minimumValue = minValue;
        maximumValue = maxValue;
        tax = taxValue;
        lastRange = false;
    }

    public boolean isLastRange() { return lastRange; }

    public float getMaximumTaxAmount(){
        return (isLastRange()) ? this.minimumValue * this.tax :
                (this.minimumValue == 0) ?
                        ((this.maximumValue - this.minimumValue) * this.tax) :
                        ((this.maximumValue - this.minimumValue + 1) * this.tax);
    }

    public Integer getMinimumValue(){
        return this.minimumValue;
    }

    public Integer getMaximumValue(){
        return (isLastRange()) ? this.minimumValue : this.maximumValue;
    }

    public float getTax(){
        return this.tax;
    }
    private boolean lastRange;

    final private Integer minimumValue;

    final private Integer maximumValue;

    final private float tax;
}
