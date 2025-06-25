package shift.rescode;

public enum Opcode {
    EOF, // End of file, used to mark the end of a bytecode stream
    NOP,
    HALT,

    // i32 operations
    I32ADD,
    I32SUB,
    I32MUL,
    I32DIV,
    I32MOD,
    U32DIV,
    U32MOD,
    I32AND,
    I32OR,
    I32XOR,
    I32SHL,
    I32SHR,
    U32SHR,
    I32NEG,
    I32NOT,
    I32EQ,
    I32NE,
    I32LT,
    I32LE,
    I32GT,
    I32GE,

    // i64 operations
    I64ADD,
    I64SUB,
    I64MUL,
    I64DIV,
    I64MOD,
    U64DIV,
    U64MOD,
    I64AND,
    I64OR,
    I64XOR,
    I64SHL,
    I64SHR,
    U64SHR,
    I64NEG,
    I64NOT,
    I64EQ,
    I64NE,
    I64LT,
    I64LE,
    I64GT,
    I64GE,

    // Single operations
    F32ADD,
    F32SUB,
    F32MUL,
    F32DIV,
    F32NEG,
    F32CMP,

    // Double operations
    F64ADD,
    F64SUB,
    F64MUL,
    F64DIV,
    F64NEG,
    F64CMP,

    // Conversion operations
    I32TOF32,

    // Control flow operations
    JUMP,
    JUMP_POP,
    JUMP_IF,
    CALL,
    RETURN,

    // Memory operations
    DEREF_1,
    DEREF_2,
    DEREF_4,
    DEREF_8,

    STORE_1,
    STORE_2,
    STORE_4,
    STORE_8,

    LOAD_GLOBALVAR, // load start of global variables
    LOAD_LOCALVAR, // load start of local variables

    /**
     * All objects have:
     *  Class Pointer (8 bytes)
     *  Pointer to the next object (8 bytes)
     *  Fields (? bytes)
     *  Array slots (??? bytes)
     */
    ALLOC_OBJECT,

    // Stack operations
    LOAD_32,
    LOAD_64,

    POP, // discard top value
    
    // Constants
    K_M1,
    K_0, // aka. K_0F, useful for converting to i64
    K_1,
    K_2,

    K_M1L,
    K_0L, // aka. K_0D
    K_1L,
    K_2L,

    K_M1F,
    K_M0F,
    K_1F,
    K_2F,

    K_M1D,
    K_M0D,
    K_1D,
    K_2D,

    K_INF32,
    K_INF64,
    K_NAN32,
    K_NAN64,

    // Constant Operands
    I32MOD_2,
    I64MOD_2,
    F32MOD_2,
    F64MOD_2,
    ;
}
