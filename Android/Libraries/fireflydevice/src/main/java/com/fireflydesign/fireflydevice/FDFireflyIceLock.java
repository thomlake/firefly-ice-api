package com.fireflydesign.fireflydevice;

public class FDFireflyIceLock {

    public static class Owner {

        public static int encode(char a, char b, char c, char d) { return (a << 24) | (b << 16) | (c << 8) | d; }

        public final static Owner None = new Owner(0);
        public final static Owner BLE = new Owner(encode('B', 'L', 'E', ' '));
        public final static Owner USB = new Owner(encode('U', 'S', 'B', ' '));

        int code;

        public Owner(int code) {
            this.code = code;
        }

        public String name() {
            if (code == None.code) {
                return "None";
            }
            if (code == BLE.code) {
                return "BLE";
            }
            if (code == USB.code) {
                return "USB";
            }
            return Integer.toString(code, 16);
        }

        public int hashCode() {
            return code;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Owner)) {
                return false;
            }
            Owner owner = (Owner)object;
            return owner.code == code;
        }

    }

    public enum Operation {
        None,
        Acquire,
        Release,
    }

    public enum Identifier {
        Sync,
        Update,
    }

    public Identifier identifier;
    public Operation operation;
    public Owner owner;

    public String identifierName() {
        return identifier.name();
    }

    public String operationName() {
        return operation.name();
    }

    public String ownerName() {
        return owner.name();
    }

    public String description() {
        return FDString.format("lock identifier %s operation %s owner %s", identifierName(), operationName(), ownerName());
    }

}
