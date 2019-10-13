package common;

public class CommonUtils {

    public static long startTime;

    public static final String GITHUB_ROOT =
            "https://raw.githubusercontent.com/yudong80/reactivejava/master";

    public static void exampleStart() {
        startTime = System.currentTimeMillis();
    }

    public static void sleep(int millis) {
        try{
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getShape(String obj) {
        if (obj == null || obj.equals("")) return "NO-SHAPE";
        if (obj.endsWith("-H")) return "HEXAGON";
        if (obj.endsWith("-O")) return "OCTAGON";
        if (obj.endsWith("-R")) return "RECTANGLE";
        if (obj.endsWith("-T")) return "TRIANGLE";
        if (obj.endsWith("<>")) return "DIAMOND";
        return "BALL";
    }
}
