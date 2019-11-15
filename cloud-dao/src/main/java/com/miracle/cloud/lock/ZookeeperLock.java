package com.miracle.cloud.lock;

import org.apache.logging.log4j.util.Strings;
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
 * @createTime 2019年07月24日 10:55:00
 */
public class ZookeeperLock implements Watcher {

    private static final String LOCK_SUFFIX = "_lock_";

    private String waitLock;
    private String lockToWait;
    private ZooKeeper keeper;
    private String rootNode;
    private String lockName;
    private Integer sessionTimeout = 30000;
    private CountDownLatch countDownLatch;

    public ZookeeperLock(String host, String rootNode, String lockName) {
        this.rootNode = rootNode;
        if (Strings.isEmpty(lockName) || lockName.contains(LOCK_SUFFIX)) {
            throw new IllegalArgumentException("lock name cannot contains " + LOCK_SUFFIX);
        }
        this.lockName = lockName;
        try {
            this.keeper = new ZooKeeper(host, sessionTimeout, this);
            Stat exists = keeper.exists(rootNode, null);
            if (null == exists) {
                keeper.create(this.rootNode, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }


    public boolean lock() {
        if (this.tryLock()) {
            System.out.println("线程【" + Thread.currentThread().getName() + "】加锁【" + this.waitLock + "】成功");
            return true;
        } else {
            return waitOtherLock(this.lockToWait, this.sessionTimeout);
        }
    }

    public boolean tryLock() {
        try {
            this.waitLock = keeper.create(this.rootNode + "/" + this.lockName + LOCK_SUFFIX, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "线程，创建锁节点" + this.waitLock + "成功");
            List<String> children = keeper.getChildren(rootNode, false);
            List<String> collect = children.stream().filter(c -> c.split(LOCK_SUFFIX)[0].equals(lockName)).sorted().collect(Collectors.toList());
            if (this.waitLock.equals(collect.get(0))) {
                return true;
            }
            String currentLock = this.waitLock.substring(this.waitLock.lastIndexOf("/") + 1);
            int preNodeIndex = Collections.binarySearch(collect, currentLock) - 1;
            this.lockToWait = collect.get(preNodeIndex);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean waitOtherLock(String lockToWait, int sessionTimeout) {
        boolean isLock = false;
        System.out.println(Thread.currentThread().getName() + "==========" + lockToWait);
        String nodeToWait = this.rootNode + "/" + lockToWait;
        System.out.println(Thread.currentThread().getName() + "==========" + nodeToWait);
        try {
            Stat exists = keeper.exists(nodeToWait, true);
            if (null != exists) {
                System.out.println("线程【" + Thread.currentThread().getName() + "】加锁失败，等待锁（" + nodeToWait + "）释放");
                this.countDownLatch = new CountDownLatch(1);
                isLock = countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
                this.countDownLatch = null;
                if (isLock) {
                    System.out.println("线程【" + Thread.currentThread().getName() + "】锁（" + this.waitLock + "）加锁成功， 锁【" + nodeToWait + "】已经释放");
                } else {
                    System.out.println("线程【" + Thread.currentThread().getName() + "】锁【" + this.waitLock + "】加锁失败 ！！");
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
            Stat exists = keeper.exists(this.waitLock, false);
            if (null != exists) {
                keeper.delete(this.waitLock, -1);
                this.waitLock = null;
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            keeper.close();
        }
    }


    @Override
    public void process(WatchedEvent event) {
        if (null != this.countDownLatch && event.getType().equals(Event.EventType.NodeDeleted)) {
            this.countDownLatch.countDown();
        }
    }


    public static void doSomething() {
        System.out.println("线程【" + Thread.currentThread().getName() + "】正在运行...");
    }

    public static void main(String[] args) {
        Runnable runnable = () -> {
            ZookeeperLock lock = null;
            lock = new ZookeeperLock("47.93.219.222:2181", "/locks", "test1");
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
