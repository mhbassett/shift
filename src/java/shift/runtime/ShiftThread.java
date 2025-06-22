package java.shift.runtime;

import shift.rescode.Opcode;

public class ShiftThread {
    public long pc;
    public int[] valueStack;
    public int vsLength;

    public byte[] varStack;

    public int pop() {
        return valueStack[--vsLength];
    }

    public long pop2() {
        return (pop() | ((long)pop() << 32));
    }

    public void push(long value) {
        valueStack[vsLength++] = (byte)value;
    }

    public void push2(long value) {
        valueStack[vsLength++] = (int)(value >> 32);
        valueStack[vsLength++] = (int)(value);
    }

    public void run(Opcode op) {
        switch (op) {
            case IADD -> push(pop() + pop());
            case ISUB -> push(pop() - pop());
        }
    }

    




}
