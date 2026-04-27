public class QuantityMeasurementApp {

    // UC6: Addition of Two Length Units (Same Category)
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

        // Convert both to inches (base unit)
        double totalInches = length1.convertToInches() + length2.convertToInches();

        // Convert result back to unit of first operand
        double resultValue = totalInches / length1.unit.getConversionFactor();
        resultValue = Math.round(resultValue * 100.0) / 100.0;

        return new QuantityMeasurementApp(resultValue, length1.unit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;
        return Double.compare(this.convertToInches(), other.convertToInches()) == 0;
    }

    public static void main(String[] args) {
        // 1 foot + 12 inches = 2 feet
        QuantityMeasurementApp feet1 = new QuantityMeasurementApp(1.0, Unit.FEET);
        QuantityMeasurementApp inch12 = new QuantityMeasurementApp(12.0, Unit.INCH);
        QuantityMeasurementApp result1 = add(feet1, inch12);
        System.out.println("1 feet + 12 inch = " + result1.value + " " + result1.unit); // 2.0 FEET

        // 1 foot + 1 foot = 2 feet
        QuantityMeasurementApp feet1b = new QuantityMeasurementApp(1.0, Unit.FEET);
        QuantityMeasurementApp feet1c = new QuantityMeasurementApp(1.0, Unit.FEET);
        QuantityMeasurementApp result2 = add(feet1b, feet1c);
        System.out.println("1 feet + 1 feet = " + result2.value + " " + result2.unit);  // 2.0 FEET

        // 1 yard + 1 foot = 4 feet
        QuantityMeasurementApp yard1 = new QuantityMeasurementApp(1.0, Unit.YARD);
        QuantityMeasurementApp feet1d = new QuantityMeasurementApp(1.0, Unit.FEET);
        QuantityMeasurementApp result3 = add(yard1, feet1d);
        System.out.println("1 yard + 1 feet = " + result3.value + " " + result3.unit);  // 4.0 FEET (in yards = 1.33)

        // 2.54 cm + 2.54 cm = 2 inches
        QuantityMeasurementApp cm1 = new QuantityMeasurementApp(2.54, Unit.CENTIMETER);
        QuantityMeasurementApp cm2 = new QuantityMeasurementApp(2.54, Unit.CENTIMETER);
        QuantityMeasurementApp result4 = add(cm1, cm2);
        System.out.println("2.54 cm + 2.54 cm = " + result4.value + " " + result4.unit); // 2.0 INCH (in cm)
    }
}