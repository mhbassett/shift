package shift.structure;

import shift.rescode.Opcode;

public class ShiftType {
    public String name;
    public ShiftType[] implCasts;

    public ShiftType(String name, ShiftType... implCasts) {
        this.name = name;
        this.implCasts = implCasts;
    }

    public ShiftType onAdd(Opcode... ops) {
        // This method is a placeholder for future implementation
        return this;
    }

    public static ShiftType i64 = new ShiftType("i64");
}
