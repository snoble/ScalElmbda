package snoble.scalambda;

public class ScalambdaShim {

    static {
        System.loadLibrary("go-scalambda");
    }
    public native void start();
    public native void writeResponse(String input);
    public native String readRequest();
}
