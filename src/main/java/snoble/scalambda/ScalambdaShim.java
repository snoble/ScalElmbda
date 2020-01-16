package snoble.scalambda;

public class ScalambdaShim {

    static {
        System.loadLibrary("Scalambda");
    }
    public native void start();
    public native void writeResponse(String input);
    public native String readRequest();
}
