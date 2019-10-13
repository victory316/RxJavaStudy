package common;

public class Log {
    public static void it(Object obj) {
        long time = System.currentTimeMillis() - CommonUtils.startTime;
        System.out.println(time + " | " + "value = " + obj);
    }

    public static void i() {
        System.out.println();
    }

    public static void i(String s) {
        System.out.println(getThreadName() + " | " + s);
    }

    public static void v(Object obj) {
        System.out.println(getThreadName() + " | " + obj);
    }

    public static void e(String tag, Object obj) {
        System.out.println(getThreadName() + " | " + tag + " | error = " + obj);
    }

    public static void d(Object obj) {
        System.out.println(getThreadName() + " | debug = " + obj);
    }

    public static void e(Object obj) {
        System.out.println(getThreadName() + " | error = " + obj);
    }

    public static void et(Object obj) {
        long time = System.currentTimeMillis() - CommonUtils.startTime;
        System.out.println(getThreadName() + " | " + time + " | " + "error = " + obj);
    }

    public static String getThreadName() {
        String threadName = Thread.currentThread().getName();
        if (threadName.length() > 30) {
            threadName = threadName.substring(0, 30) + "...";
        }
        return threadName;
    }
}
