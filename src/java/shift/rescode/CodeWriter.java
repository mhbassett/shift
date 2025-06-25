package shift.rescode;

import java.io.IOException;

public class CodeWriter {
    public static byte[] unify(Object[] code) throws IOException {
        byte[] res = new byte[code.length];
        
        for (int i = 0; i < code.length;) {
            switch (code[i]) {
                case Opcode opcode -> {
                    res[i++] = (byte) opcode.ordinal();
                }
                case Integer integer -> {
                    int value = integer;
                    res[i++] = (byte) (value >> 24);
                    res[i++] = (byte) (value >> 16);
                    res[i++] = (byte) (value >> 8);
                    res[i++] = (byte) value;
                }
                case Long l -> {
                    long value = l;
                    res[i++] = (byte) (value >> 56);
                    res[i++] = (byte) (value >> 48);
                    res[i++] = (byte) (value >> 40);
                    res[i++] = (byte) (value >> 32);
                    res[i++] = (byte) (value >> 24);
                    res[i++] = (byte) (value >> 16);
                    res[i++] = (byte) (value >> 8);
                    res[i++] = (byte) value;
                }
                default -> throw new ClassCastException();
            }
        }

        return res;
    }
}
