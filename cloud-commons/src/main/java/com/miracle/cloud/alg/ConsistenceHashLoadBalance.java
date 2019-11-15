package com.miracle.cloud.alg;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年07月22日 10:58:00
 */
public class ConsistenceHashLoadBalance {

    private TreeMap<Long, String> virtualNodes = new TreeMap<>();
    private LinkedList<String> nodes;
    /**
     * 每个真实节点的虚拟节点数
     */
    private final int repliCnt;

    public ConsistenceHashLoadBalance(LinkedList<String> nodes, int repliCnt) {
        this.nodes = nodes;
        this.repliCnt = repliCnt;
    }

    public void initialization() {
        for (String node : nodes) {
            for (int i = 0; i < repliCnt / 4; i++) {
                String name = getNodeNameByIndex(node, i);
                for (int j = 0; j < 4; j++) {
                    virtualNodes.put(ConsistenceHashNoVirtualNode.hash(name, j), node);
                }
            }
        }
    }

    private String getNodeNameByIndex(String nodeName, int index) {
        return nodeName + "&&" + index;
    }

    public String selectNode(String key) {
        Long hashKey = ConsistenceHashNoVirtualNode.hash(key, 0);
        if (!virtualNodes.containsKey(hashKey)) {
            Map.Entry<Long, String> entry = virtualNodes.ceilingEntry(hashKey);
            if (null == entry) {
                return nodes.getFirst();
            } else {
                return entry.getValue();
            }

        } else {
            return virtualNodes.get(hashKey);
        }
    }

    public void addNode(String node) {
        nodes.add(node);
        String nodeName = getNodeNameByIndex(node, 0);
        for (int i = 0; i < repliCnt / 4; i++) {
            for (int j = 0; j < 4; j++) {
                virtualNodes.put(ConsistenceHashNoVirtualNode.hash(nodeName, j), node);
            }
        }
    }


    public void remove(String node) {
        nodes.remove(node);
        String nodeName = getNodeNameByIndex(node, 0);
        for (int i = 0; i < repliCnt / 4; i++) {
            for (int j = 0; j < 4; j++) {
                virtualNodes.remove(ConsistenceHashNoVirtualNode.hash(nodeName, j), node);
            }
        }
    }
}


