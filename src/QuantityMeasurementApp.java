public class QuantityMeasurementApp {

    // UC5: Unit-to-Unit Conversion (Same Measurement Type)
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

        // Step 1: convert to base unit (inches)
        double inInches = value * sourceUnit.getConversionFactor();

        // Step 2: convert from base unit to target unit
        double result = inInches / targetUnit.getConversionFactor();

        // Step 3: round to 2 decimal places
        return Math.round(result * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;
        return Double.compare(this.convertToInches(), other.convertToInches()) == 0;
    }

    public static void main(String[] args) {
        // Feet to Inches
        System.out.println("1 feet -> inches:     " + convert(1.0, Unit.FEET, Unit.INCH));       // 12.0
        System.out.println("1 yard -> inches:     " + convert(1.0, Unit.YARD, Unit.INCH));       // 36.0
        System.out.println("1 yard -> feet:       " + convert(1.0, Unit.YARD, Unit.FEET));       // 3.0
        System.out.println("12 inch -> feet:      " + convert(12.0, Unit.INCH, Unit.FEET));      // 1.0
        System.out.println("2.54 cm -> inches:    " + convert(2.54, Unit.CENTIMETER, Unit.INCH));// 1.0
        System.out.println("1 inch -> cm:         " + convert(1.0, Unit.INCH, Unit.CENTIMETER)); // 2.54

        // Equality still works from UC4
        QuantityMeasurementApp yard1 = new QuantityMeasurementApp(1.0, Unit.YARD);
        QuantityMeasurementApp feet1 = new QuantityMeasurementApp(3.0, Unit.FEET);
        System.out.println("1 yard == 3 feet:     " + yard1.equals(feet1));                      // true
    }
}