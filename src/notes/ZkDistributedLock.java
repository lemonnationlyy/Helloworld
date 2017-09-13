package notes;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZkDistributedLock implements Lock {

    private static final String root = "/lock";

    private static final String lockName = "/temp";

    private CountDownLatch countDownLatch;

    private String cur;
    private String bef;

    private ZkClient client;

    public ZkDistributedLock() {
        client = ZkUtils.getInstance();
        init();
    }

    private void init() {
        try {
            if (client.exists(root)) {
                client.createPersistent(root, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lock() {
        if (!tryLock()) {
            waitLock();
            lock();
        } else {
            System.out.println("DistributedLock get!");
        }
    }

    private void waitLock() {
        IZkDataListener listener = new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {

            }

            public void handleDataDeleted(String s) throws Exception {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        };
        client.subscribeDataChanges(bef, listener);
        if (client.exists(bef)) {
            countDownLatch = new CountDownLatch(1);
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.unsubscribeDataChanges(bef, listener);
    }

    public void lockInterruptibly() throws InterruptedException {

    }

    public boolean tryLock() {
        if (cur == null || cur == "") {
            cur = client.createEphemeralSequential(root + lockName, "".getBytes());
        }
        List<String> children = client.getChildren(root);
        Collections.sort(children);
        if (cur.equals(root + "/" + children.get(0))) {
            return true;
        } else {
            int before = Collections.binarySearch(children, cur.substring(6));
            bef = root + "/" + children.get(before - 1);
        }
        return false;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public void unlock() {
        client.delete(cur);
    }

    public Condition newCondition() {
        return null;
    }
}
