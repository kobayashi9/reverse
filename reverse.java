import java.awt.*;
import java.awt.event.*;

public class reverse implements MouseListener {
    reversemodel m;
    reverseview    v;
    reverse() {
        m = new reversemodel();
        v = new reverseview(m);
        v.addMouseListener(this);
        Frame f = new Frame();
        f.add(v);f.pack();f.setVisible(true);
    }
    public void mouseExited(MouseEvent e){}     // マウスが画面外に出た: 何もしない
    public void mouseEntered(MouseEvent e){}    // マウスが画面内に入った: 何もしない
    public void mousePressed(MouseEvent e){}    // マウスボタンが押された: 何もしない
    public void mouseReleased(MouseEvent e){} // マウスボタンが離された: 何もしない
    public void mouseClicked(MouseEvent e){     // マウスボタンがクリックされた
        int bx = v.getBrdX(e);        // マウスが指す盤上の x座標を取得
        int by = v.getBrdY(e);        //        同上 y座標
        m.play(bx,by);        // 盤上にコマ(黒または白)を置く
        if (m.game != -1) { gameOver(); }    // ゲーム終了なら、gameOver を呼ぶ
        v.repaint();
    }

    private void gameOver() {
        // ゲーム終了時の処理: メッセージを設定する
        if (m.game == 1) {        // 黒の勝ち
            v.setMessage("黒 win");
        } else if (m.game == 2) {        // 白の勝ち
            v.setMessage("白 win");
        } else {                                     // 引分け
            v.setMessage("Draw");
        }
    }

    public static void main(String[] args) {
        reverse bw =new reverse();
    }
}

class reversemodel {
    int board[][];
    int turn;    //現在のプレイヤ(1:黒,2:白)
    int game;    //gameの状況(-1:ゲーム続行，0：引き分け，1:黒の勝ち，2:白の勝ち)
    int temp;

    reversemodel() {
        board = new int[8][8];
        for(int y=0 ; y<8; y++) {
            for(int x=0 ; x<8 ; x++) {
                board[3][3] = 1;
                board[4][4] = 1;
                board[3][4] = 2;
                board[4][3] = 2;
            }
        }
        turn=1;         //黒を先手とする
        game=-1;        //ゲーム続行中が初期状態
     }

    public boolean isValidMove(int x, int y) {
        // 盤上の (x,y) に置けるかどうか判定する
        if ((game != -1) ||    (x < 0) || (7 < x) || (y < 0) || (7 < y)) { // 盤外には置けない
            return false;
        }
        return( board[x][y] == 0 );                 // 空マスなら置ける
    }

