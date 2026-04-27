public class QuantityMeasurementApp {

    // UC4: Extended Unit Support - Yards and Centimeters added
    enum Unit {
        FEET(12.0),
        INCH(1.0),
        YARD(36.0),
        CENTIMETER(0.393701);

        final double conversionFactor; // everything converts to inches as base

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

    // Convert all units to inches as base unit
    public double convertToInches() {
        return this.value * this.unit.conversionFactor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;
        return Double.compare(this.convertToInches(), other.convertToInches()) == 0;
    }

    public static void main(String[] args) {
        // Feet comparisons
        QuantityMeasurementApp feet1 = new QuantityMeasurementApp(1.0, Unit.FEET);
        QuantityMeasurementApp feet2 = new QuantityMeasurementApp(1.0, Unit.FEET);
        System.out.println("1 feet == 1 feet: " + feet1.equals(feet2));       // true

        // Inch comparisons
        QuantityMeasurementApp inch1 = new QuantityMeasurementApp(12.0, Unit.INCH);
        System.out.println("1 feet == 12 inch: " + feet1.equals(inch1));      // true

        // Yard comparisons
        QuantityMeasurementApp yard1 = new QuantityMeasurementApp(1.0, Unit.YARD);
        QuantityMeasurementApp feet3 = new QuantityMeasurementApp(3.0, Unit.FEET);
        QuantityMeasurementApp inch2 = new QuantityMeasurementApp(36.0, Unit.INCH);
        System.out.println("1 yard == 3 feet:  " + yard1.equals(feet3));      // true
        System.out.println("1 yard == 36 inch: " + yard1.equals(inch2));      // true

        // Centimeter comparisons
        QuantityMeasurementApp cm1 = new QuantityMeasurementApp(2.54, Unit.CENTIMETER);
        QuantityMeasurementApp inch3 = new QuantityMeasurementApp(1.0, Unit.INCH);
        System.out.println("2.54 cm == 1 inch: " + cm1.equals(inch3));        // true
    }
}