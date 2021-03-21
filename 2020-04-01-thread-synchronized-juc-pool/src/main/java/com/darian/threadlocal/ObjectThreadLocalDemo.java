package com.darian.threadlocal;

/***
 *
 *
 * @author <a href="1934849492@qq.com">Darian</a> 
 * @date 2020/1/31  16:46
 */
public class ObjectThreadLocalDemo {
    static ThreadLocal<MockObject> mockLocal = ThreadLocal.withInitial(() -> new MockObject());

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                MockObject mockObject = mockLocal.get();
                mockObject.incr();

                Println.println(mockObject.getI());
            }).start();
        }
    }

    static class MockObject {
        private int i;

        public void incr() {
            i++;
        }

        public int getI() {
            return i;
        }
    }
    // thredId: [ 18 ] thredName: [ Thread-4 ]
    //   - 1
    //thredId: [ 14 ] thredName: [ Thread-0 ]
    //   - 1
    //thredId: [ 15 ] thredName: [ Thread-1 ]
    //   - 1
    //thredId: [ 17 ] thredName: [ Thread-3 ]
    //   - 1
    //thredId: [ 16 ] thredName: [ Thread-2 ]
    //   - 1
}
