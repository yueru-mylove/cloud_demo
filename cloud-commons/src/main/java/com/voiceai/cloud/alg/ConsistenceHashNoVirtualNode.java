package com.voiceai.cloud.alg;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年07月21日 16:31:00
 */
public class ConsistenceHashNoVirtualNode {

    private TreeMap<Long, String> realNodes = new TreeMap<>();
    private String[] nodes;

    public ConsistenceHashNoVirtualNode(Map<Long, String> realNodes, String[] nodes) {
        this.nodes = nodes;
    }

    public void initialize() {
        for (String node : nodes) {
            realNodes.put(hash(node, 0), node);
        }
    }

    public String selectNode(String key) {
        Long hashOfKey = hash(key, 0);
        if (!realNodes.containsKey(hashOfKey)) {
            Map.Entry<Long, String> entry = realNodes.ceilingEntry(hashOfKey);
            if (null != entry) {
                return entry.getValue();
            } else {
                return nodes[0];
            }
        } else {
            return realNodes.get(hashOfKey);
        }
    }


    public static Long hash(String nodeName, int number) {
        byte[] digest = md5(nodeName);
        return ((long) (digest[3 + number * 4] & 0xFF) << 24)
                | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                | (digest[number * 4] & 0xFF)
                & 0xFFFFFFFFL;
    }


    public static byte[] md5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(str.getBytes(StandardCharsets.UTF_8));
            return md5.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void printTreeNode() {
        if (null != realNodes && !realNodes.isEmpty()) {
            realNodes.forEach((k, v) -> {
                System.out.println(v + " ==> " + k);
            });
        } else {
            System.out.println("no node available");
        }
    }

}


