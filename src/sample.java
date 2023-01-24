import java.util.Scanner;


//五目並べ
class Gomoku {
   //マスや駒のステータスに関する変数------------------
   //各マスの状態(0:なにも置かれていない, 1:白が置かれている, 2:黒が置かれている)
   int[][] f_koma = new int[8][8];
   //先手と後手の入れ替え(true:白, false:黒)
   boolean f_turn;


   //マスや駒の描画に関する変数-----------------------
   // 駒([0]:駒が置かれていない, [1]:白, [2]:黒)
   String[] koma = new String[3];
   //マスの横のアルファベット表示のための文字列
   String alphabet;


   //入力のためのクラス
   Scanner sc = new Scanner(System.in);
  
   //初期化
   Gomoku() {
       f_turn = true;  //白が先手
       alphabet = "ABCDEFGH";  //マスの横軸
       koma[0] = "ー ";        //描画する駒(空)
       koma[1] = "〇 ";        //描画する駒(白)
       koma[2] = "Ⅹ  ";       //描画する駒(黒)
       //最初にマスをすべて空にする
       for(int i = 0; i < 8; i++) {
           for (int j = 0; j < 8; j++) {
               f_koma[i][j] = 0;
           }
       }
   }
   //勝敗条件の判定
   int judge() {
       int multi = 1;  //積
       // 5マス同じものが並んでいるかを判断
       // 5つの数を全て掛ける->4回の掛け算
       // 白:multi=1*1*1*1*1=1, 黒:multi=2*2*2*2*2=32
      
       //横
       for (int i = 0; i < 8; i++) {
           for (int j = 0; j < 4; j++) {
               multi = f_koma[i][j];
               for (int k = 1; k < 5; k++) {
                   multi *= f_koma[i][j + k];
               }
               if (multi == 1) {
                   return 1;
               }
               if (multi == 32) {
                   return 2;
               }
           }
       }
       //縦
       for (int i = 0; i < 8; i++) {
           for (int j = 0; j < 4; j++) {
               multi = f_koma[j][i];
               for (int k = 1; k < 5; k++) {
                   multi *= f_koma[j + k][i];
               }
               if (multi == 1) {
                   return 1;
               }
               if (multi == 32) {
                   return 2;
               }
           }
       }
       //斜め
       for (int i = 0; i < 4; i++) {
           for (int j = 0; j < 3; j++) {
               multi = f_koma[i][j];
               for (int k = 1; k < 5; k++) {
                   multi *= f_koma[i+k][j+k];
               }
               if (multi == 1) {
                   return 1;
               }
               if (multi == 32) {
                   return 2;
               }
           }
           for (int j = 0; j < 3; j++) {
               multi = f_koma[i][7-j];
               for (int k = 1; k < 5; k++) {
                   multi *= f_koma[i + k][7 - k];
               }
               if (multi == 1) {
                   return 1;
               }
               if (multi == 32) {
                   return 2;
               }
           }
       }
       return 0;
   }
   //マスと駒を描画
   void draw_koma() {
       for (int i = 0; i < 9; i++) {
           for (int j = 0; j < 9; j++) {
               if (i == 0) {
                   if (j==0)
                       System.out.print(" ");
                   else
                       System.out.printf("%c  ", 0x0040+j);
               }else if (j == 0) {
                   System.out.print(i);
               } else {
                   System.out.print(koma[f_koma[i-1][j-1]]);
               }
           }
           System.out.println("");
       }
   }
   //更新
   boolean updata_koma() {
       String com = sc.next(); //キーボードから入力
       if(com.compareTo("q") == 0){
           System.out.println("Out");
           System.out.println(-1 % 3);
           return false;
       }
       if(alphabet.indexOf(com.charAt(1)) == -1) {
           System.out.println("One more");
           return true;
       }
       if (f_koma[com.charAt(0) - 49][alphabet.indexOf(com.charAt(1))] != 0) {
           System.out.println("One more");
           return true;
       }
       //入力:数字+アルファベット
       //入力された2文字のうち、1文字目をマスの縦の数字に対応、2文字目をマスの横に対応
       //alphabetにはAからHまで順番に入っている。入力された文字がalphabet配列の何番目の要素なのかを取得(indexOf関数)
       f_koma[com.charAt(0)-49][alphabet.indexOf(com.charAt(1))] = f_turn ? 1 : 2;
       switch(this.judge()) {  //judge関数で勝敗を判定
           case 0: //勝敗なし
               break;
           case 1: //白の勝ち
               this.draw_koma();
               System.out.println("〇の勝ち");
               return false;
           case 2: //黒の勝ち
               this.draw_koma();
               System.out.println("Ⅹの勝ち");
               return false;
       }
       f_turn = !f_turn;   //手番の入れ替え
       return true;
   }
}


class sample {
   public static void main(String[] args) {
       Gomoku gomoku = new Gomoku();
       System.out.print("\033[H\033[2J");
       System.out.flush();
       gomoku.draw_koma(); //マス表示
       while(gomoku.updata_koma()) {   //勝敗がつくまで繰り返す(更新)
           System.out.print("\033[H\033[2J");
           System.out.flush();
           gomoku.draw_koma(); //描画
       }
   }
}