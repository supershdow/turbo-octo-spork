package com.example.benji.chess;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button start, settings1, settings2;
    boolean inGame, inSettings;
    int height, width;
    android.view.ViewGroup.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    RelativeLayout lay;
    TextView output;
    public int turn = 0;

    AI ai;
    Handler handler = new Handler();

    public static Piece[][] board;
    public static Piece selected;
    public static MainActivity self;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button)findViewById(R.id.start);
        settings1 = (Button)findViewById(R.id.settings1);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        height = metrics.heightPixels;
        width = metrics.widthPixels;

        self = this;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGame();
            }
        });

        settings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSettings();
            }
        });

        board = new Piece[8][8];

    }

    public void gameLoop(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (ai.isRunning)
                    return;
                if (turn % 2 == 0){

                } else {
                    System.out.println("AI");
                    ai.AIMove(board);
                }
            }
        });
        t.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameLoop();
            }
        }, 1000);
        reload();
    }

    public void loadSettings(){
        setContentView(R.layout.settings_main);
        inSettings = true;
        inGame = false;

    }

    public void loadGame(){
        setContentView(R.layout.game_main);
        output = (TextView)findViewById(R.id.output);
        lay = (RelativeLayout)findViewById(R.id.gamelay);
        //System.out.println(lay);
        lp.width = width / 8;
        lp.height = width / 8;
        settings2 = (Button)findViewById(R.id.settings2);
        settings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSettings();
            }
        });
        for (int i = 2; i < 7; i++)
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Empty(new ImageView(getApplicationContext()), i, j);
                if ((i + j) % 2 == 0)
                    board[i][j].image.setImageResource(R.drawable.lightsquare);
                else
                    board[i][j].image.setImageResource(R.drawable.darksquare);
                board[i][j].image.setVisibility(View.VISIBLE);
                board[i][j].image.setX(j * width / 8);
                board[i][j].image.setY(i * width / 8);
                board[i][j].image.setLayoutParams(lp);
                lay.addView(board[i][j].image);
            }
        loadPawns();
        loadRooks();
        loadKnights();
        loadBishops();
        loadQueens();
        loadKings();
        inSettings = false;
        inGame = true;
        ai = new AI(board);
        gameLoop();

    }

    void loadPawns(){
        //Load white Pawns
        for (int i = 0; i < 8; i++){
            board[6][i] = new Pawn(new ImageView(getApplicationContext()), 6, i);
            board[6][i].whitePiece = true;
            if ((i) % 2 == 0)
                board[6][i].image.setImageResource(R.drawable.lightpawnlightsquare);
            else
                board[6][i].image.setImageResource(R.drawable.lightpawndarksquare);
            board[6][i].image.setVisibility(View.VISIBLE);
            board[6][i].image.setX(i * width / 8);
            board[6][i].image.setY(6 * width / 8);
            board[6][i].image.setLayoutParams(lp);
            lay.addView(board[6][i].image);
        }
        //Load black pawns
        for (int i = 0; i < 8; i++){
            board[1][i] = new Pawn(new ImageView(getApplicationContext()), 1, i);
            if ((i + 1) % 2 == 0)
                board[1][i].image.setImageResource(R.drawable.darkpawnlightsquare);
            else
                board[1][i].image.setImageResource(R.drawable.darkpawndarksquare);
            board[1][i].image.setVisibility(View.VISIBLE);
            board[1][i].image.setX(i * width / 8);
            board[1][i].image.setY(1 * width / 8);
            board[1][i].image.setLayoutParams(lp);
            lay.addView(board[1][i].image);
        }
    }

    void loadRooks(){
        //Load white rooks
        for (int i = 0; i < 8; i += 7) {
            board[7][i] = new Rook(new ImageView(getApplicationContext()), 7, i);
            board[7][i].whitePiece = true;
            if ((i + 7) % 2 == 0)
                board[7][i].image.setImageResource(R.drawable.lightrooklightsquare);
            else
                board[7][i].image.setImageResource(R.drawable.lightrookdarksquare);
            board[7][i].image.setVisibility(View.VISIBLE);
            board[7][i].image.setX(i * width / 8);
            board[7][i].image.setY(7 * width / 8);
            board[7][i].image.setLayoutParams(lp);
            lay.addView(board[7][i].image);
        }
        //Load black rooks
        for (int i = 0; i < 8; i += 7) {
            board[0][i] = new Rook(new ImageView(getApplicationContext()), 0, i);
            if ((i) % 2 == 0)
                board[0][i].image.setImageResource(R.drawable.darkrooklightsquare);
            else
                board[0][i].image.setImageResource(R.drawable.darkrookdarksquare);
            board[0][i].image.setVisibility(View.VISIBLE);
            board[0][i].image.setX(i * width / 8);
            board[0][i].image.setY(0);
            board[0][i].image.setLayoutParams(lp);
            lay.addView(board[0][i].image);
        }
    }

    void loadKnights(){
        //Load white rooks
        for (int i = 1; i < 8; i += 5) {
            board[7][i] = new Knight(new ImageView(getApplicationContext()), 7, i);
            board[7][i].whitePiece = true;
            if ((i + 7) % 2 == 0)
                board[7][i].image.setImageResource(R.drawable.lightknightlightsquare);
            else
                board[7][i].image.setImageResource(R.drawable.lightknightdarksquare);
            board[7][i].image.setVisibility(View.VISIBLE);
            board[7][i].image.setX(i * width / 8);
            board[7][i].image.setY(7 * width / 8);
            board[7][i].image.setLayoutParams(lp);
            lay.addView(board[7][i].image);
        }
        //Load black rooks
        for (int i = 1; i < 8; i += 5) {
            board[0][i] = new Knight(new ImageView(getApplicationContext()), 0, i);
            if ((i) % 2 == 0)
                board[0][i].image.setImageResource(R.drawable.darkknightlightsquare);
            else
                board[0][i].image.setImageResource(R.drawable.darkknightdarksquare);
            board[0][i].image.setVisibility(View.VISIBLE);
            board[0][i].image.setX(i * width / 8);
            board[0][i].image.setY(0);
            board[0][i].image.setLayoutParams(lp);
            lay.addView(board[0][i].image);
        }
    }

    void loadBishops(){
        //Load white rooks
        for (int i = 2; i < 8; i += 3) {
            board[7][i] = new Bishop(new ImageView(getApplicationContext()), 7, i);
            board[7][i].whitePiece = true;
            if ((i + 7) % 2 == 0)
                board[7][i].image.setImageResource(R.drawable.lightbishoplightsquare);
            else
                board[7][i].image.setImageResource(R.drawable.lightbishopdarksquare);
            board[7][i].image.setVisibility(View.VISIBLE);
            board[7][i].image.setX(i * width / 8);
            board[7][i].image.setY(7 * width / 8);
            board[7][i].image.setLayoutParams(lp);
            lay.addView(board[7][i].image);
        }
        //Load black rooks
        for (int i = 2; i < 8; i += 3) {
            board[0][i] = new Bishop(new ImageView(getApplicationContext()), 0, i);
            if ((i) % 2 == 0)
                board[0][i].image.setImageResource(R.drawable.darkbishoplightsquare);
            else
                board[0][i].image.setImageResource(R.drawable.darkbishopdarksquare);
            board[0][i].image.setVisibility(View.VISIBLE);
            board[0][i].image.setX(i * width / 8);
            board[0][i].image.setY(0);
            board[0][i].image.setLayoutParams(lp);
            lay.addView(board[0][i].image);
        }

    }

    void loadQueens(){
        //Load white queen
        board[7][3] = new Queen(new ImageView(getApplicationContext()), 7, 3, true);
        board[7][3].whitePiece = true;
        board[7][3].image.setImageResource(R.drawable.lightqueenlightsquare);
        board[7][3].image.setVisibility(View.VISIBLE);
        board[7][3].image.setX(3 * width / 8);
        board[7][3].image.setY(7 * width / 8);
        board[7][3].image.setLayoutParams(lp);
        lay.addView(board[7][3].image);

        //Load black queen
        board[0][3] = new Queen(new ImageView(getApplicationContext()), 0, 3, false);
        board[0][3].image.setImageResource(R.drawable.darkqueendarksquare);
        board[0][3].image.setVisibility(View.VISIBLE);
        board[0][3].image.setX(3 * width / 8);
        board[0][3].image.setY(0);
        board[0][3].image.setLayoutParams(lp);
        lay.addView(board[0][3].image);
    }

    void loadKings(){
        //Load white king
        board[7][4] = new King(new ImageView(getApplicationContext()), 7, 4);
        board[7][4].whitePiece = true;
        board[7][4].image.setImageResource(R.drawable.lightkingdarksquare);
        board[7][4].image.setVisibility(View.VISIBLE);
        board[7][4].image.setX(4 * width / 8);
        board[7][4].image.setY(7 * width / 8);
        board[7][4].image.setLayoutParams(lp);
        lay.addView(board[7][4].image);

        //Load black king
        board[0][4] = new King(new ImageView(getApplicationContext()), 0, 4);
        board[0][4].image.setImageResource(R.drawable.darkkinglightsquare);
        board[0][4].image.setVisibility(View.VISIBLE);
        board[0][4].image.setX(4 * width / 8);
        board[0][4].image.setY(0);
        board[0][4].image.setLayoutParams(lp);
        lay.addView(board[0][4].image);

    }


    public void reload(){
        lay.removeAllViews();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j] == null){
                    board[i][j] = new Empty(new ImageView(getApplicationContext()), i, j);
                    if ((i + j) % 2 == 0)
                        board[i][j].image.setImageResource(R.drawable.lightsquare);
                    else
                        board[i][j].image.setImageResource(R.drawable.darksquare);
                    board[i][j].image.setVisibility(View.VISIBLE);
                    board[i][j].image.setX(j * width / 8);
                    board[i][j].image.setY(i * width / 8);
                    board[i][j].image.setLayoutParams(lp);
                    lay.addView(board[i][j].image);

                } else {
                    board[i][j].row = i;
                    board[i][j].col = j;
                    if (board[i][j].image == null) {
                        board[i][j] = new Empty(new ImageView(getApplicationContext()), i, j);
                        if ((i + j) % 2 == 0)
                            board[i][j].image.setImageResource(R.drawable.lightsquare);
                        else
                            board[i][j].image.setImageResource(R.drawable.darksquare);


                    }
                    board[i][j].checkColor();
                    board[i][j].image.setVisibility(View.VISIBLE);
                    board[i][j].image.setX(j * width / 8);
                    board[i][j].image.setY(i * width / 8);
                    board[i][j].image.setLayoutParams(lp);
                    lay.addView(board[i][j].image);
                }

    }
}
