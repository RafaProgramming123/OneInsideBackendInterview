package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Node {
    int value;
    Node left, right;

    Node(int value) {
        this.value = value;
        left = right = null;
    }
}

class BinaryTree {
    private Node root;

    public BinaryTree() {
        root = null;
    }


    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node root, int value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }
        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }
    public void printNodesUpToHeight(int height) {
        if (root == null) {
            return;
        }
        for (int h = 1; h <= height; h++) {
            System.out.print(h+":");
            List<Node> nodesAtHeight = getNodesAtHeight(root, h);
                  if (nodesAtHeight.isEmpty()) {
                System.out.println("False" );
            } else {
                for (Node node : nodesAtHeight) {
                    System.out.print(node.value + " ");
                }
                System.out.println();
            }
        }
    }


    private List<Node> getNodesAtHeight(Node root, int height) {
                List<Node> result = new ArrayList<>();
        if (height < 1) {
            return result;
        }
          Queue<Node> queue = new LinkedList<>();
        queue.add(root);
                int currentHeight = 1;
        while (!queue.isEmpty() && currentHeight <= height) {
                      int levelSize = queue.size();
             for (int i = 0; i < levelSize; i++) {
                        Node currentNode = queue.poll();
                if (currentHeight == height) {
                      result.add(currentNode);
                }
                   if (currentNode.left != null) {
                    queue.add(currentNode.left);
                }
                 if (currentNode.right != null) {
                     queue.add(currentNode.right);
                }
            }
              currentHeight++;
         }
                  return result;
    }
}


public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        BinaryTree tree = new BinaryTree();
        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);
        tree.printNodesUpToHeight(3);

    }
}