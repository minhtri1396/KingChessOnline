package helper;

public class ErrorLogger {
    
    public static void log(Class c, Exception ex) {
        System.out.println(String.format("At %dms occurred error in %s, cause: %s",
                System.currentTimeMillis(),
                c.getName(),
                ex.getMessage())
        );
    }
    
}
