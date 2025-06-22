package shift.rescode;

public enum Opcode {
    // Integer operations
    IADD,
    ISUB,
    IMUL,
    IDIV,
    IMOD,
    UDIV,
    UMOD,
    IAND,
    IOR,
    IXOR,
    ISHL,
    ISHR,
    USHR,
    INEG,
    INOT,
    IEQ,
    INE,
    ILT,
    ILE,
    IGT,
    IGE,

    // Single operations
    FADD,
    FSUB,
    FMUL,
    FDIV,
    FNEG,
    FCMP,

    // Double operations
    DADD,
    DSUB,
    DMUL,
    DDIV,
    DNEG,
    DCMP,

    // Conversion operations
    I2F,
    I2D,
    F2I,
    F2D,
    F2U,
    D2I,
    D2F,
    D2U,

    // Control flow operations
    JUMP,
    JUMP_IF,
    CALL,
    RETURN,

    // Memory operations
    DEREF_BYTE,
    DEREF_SHORT,
    DEREF_INT,
    DEREF_LONG,

    STORE_BYTE,
    STORE_SHORT,
    STORE_INT,
    STORE_LONG,

    INC_BYTE,
    INC_SHORT,
    INC_INT,
    INC_LONG,

    DEC_BYTE,
    DEC_SHORT,
    DEC_INT,
    DEC_LONG,

    MALLOC,
    FREE,

    // Stack operations
    LOAD_BYTE,
    LOAD_SHORT,
    LOAD_INT,
    LOAD_LONG,
    LOADN,

    ;
}
