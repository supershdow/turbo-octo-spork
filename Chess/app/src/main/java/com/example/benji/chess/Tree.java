package com.example.benji.chess;


import java.util.LinkedList;

/**
 * Created by benji on 1/2/18.
 */

public class Tree {
    public Node root;
    LinkedList<Node> allNodes;


    public Tree(Piece[][] board, int score){
        root = new Node();
        allNodes = new LinkedList<>();
        allNodes.add(root);
        root.data = board;
        root.score = score;
        root.depth = 0;

    }

    public Node add(Piece[][] board, int score, Node parent){
        Node child = new Node();
        if (parent.firstChild == null)
            parent.firstChild = child;
        else {
            Node sib = parent.firstChild;
            while (sib.nextSibling != null)
                sib = sib.nextSibling;
            sib.nextSibling = child;
        }
        child.data = new Piece[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                child.data[i][j] = board[i][j];
        child.score = score;
        child.depth = parent.depth + 1;
        allNodes.add(child);
        return child;
    }

    public LinkedList<Node> getDepthList(int depth){
        LinkedList<Node> nodes = new LinkedList<>();
        for (Node n : allNodes)
            if (n.depth == depth) {
                nodes.add(n);
            }

        return nodes;
    }

    public String toString(){
        return root.toString();
    }

    public int getMaxDepth(){
        return root.getMaxDepth();
    }

    public Node findBoard(Piece[][] board){
        if (root.data.equals(board))
            return root;
        return findBoard(root.firstChild, board);
    }

    private Node findBoard(Node n, Piece[][] board){
        //System.out.println(n);
        if (n == null)
            return null;
        else if (n.data.equals(board))
            return n;
        Node node = findBoard(n.nextSibling, board);
        if (node != null && node.data.equals(board))
            return node;
        else
            return findBoard(n.firstChild, board);
    }

    public int size(){
        return size(root);

    }

    private int size(Node n){
        if (n == null)
            return 0;
        return 1 + size(n.nextSibling) + size(n.firstChild);
    }

    public void clear(){
        root = null;
        allNodes = new LinkedList<>();
    }



}
