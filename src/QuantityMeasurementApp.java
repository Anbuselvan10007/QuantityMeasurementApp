public class QuantityMeasurementApp {

    // UC7: Addition with Target Unit Specification
    enum Unit {
        FEET(12.0),
        INCH(1.0),
        YARD(36.0),
        CENTIMETER(0.393701);

        final double conversionFactor; // base unit = inches

        Unit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor() {
            return conversionFactor;
        }
    }

    double value;
    Unit unit;

    public QuantityMeasurementApp(double value, Unit unit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be a finite number");
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    // Convert to inches (base unit)
    public double convertToInches() {
        return this.value * this.unit.conversionFactor;
    }

    // UC5: Static conversion method
    public static double convert(double value, Unit sourceUnit, Unit targetUnit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be a finite number");
        if (sourceUnit == null || targetUnit == null)
            throw new IllegalArgumentException("Units cannot be null");
        double inInches = value * sourceUnit.getConversionFactor();
        double result = inInches / targetUnit.getConversionFactor();
        return Math.round(result * 100.0) / 100.0;
    }

    // UC6: Add two lengths - result in unit of first operand
    public static QuantityMeasurementApp add(QuantityMeasurementApp length1,
                                             QuantityMeasurementApp length2) {
        if (length1 == null || length2 == null)
            throw new IllegalArgumentException("Lengths cannot be null");
        double totalInches = length1.convertToInches() + length2.convertToInches();
        double resultValue = Math.round((totalInches / length1.unit.getConversionFactor()) * 100.0) / 100.0;
        return new QuantityMeasurementApp(resultValue, length1.unit);
    }

    // UC7: Add two lengths - result in explicitly specified target unit
    public static QuantityMeasurementApp add(QuantityMeasurementApp length1,
                                             QuantityMeasurementApp length2,
                                             Unit targetUnit) {
        if (length1 == null || length2 == null)
            throw new IllegalArgumentException("Lengths cannot be null");
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
        if (!Double.isFinite(length1.value) || !Double.isFinite(length2.value))
            throw new IllegalArgumentException("Values must be finite numbers");

        // Convert both to base unit (inches)
        double totalInches = length1.convertToInches() + length2.convertToInches();

        // Convert to explicitly specified target unit
        double resultValue = Math.round((totalInches / targetUnit.getConversionFactor()) * 100.0) / 100.0;

        return new QuantityMeasurementApp(resultValue, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;
        return Double.compare(this.convertToInches(), other.convertToInches()) == 0;
    }

    public static void main(String[] args) {
        QuantityMeasurementApp feet1 = new QuantityMeasurementApp(1.0, Unit.FEET);
        QuantityMeasurementApp inch12 = new QuantityMeasurementApp(12.0, Unit.INCH);

        // UC6 style - result in unit of first operand (feet)
        QuantityMeasurementApp result1 = add(feet1, inch12);
        System.out.println("1 feet + 12 inch (default) = "
                + result1.value + " " + result1.unit);           // 2.0 FEET

        // UC7 - result in YARDS (explicit target unit)
        QuantityMeasurementApp result2 = add(feet1, inch12, Unit.YARD);
        System.out.println("1 feet + 12 inch in YARDS  = "
                + result2.value + " " + result2.unit);           // 0.67 YARD

        // UC7 - result in INCHES (explicit target unit)
        QuantityMeasurementApp result3 = add(feet1, inch12, Unit.INCH);
        System.out.println("1 feet + 12 inch in INCHES = "
                + result3.value + " " + result3.unit);           // 24.0 INCH

        // UC7 - 1 yard + 1 foot in CENTIMETERS
        QuantityMeasurementApp yard1 = new QuantityMeasurementApp(1.0, Unit.YARD);
        QuantityMeasurementApp feet2 = new QuantityMeasurementApp(1.0, Unit.FEET);
        QuantityMeasurementApp result4 = add(yard1, feet2, Unit.CENTIMETER);
        System.out.println("1 yard + 1 feet in CM      = "
                + result4.value + " " + result4.unit);           // ~121.92 CENTIMETER
    }
}