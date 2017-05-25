package helper;

public class Converter {
    
    public static byte[] toBytes(String value) {
        return value.getBytes();
    }
    
    public static byte[] toBytes(double value) {
        return toBytes(Double.doubleToLongBits(value));
    }
    
    public static byte[] toBytes(long value) {
        return new byte[] {
            (byte)(value >>> 56),
            (byte)(value >>> 48),
            (byte)(value >>> 40),
            (byte)(value >>> 32),
            (byte)(value >>> 24),
            (byte)(value >>> 16),
            (byte)(value >>> 8),
            (byte)value
        };
    }
    
    public static byte[] toBytes(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value
        };
    }
    
    public static byte[] toBytes(short value) {
        return new byte[] {
                (byte)(value >>> 8),
                (byte)value
        };
    }
    
    public static String toString(byte[] bytes) {
        return new String(bytes);
    }
    
    public static double toDouble(byte[] bytes) {
        return Double.longBitsToDouble(toLong(bytes));
    }
    
    public static long toLong(byte[] bytes) {
        long value = 0;
        
        for (byte b : bytes) {
            value <<= 8;
            value |= (b & 0xFF);
        }
        
        return value;
    }
    
    public static int toInt(byte[] bytes) {
        int value = 0;
        
        for (byte b : bytes) {
            value <<= 8;
            value |= (b & 0xFF);
        }
        
        return value;
    }
    
    public static short toShort(byte[] bytes) {
        short value = 0;
        
        for (byte b : bytes) {
            value <<= 8;
            value |= (b & 0xFF);
        }
        
        return value;
    }
    
}
