package com.example.benji.chess;

import java.util.LinkedList;

/**
 * Created by benji on 1/3/18.
 */

public class Node{
    public Piece[][] data;
    public int score;
    public Node firstChild;
    public Node nextSibling;
    public int depth;

    public Node(){

    }

    public String toString(){
        String out = depth + " " + score + " | ";
        if (nextSibling != null)
            out += nextSibling.toString();
        if (firstChild != null)
            out += "\n" + firstChild.toString();
        return out;
    }

    public int getMaxDepth(){
        if (firstChild == null && nextSibling == null)
            return depth;
        else if (firstChild != null && nextSibling != null)
            return Math.max(firstChild.getMaxDepth(), nextSibling.getMaxDepth());
        else if (firstChild != null)
            return firstChild.getMaxDepth();
        else if (nextSibling != null)
            return Math.max(depth, nextSibling.getMaxDepth());
        else
            return 0;
    }

    public LinkedList<Node> getChildren(){
        LinkedList<Node> children = new LinkedList<>();
        if (firstChild == null)
            return children;
        children.add(firstChild);
        Node n = firstChild;
        while (n.nextSibling != null) {
            children.add(n.nextSibling);
            n = n.nextSibling;
        }
        return children;
    }
}