    public void play(int x, int y) {
        //盤上の(x,y)に現在のプレイヤが置く
        if(!isValidMove(x,y)) return;        //置けなければ無視する
        int u=0;                //パス判定
        for(int s=0 ; s<8 ; s++) {
            for(int t=0 ; t<8 ; t++) {
                if(board[s][t] == 0){
                    //↓　上
                    int d=0;        // 異なる駒をカウント
                    while(t>1 && board[s][t-d-1] == 3-turn) {
                        d++;
                        if(d>0 && t-d>0 && board[s][t-d-1]==turn){
                            u=1;
                            break;
                        }
                        if(t-d==0){
                            break;
                        }
                    }
                    //↓　下
                    d=0;
                    while(t<6 && board[s][t+d+1] == 3-turn){
                        d++;
                        if(d>0 && t+d<7 && board[s][t+d+1]==turn){
                            u=1;
                            break;
                        }
                        if(t+d==7){
                            break;
                        }
                    }
                    //↓　左
                    d=0;
                    while(s>1 && board[s-d-1][t] == 3-turn){
                        d++;
                        if(d>0 && s-d>0 && board[s-d-1][t]==turn){
                            u=1;
                            break;
                        }
                        if(s-d==0){
                            break;
                        }
                    }
                    //↓　右
                    d=0;
                    while(s<6 && board[s+d+1][t] == 3-turn){
                        d++;
                        if(d>0 && s+d<7 && board[s+d+1][t]==turn){
                            u=1;
                            break;
                        }
                        if(s+d==7){
                            break;
                        }
                    }
                    //↓　右斜め上
                    d=0;
                    while(s<6 && t>1 && board[s+d+1][t-d-1] == 3-turn){
                        d++;
                        if(d>0 && s+d<7 && t-d>0 && board[s+d+1][t-d-1]==turn){
                            u=1;
                            break;
                        }
                        if(s+d==7 || t-d==0){
                            break;
                        }
                    }
                    //↓　右斜め下
                    d=0;
                    while(s<6 && t<6 && board[s+d+1][t+d+1] == 3-turn){
                        d++;
                        if(d>0 && s+d<7 && t+d<7 && board[s+d+1][t+d+1]==turn){
                            u=1;
                            break;
                        }
                        if(s+d==7 || t+d==7){
                            break;
                        }
                    }
                    //↓　左斜め上
                    d=0;
                    while(s>1 && t>1 && board[s-d-1][t-d-1] == 3-turn){
                        d++;
                        if(d>0 && s-d>0 && t-d>0 && board[s-d-1][t-d-1]==turn){
                            u=1;
                            break;
                        }
                        if(s-d==0 || t-d==0){
                            break;
                        }
                    }
                    //↓　左斜め下
                    d=0;
                    while(s>1 && t<6 && board[s-d-1][t+d+1] == 3-turn){
                        d++;
                        if(d>0 && s-d>0 && t+d<7 && board[s-d-1][t+d+1]==turn ){
                        u=1;
                        break;
                    }
                    if(s-d==0 || t+d==7){
                        break;
                    }
                }
            }
        }
    }
    if(u==0){
        turn = 3-turn;
    }else if(u==1){
    board[x][y]=turn;                            //置く
    }
        int i=0;
        int temp2;
        temp2 = 0;
        //駒の反転ついて
        //　↓上方向の反転
        if(y>1 && board[x][y] == turn){
            i=0;
            while(board[x][y-i-1] == 3-turn && y>1) {
                i++;
                if(y-i==1){
                    break;
                }
            }
            if (i != 0 && board[x][y-i-1] == turn) {
                temp2=1;
                for(int temp=1 ; temp<i+1 ; temp++) {
                    board[x][y-temp] = turn;
                }
            }
        }
        //　↓下方向の反転
        if(y<6 && board[x][y] == turn){
            i=0;
            while(board[x][y+i+1] == 3-turn && y<6){
                i++;
                if(y+i==6){
                    break;
                }
            }
            if (i != 0 && board[x][y+i+1] == turn) {
                temp2=1;
                for(int temp=1 ; temp<i+1 ; temp++) {
                    board[x][y+temp] = turn;
                }
            }
        }
        //　↓左方向の反転
        if(x>1 && board[x][y] == turn){
            i=0;
            while(board[x-i-1][y] == 3-turn && x>0){
                i++;
                if(x-i==1){
                    break;
                }
            }
            if (i != 0 && board[x-i-1][y]==turn) {
                temp2=1;
                for(int temp=1 ; temp<i+1 ; temp++) {
                    board[x-temp][y] = turn;
                }
            }
        }
        //　↓右方向の反転
        if(x<6 && board[x][y] == turn){
            i=0;
            while(board[x+i+1][y] == 3-turn && x<6){
                i++;
                if(x+i==6){
                    break;
                }
            }
            if (i != 0 && board[x+i+1][y]==turn) {
                temp2=1;
                for(int temp=1 ; temp<i+1 ; temp++) {
                    board[x+temp][y] = turn;
                }
            }
        }
        //　↓右斜め上の反転
        if(x<6 && y>1 && board[x][y] == turn){
            i=0;
            while(x<6 && y>1 && board[x+i+1][y-i-1] == 3-turn){
                i++;
                if(x+i==6 || y-i==1){
                    break;
                }
            }
            if (i != 0 && board[x+i+1][y-i-1]==turn) {
                temp2=1;
                for(int temp=1 ; temp<i+1 ; temp++) {
                    board[x+temp][y-temp] = turn;
                }
            }
        }
        //　↓右斜め下の反転
        if(x<6 && y<6 && board[x][y] == turn){
            i=0;
            while(board[x+i+1][y+i+1] == 3-turn && x<6 && y<6){
                i++;
                if(x+i==6 || y+i==6){
                    break;
                }
            }
            if (i != 0 && board[x+i+1][y+i+1]==turn) {
                temp2=1;
                for(int temp=1 ; temp<i+1 ; temp++) {
                    board[x+temp][y+temp] = turn;
                }
            }
        }
        //　↓左斜め上の反転
        if(x>1 && y>1 && board[x][y] == turn){
            i=0;
            while(board[x-i-1][y-i-1] == 3-turn && x>0 && y>0){
                i++;
                if(x-i==1 || y-i==1){
                    break;
                }
            }
            if (i != 0 && board[x-i-1][y-i-1]==turn) {
                temp2=1;
                for(int temp=1 ; temp<i+1 ; temp++) {
                    board[x-temp][y-temp] = turn;

                }
            }
        }
        //　↓左斜め下の反転
        if(x>1 && y<6 && board[x][y] == turn){
            i=0;
            while(board[x-i-1][y+i+1] == 3-turn && x>1 && y<6){
                i++;
                if(x-i==1 || y+i==6)
                break;
            }
            if (i != 0 && board[x-i-1][y+i+1]==turn) {
                temp2=1;
                for(int temp=1 ; temp<i+1 ; temp++) {
                    board[x-temp][y+temp] = turn;
                }
            }
        }

        if (temp2==0){
            board[x][y]=0;
        } else {
            turn = 3-turn;
        }

        judgeGame();                                         //ゲーム終了判定
    }

