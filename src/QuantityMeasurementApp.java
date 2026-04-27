public class QuantityMeasurementApp {

    // UC3: Generic Quantity class - DRY Principle
    enum Unit {
        FEET(1.0),
        INCH(1.0 / 12.0);

        final double conversionFactor;

        Unit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }
    }

    double value;
    Unit unit;

    // Single generic constructor for any unit
    public QuantityMeasurementApp(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    // Convert any unit to base unit (feet)
    public double convertToFeet() {
        return this.value * this.unit.conversionFactor;
    }

    // Single equals() for all units - no duplication
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;
        return Double.compare(this.convertToFeet(), other.convertToFeet()) == 0;
    }

    public static void main(String[] args) {
        // Feet comparisons (UC1 still works)
        QuantityMeasurementApp feet1 = new QuantityMeasurementApp(1.0, Unit.FEET);
        QuantityMeasurementApp feet2 = new QuantityMeasurementApp(1.0, Unit.FEET);
        QuantityMeasurementApp feet3 = new QuantityMeasurementApp(2.0, Unit.FEET);
        System.out.println("1 feet == 1 feet: " + feet1.equals(feet2)); // true
        System.out.println("1 feet == 2 feet: " + feet1.equals(feet3)); // false

        // Inches comparisons (UC2 still works)
        QuantityMeasurementApp inch1 = new QuantityMeasurementApp(12.0, Unit.INCH);
        QuantityMeasurementApp inch2 = new QuantityMeasurementApp(12.0, Unit.INCH);
        QuantityMeasurementApp inch3 = new QuantityMeasurementApp(1.0, Unit.INCH);
        System.out.println("12 inch == 12 inch: " + inch1.equals(inch2)); // true
        System.out.println("12 inch == 1 inch:  " + inch1.equals(inch3)); // false

        // Cross unit comparison
        QuantityMeasurementApp oneFeet = new QuantityMeasurementApp(1.0, Unit.FEET);
        QuantityMeasurementApp twelveInch = new QuantityMeasurementApp(12.0, Unit.INCH);
        System.out.println("1 feet == 12 inch:  " + oneFeet.equals(twelveInch)); // true
    }
}