// UC8: LengthUnit extracted as standalone top-level enum
enum LengthUnit {
    FEET(12.0),
    INCH(1.0),
    YARD(36.0),
    CENTIMETER(0.393701);

    private final double conversionFactor; // base unit = inches

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    // Convert value in this unit to base unit (inches)
    public double convertToBaseUnit(double value) {
        return value * this.conversionFactor;
    }

    // Convert base unit (inches) back to this unit
    public double convertFromBaseUnit(double baseValue) {
        return Math.round((baseValue / this.conversionFactor) * 100.0) / 100.0;
    }
}

// QuantityMeasurementApp simplified - delegates conversion to LengthUnit
public class QuantityMeasurementApp {

    double value;
    LengthUnit unit;

    public QuantityMeasurementApp(double value, LengthUnit unit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be a finite number");
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    // Delegates to LengthUnit
    public double convertToBaseUnit() {
        return this.unit.convertToBaseUnit(this.value);
    }

    // UC5: Convert between any two units
    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be a finite number");
        if (sourceUnit == null || targetUnit == null)
            throw new IllegalArgumentException("Units cannot be null");
        double inBase = sourceUnit.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(inBase);
    }

    // UC6: Add two lengths - result in unit of first operand
    public static QuantityMeasurementApp add(QuantityMeasurementApp l1,
                                             QuantityMeasurementApp l2) {
        if (l1 == null || l2 == null)
            throw new IllegalArgumentException("Lengths cannot be null");
        double totalBase = l1.convertToBaseUnit() + l2.convertToBaseUnit();
        double resultValue = l1.unit.convertFromBaseUnit(totalBase);
        return new QuantityMeasurementApp(resultValue, l1.unit);
    }

    // UC7: Add two lengths - result in explicit target unit
    public static QuantityMeasurementApp add(QuantityMeasurementApp l1,
                                             QuantityMeasurementApp l2,
                                             LengthUnit targetUnit) {
        if (l1 == null || l2 == null)
            throw new IllegalArgumentException("Lengths cannot be null");
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double totalBase = l1.convertToBaseUnit() + l2.convertToBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(totalBase);
        return new QuantityMeasurementApp(resultValue, targetUnit);
    }

    // UC1-UC4: Equality check
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityMeasurementApp other = (QuantityMeasurementApp) obj;
        return Double.compare(this.convertToBaseUnit(), other.convertToBaseUnit()) == 0;
    }

    public static void main(String[] args) {
        // UC1: Feet equality
        QuantityMeasurementApp f1 = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        QuantityMeasurementApp f2 = new QuantityMeasurementApp(1.0, LengthUnit.FEET);
        System.out.println("1 feet == 1 feet:          " + f1.equals(f2));         // true

        // UC2/UC3: Cross unit equality
        QuantityMeasurementApp i12 = new QuantityMeasurementApp(12.0, LengthUnit.INCH);
        System.out.println("1 feet == 12 inch:         " + f1.equals(i12));        // true

        // UC4: Yard & CM
        QuantityMeasurementApp y1 = new QuantityMeasurementApp(1.0, LengthUnit.YARD);
        QuantityMeasurementApp f3 = new QuantityMeasurementApp(3.0, LengthUnit.FEET);
        System.out.println("1 yard == 3 feet:          " + y1.equals(f3));         // true

        // UC5: Conversion
        System.out.println("1 feet -> inches:          " + convert(1.0, LengthUnit.FEET, LengthUnit.INCH));   // 12.0
        System.out.println("1 yard -> feet:            " + convert(1.0, LengthUnit.YARD, LengthUnit.FEET));   // 3.0
        System.out.println("2.54 cm -> inches:         " + convert(2.54, LengthUnit.CENTIMETER, LengthUnit.INCH)); // 1.0

        // UC6: Add with default unit
        QuantityMeasurementApp result1 = add(f1, i12);
        System.out.println("1 feet + 12 inch =         " + result1.value + " " + result1.unit); // 2.0 FEET

        // UC7: Add with target unit
        QuantityMeasurementApp result2 = add(f1, i12, LengthUnit.YARD);
        System.out.println("1 feet + 12 inch in YARDS= " + result2.value + " " + result2.unit); // 0.67 YARD
    }
}