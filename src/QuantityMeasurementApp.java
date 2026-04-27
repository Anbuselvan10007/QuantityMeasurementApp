public class QuantityMeasurementApp {

    // UC2: Feet and Inches Measurement Equality
    enum Unit {
        FEET(12.0),
        INCH(1.0);

        double conversionFactor;

        Unit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }
    }

    double value;
    Unit unit;

    public QuantityMeasurementApp(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public double convertToBaseUnit() {
        return this.value * this.unit.conversionFactor;
    }

    public boolean equals(QuantityMeasurementApp other) {
        return this.convertToBaseUnit() == other.convertToBaseUnit();
    }

    public static void main(String[] args) {
        QuantityMeasurementApp feet1 = new QuantityMeasurementApp(1.0, Unit.FEET);
        QuantityMeasurementApp inch1 = new QuantityMeasurementApp(12.0, Unit.INCH);
        QuantityMeasurementApp inch2 = new QuantityMeasurementApp(1.0, Unit.INCH);

        System.out.println("1 feet == 12 inch: " + feet1.equals(inch1)); // true
        System.out.println("1 feet == 1 inch: " + feet1.equals(inch2));  // false
    }
}