    public int judgeGame() {
        // ゲームの終了判定
        //     次の 4通りのうちのいずれかを、game に設定して、返す:
        //     - ゲーム続行:        -1
        //     - ゲーム終了で引分け:     0
        //     - ゲーム終了で黒の勝ち:     1
        //     - ゲーム終了で白の勝ち:     2
        // 盤面が全部埋まっているか否かを判定する。
        // メッセージがあれば表示する
        // (上記、並んでいれば return するので、処理がここまで来るのは
        //     並んでいないときに限られる)
        // 次に、空マスの個数を数える
        int j=0;
        int g=0;
        int c=0;
        for(int y=0 ; y<8 ; y++) {
            for(int x=0 ; x<8 ; x++) {
                if (board[x][y] == 1) {g++;}
                if (board[x][y] == 0) {j++;}
                if (board[x][y] == 2) {c++;}
            }
        }
        if(g == 0 ){
            game=2;
            return game;
        }
        if(c == 0){
            game = 1;
            return game;
        }
        if (j == 0) {
            if(g == 32){
                game=0; return game;
            } else if (g > 32) {
                game=1; return game;
            } else {
                game=2; return game;
            }
        } // 空マスが 0個ならゲーム終了


        game = -1; return game;            // 空マスが 0個でないので、ゲーム続行
    }
}

class reverseview extends Canvas {
    reversemodel model;
    int pointedX, pointedY;                     //マウスが指している盤上の座標
    String message=null;                            //メッセージ(ゲーム終了時に表示)

    private Color backGround = new Color(0,160,0);
    private Color lineColor    = new Color(0,0,0);

    public reverseview(reversemodel model) {
        this.model = model;
        setSize(700,700);
        pointedX=-1;            // 最初はマウスがどのマスも指していないことにする
    }
    public int getBrdX(MouseEvent e) {        // マウスが指す盤上の x 座標を返す
        int x = e.getX();     // マウスの画面上の x 座標を取得
        if((30 <= x) && (x < 670)) {    // 盤の中であれば、
            return (x-30) / 80;        //     座標を変換して返す
        }
        return -1;        // 盤の外であれば、-1 を返す
    }
    public int getBrdY(MouseEvent e) {    // マウスが指す盤上の y 座標を返す
        int y = e.getY();    // マウスの画面上の y 座標を取得
        if ((30 <= y) && (y < 670)) {    // 盤の中であれば、
            return (y-30) / 80;        //座標を変換して返す
        }
        return -1;    // 盤の外であれば、-1 を返す
    }
    public void paint(Graphics gc) {
        //線を表示
        gc.setColor(backGround);
        gc.fillRect(0,0,700,700);
        gc.setColor(Color.black);
        gc.fillOval(275,275,70,70);
        gc.fillOval(355,355,70,70);
        gc.setColor(Color.white);
        gc.fillOval(275,355,70,70);
        gc.fillOval(355,275,70,70);

        gc.setColor(lineColor);
        for(int i=0 ; i<9 ; i++){
            gc.drawLine(30+80*i,30,30+80*i,670);
            gc.drawLine(30,30+80*i,670,30+80*i);
        }
        for(int y=0 ; y<8 ; y++) {
            for(int x=0 ; x<8 ; x++) {
                if (model.board[x][y] == 1) { drawb(gc,x,y); }
                if (model.board[x][y] == 2) { draww(gc,x,y); }
            }
        }
        // メッセージがあれば表示する
        if (message != null) {
            gc.setColor(Color.red);
            gc.setFont(new Font("Default",Font.BOLD,80));
            gc.drawString(message,180,300);
        }
    }
    private void drawb(Graphics gc, int x, int y) {
        gc.setColor(Color.black);
        gc.fillOval(35+x*80,35+y*80,70,70);
    }
    private void draww(Graphics gc, int x, int y) {
        gc.setColor(Color.white);
        gc.fillOval(35+x*80,35+y*80,70,70);
    }
    public void setMessage(String mes) {
        // メッセージを設定する。
        message = mes;
    }
}
