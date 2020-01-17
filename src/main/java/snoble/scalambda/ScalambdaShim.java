package snoble.scalambda;

public class ScalambdaShim {

    static {
        String javaLibPath = System.getProperty("java.library.path");
	System.out.println(javaLibPath);
        System.loadLibrary("go-scalambda");
    }
    public native void start();
    public native void writeResponse(String input);
    public native String readRequest();
}
