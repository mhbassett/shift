package shift;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Utils {

    private Utils() {
        // Prevent instantiation
    }

    public static class FunctionIterator<T> implements Iterator<T> {
        private final Supplier<T> function;
        private T next;

        public FunctionIterator(Supplier<T> function, Predicate<T> endCondition) {
            this.function = function;
            this.next = function.get();
            while (endCondition.test(next)) {
                next = function.get();
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            T current = next;
            next = function.get();
            return current;
        }
    }

    public static class LinkedNode implements Iterable<LinkedNode> {
        public LinkedNode next = null;

        protected LinkedNode then(LinkedNode next) {
            this.next = next;
            return next;
        }

        public LinkedNode next() {
            return next;
        }

        @Override
        public Iterator<LinkedNode> iterator() {
            return new  Iterator<>() {
                private LinkedNode current = LinkedNode.this;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public LinkedNode next() {
                    LinkedNode temp = current;
                    current = current.next;
                    return temp;
                }
            };
        }
    }

    public static boolean isLetter(int c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }

    public static boolean isDigit(int c) {
        return '0' <= c && c <= '9';
    }

    public static boolean isHexDigit(int c) {
        return isDigit(c) || 'a' <= c && c <= 'f' || 'A' <= c && c <= 'F';
    }

    public static boolean isIdentifierStart(int c) {
        return isLetter(c) || c == '_';
    }

    public static boolean isIdentifierPart(int c) {
        return isIdentifierStart(c) || isDigit(c);
    }

    public static boolean isIdentifier(String token) {
        int first = token.codePointAt(0);
        if (!isIdentifierStart(first)) return false;
        for (int i = 1, c; i < token.length();) {
            if (!isIdentifierPart(c = token.charAt(i++))) return false;
        }
        return true;
    }

    public static <T> Iterable<T> iterable(Iterator<T> it) {
        return () -> it;
    }

    public static <T> T qq(T a, T b) {
        return a == null ? b : a;
    }

    public static <T> T qq(T a, Supplier<T> b) {
        return a == null ? b.get() : a;
    }

    public static void sqq(Object a, Runnable b) {
        if (a == null) {
            b.run();
        }
    }
    
}
