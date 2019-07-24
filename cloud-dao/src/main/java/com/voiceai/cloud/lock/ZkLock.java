package com.voiceai.cloud.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年07月21日 14:27:00
 */
public class ZkLock implements Watcher {


    private ZooKeeper zooKeeper;
    private String rootNode;
    private String lockName;
    private String currentLock;
    private String waitLock;
    private CountDownLatch countDownLatch;
    private Integer sessionTimeout = 30000;

    public ZkLock(String host, String rootNode, String lockName) {
        this.rootNode = rootNode;
        this.lockName = lockName;
        try {
            zooKeeper = new ZooKeeper(host, sessionTimeout, this);
            Stat stat = zooKeeper.exists(rootNode, false);
            if (null == stat) {
                zooKeeper.create(rootNode, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

        } catch (IOException | KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean lock() {
        if (this.tryLock()) {
            System.out.println("线程【" + Thread.currentThread().getName() + "】加锁【" + this.currentLock + "】");
            return true;
        } else {
            return waitOtherLock(this.waitLock, this.sessionTimeout);
        }
    }


    public boolean tryLock() {
        String split = "_lock_";
        if (this.lockName.contains(split)) {
            throw new IllegalArgumentException("lock name can't contains '_lock_'");
        }

        try {
            this.currentLock = zooKeeper.create(this.rootNode + "/" + this.lockName + split, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("线程【" + Thread.currentThread().getName()
                    + "】创建锁节点（" + this.currentLock + "）成功，开始竞争...");

            List<String> children = zooKeeper.getChildren(this.rootNode, false);
            List<String> lockNodes = children.stream().filter(c -> c.split(split)[0].equals(this.lockName)).sorted().collect(Collectors.toList());
            String currentLockPath = this.rootNode + "/" + lockNodes.get(0);
            if (this.currentLock.equals(currentLockPath)) {
                return true;
            }
            String currentLockNode = this.currentLock.substring(this.currentLock.lastIndexOf("/") + 1);
            int preNodeIndex = Collections.binarySearch(lockNodes, currentLockNode) - 1;
            this.waitLock = lockNodes.get(preNodeIndex);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean waitOtherLock(String waitLock, int sessionTimeout) {
        boolean isLock = false;
        try {
            String waitNode = this.rootNode + "/" + waitLock;
            Stat exists = zooKeeper.exists(waitNode, true);
            if (null != exists) {
                System.out.println("线程【" + Thread.currentThread().getName() + "】加锁失败，等待锁（" + waitNode + "）释放");
                this.countDownLatch = new CountDownLatch(1);
                isLock = this.countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
                this.countDownLatch = null;
                if (isLock) {
                    System.out.println("线程【" + Thread.currentThread().getName() + "】锁（" + this.currentLock + "）加锁成功， 锁【" + waitNode + "】已经释放");
                } else {
                    System.out.println("线程【" + Thread.currentThread().getName() + "】锁【" + this.currentLock + "】加锁失败 ！！");
                }
            } else {
                isLock = true;
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return isLock;
    }


    public void unLock() throws InterruptedException {
        try {
            Stat exists = zooKeeper.exists(this.currentLock, false);
            if (null != exists) {
                System.out.println("线程【" + Thread.currentThread().getName() + "】释放锁 " + this.currentLock + "############################################");
                zooKeeper.delete(this.currentLock, -1);
                this.currentLock = null;
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            zooKeeper.close();
        }
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        if (null != this.countDownLatch && watchedEvent.getType() == Event.EventType.NodeDeleted) {
            this.countDownLatch.countDown();
        }
    }

    public static void doSomething() {
        System.out.println("线程【" + Thread.currentThread().getName() + "】正在运行...");
    }

    public static void main(String[] args) {
        Runnable runnable = () -> {
            ZkLock lock = null;
            lock = new ZkLock("47.93.219.222:2181", "/locks", "test1");
            if (lock.lock()) {
                doSomething();
                try {
                    Thread.sleep(1000);
                    lock.unLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
    }

}
