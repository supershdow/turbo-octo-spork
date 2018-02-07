package com.example.benji.chess;


import java.util.LinkedList;

/**
 * Created by benji on 1/2/18.
 */

public class AI {
    Tree boards;
    public boolean isRunning;
    public int depthSpread = 3;
    public AI(Piece[][] board){
        boards = new Tree(board, scoreBoard(board));
        constructChildren(board, depthSpread);

        System.out.println(boards.size());
    }

    public void AIMove(Piece[][] board){
        isRunning = true;
        //boards.clear();
        //boards = new Tree(board, scoreBoard(board));
        boards.root = boards.findBoard(board);
        constructChildren(boards.root.data, depthSpread);

        System.out.println(boards.size());
        //Piece[][] move = minimax().data;
        System.out.println("Start");
        int move = minimax(boards.findBoard(MainActivity.board), depthSpread, -10000, 10000, true);
        LinkedList<Node> children = boards.getDepthList(1);
        for (Node n: children)
            if (n.score == move)
                copyBoard(MainActivity.board, n.data);
        System.out.println("Done");
        MainActivity.self.turn++;
        printBoard(MainActivity.board);
        isRunning = false;
    }

    public int minimax(Node node, int depth, int alpha, int beta, boolean maximizingPlayer){
        Node bestNode = node;
        if (node.firstChild == null || depth == 0) {
            //node.score = scoreBoard(node.data);
            return node.score;
        }
        if (maximizingPlayer){
            bestNode.score = -10000;
            LinkedList<Node> children = node.getChildren();
            for (Node child: children){
                bestNode.score = Math.max(bestNode.score, minimax(child, depth - 1, alpha, beta, false));
                alpha = Math.max(alpha, bestNode.score);
                if (beta <= alpha)
                    break;
            }
            return bestNode.score;
        }
        else {
            bestNode.score = 10000;
            LinkedList<Node> children = node.getChildren();
            if (children.size() == 0)
                constructChildren(node.data, depthSpread);
            for (Node child: children){
                bestNode.score = Math.min(bestNode.score, minimax(child, depth - 1, alpha, beta, true));
                beta = Math.min(beta, bestNode.score);
                if (beta <= alpha)
                    break;
            }
            return bestNode.score;
        }
        //printBoard(bestNode.data);
        //return bestNode;
    }

    /*public Node minimax(){
        Node bestNode = null;
        for (int i = depthSpread; i >= 0; i--){
            LinkedList<Node> boardList = boards.getDepthList(i);
            for (Node n: boardList) {
                if (n.depth == depthSpread){
                    n.score = scoreBoard(n.data);
                } else {
                    LinkedList<Node> children = n.getChildren();
                    if (i % 2 == 0){
                        //System.out.println("Maximizing");
                        Node largest = children.get(0);
                        for (Node child: children){
                            child.score = scoreBoard(child.data);
                            if (child.score > largest.score)
                                largest = child;
                        }
                        n.score = largest.score;
                        if (i == 0)
                            bestNode = largest;
                    } else {
                        //System.out.println("Minimizing");
                        Node smallest = children.get(0);
                        for (Node child: children){
                            child.score = scoreBoard(child.data);
                            if (child.score < smallest.score)
                                smallest = child;
                        }
                        n.score = smallest.score;

                    }
                }
            }
        }
        printBoard(bestNode.data);
        return bestNode;
    }*/

    public void constructChildren(Piece[][] board, int maxDepth){
        LinkedList<Node> children = new LinkedList<>();
        Node parent = boards.findBoard(board);
        if (parent == null)
            return;
        if (parent.depth >= maxDepth)
            return;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                for (int x = 0; x < 8; x++)
                    for (int y = 0; y < 8; y++) {
                        Piece[][] temp = new Piece[8][8];
                        copyBoard(temp, board);
                        if (temp[i][j].validMove(temp, x, y)) {
                            if (parent.depth % 2 == 0 && temp[i][j].whitePiece)
                                continue;
                            else if (parent.depth % 2 == 1 && !temp[i][j].whitePiece)
                                continue;
                            temp[x][y] = temp[i][j];
                            temp[i][j] = new Empty(null, i, j);
                            //if (boards.findBoard(temp) == null)
                            children.add(boards.add(temp, scoreBoard(temp), parent));
                            //System.out.println(children.size());
                            //copyBoard(temp, board);
                        }
                    }
        for (int i = 0; i < children.size(); i++)
            constructChildren(children.get(i).data, maxDepth);
    }

    public void copyBoard(Piece[][] to, Piece[][] from){
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                to[i][j] = from[i][j];

    }

    public int scoreBoard(Piece[][] board){
        int score = 0;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                int toAdd = 0;
                switch (board[i][j].getClass().getSimpleName()){
                    case "Pawn":
                        toAdd = 10;
                        break;
                    case "Knight":
                        toAdd = 30;
                        break;
                    case "Bishop":
                        toAdd = 30;
                        break;
                    case "Rook":
                        toAdd = 50;
                        break;
                    case "Queen":
                        toAdd = 90;
                        break;
                    case "King":
                        toAdd = 900;
                        break;
                }
                if (board[i][j].whitePiece)
                    toAdd *= -1;
                score += toAdd;
            }
        }
        return score;
    }

    public void printBoard(Piece[][] board){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j].getClass().getSimpleName().substring(0, 2));
                System.out.print("|");

            }
            System.out.println();
        }
        System.out.println("\n");
        //System.out.println();

    }

    public Node maxNode(Node n1, Node n2){
        if (n1.score >= n2.score)
            return n1;
        else
            return n2;
    }

    public Node minNode(Node n1, Node n2){
        if (n1.score <= n2.score)
            return n1;
        else
            return n2;
    }
}
