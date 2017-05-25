package helper;

// We use this class to wrap message to byte before send it to server
public class Wrapper {
    
    public static final Wrapper INSTANCE = new Wrapper();
    
    private Wrapper() {}
    
    // Data type of 'values' param can be byte or byte[] (please don't use Byte or Byte[])
    // This method can be used for [byte, byte[], byte] as a param,
    // but not [byte[byte[]]] or more
    // If you wanna update this method to input [byte[byte[]]] as param,
    // you can rebuild this method and use recursion to do it.
    public byte[] wrap(Object[] values) {
        int[] info = new int[values.length];
        int nBytes = this.calcBytesOf(values, info);
        byte[] pack = new byte[nBytes + 1]; // [info][0][values]
        
        // Bind information to head of package
        // The information is about size of each value in the package
        int iPack = this.bind(pack, info);
        
        for (Object value : values) {
            if (value instanceof byte[]) {
                byte[] bytes = (byte[]) value;
                for (byte b : bytes) {
                    pack[iPack++] = b;
                }
            } else {
                pack[iPack++] = (byte)value;
            }
        }
        
        return pack;
    }
    
    // This method will give us specific sum of bytes need for 'values' param
    // And set 'info' param with the number of bytes of each part in a package
    // (we will split 'values' param into part of the package later)
    private int calcBytesOf(Object[] values, int[] info) {
        int countBytes = 0;
        
        int iInfo = 0;
        for (Object value : values) {
            if (value instanceof byte[]) {
                info[iInfo] = ((byte[]) value).length;
                countBytes += Math.ceil(info[iInfo] / 126.0) + info[iInfo++];
            } else {
                info[iInfo++] = 1;
                countBytes += 2;
            }
        }
        
        return countBytes;
    }
    
    private int bind(byte[] pack, int[] info) {
        int iPack = 0, partSize;
        
        for (int size : info) {
            if (size > 126) {
                partSize = size;
                while (partSize > 126) {
                    pack[iPack++] = (byte)(126 | 0x80); // 0x80 means it still has other parts
                    partSize -= 126;
                }
                pack[iPack++] = (byte)partSize;
            } else {
                pack[iPack++] = (byte)size;
            }
        }
        
        pack[iPack++] = 0; // end of information sign
        
        return iPack;
    }
    
    public Object[] unwrap(byte[] pack) {
        int[] info = new int[calcInfoSize(pack)];
        Object[] message = new Object[info.length];
        int iPack = this.unbind(pack, info);
        
        byte[] part;
        int iMessage = 0, iPart;
        for (int size : info) {
            if (size > 1) {
                iPart = 0;
                part = new byte[size];
                message[iMessage++] = part;
                while (iPart < size) {
                    part[iPart++] = pack[iPack++];
                }
            } else {
                message[iMessage++] = pack[iPack++];
            }
        }
        
        return message;
    }
    
    private int calcInfoSize(byte[] pack) {
        int count = 0;
        
        for (byte b : pack) {
            if (b == 0) { break; }
            if ((b & 0x80) == 0) {
                ++count;
            }
        }
        
        return count;
    }
    
    // Get information of pack param and assign it to info param
    private int unbind(byte[] pack, int[] info) {
        int iInfo = 0, iPack = 0;
        
        while (pack[iPack] != 0) {
            if ((pack[iPack] & 0x80) == 0) {
                info[iInfo] = pack[iPack++];
            } else {
                while ((pack[iPack] & 0x80) != 0) {
                    info[iInfo] += (pack[iPack++] & 0x7F); // (pack[iPack++] & 0x7F) is 126
                }
                info[iInfo] += pack[iPack++];
            }
            ++iInfo;
        }
        
        return iPack + 1;
    }
    
}
