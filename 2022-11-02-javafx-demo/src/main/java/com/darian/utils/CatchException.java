package com.darian.utils;

import java.util.concurrent.Callable;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  17:41
 */
public class CatchException {

    public static void run(ExceptionRunnable exceptionRunnable) {
        try {
            exceptionRunnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T call(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
