/*Author @ Phillip Crawford
 *Email @ phillip.crawford3@gmail.com
 *Pengo @pcrawfor
 *Assignment name and number: Assignment 6 (Maze Generation with Disjoint Sets)
 *Last Modified: April 26 2014
 *Filename: MazeGen.java
 *Purpose: Disjoint Set Forest and 2 D int array will be used to build random
 *maze. Member functions mazeInitializer, randomWallBreaker, and roomBreaker
 *make use of Disjoint Set Forest and 2 D int array. Output is of type Char and
 *int.
 *Code status: working/tested
 */
import java.util.Random;

public class MazeGen{
    Random rand = new Random();
    private int [] parent;
    private int [] rank;
    private int [][] letters;
    private int n;
    private int size;  // n squared
    private int count; // count keeps track of which Set element we're on

    public void mazeGen(int n){
        this.n = n;
        this.size = n*n;
        parent = new int [size];
        rank = new int[size];
        for (int i = 0; i < size; i++){
            makeSet(i);
        }
        mazeInitializer();//build maze
        randomWallBreaker();//break random eligible walls
        roomBreaker();//break remaining walls that need to be broken
        printIt();
    }

    //Make Disjointed Set function
    public void makeSet(int x){
        parent[x] =x;
        rank[x] = 0;
    }

    //Union Disjointed Set function
    public void union(int x, int y){
        link(find(x), find(y));
    }

    //Link Disjointed Set function
    private void link(int x, int y){
        if (x == y) return;
        if (rank[x] > rank[y])
            parent[y] = x;
        else {
            parent[x] = y;
            if (rank[x] == rank[y])
                rank[y]++;
        }
    }

    //Find Disjointed Set function
    public int find(int x) {
        if(x != parent[x])
            parent[x] = find(parent[x]);
        return parent[x];
    }

    // Initialize maze
    public void mazeInitializer(){
        letters = new int [n][n];
        for (int j = 0; j < n; j++){
            for (int k = 0; k < n; k++){
                letters [j][k] = 15;
            }
        }
        letters[0][0] = 11;
        letters[n-1][n-1] = 14;
    }

    // iterate through maze breaking down random eligible walls, building
    // Disjointed Set Forest accordingly
    public void randomWallBreaker(){
        int rightCheck;
        int leftCheck;
        int upCheck;
        int downCheck;
        int rightRand;
        int leftRand;
        int upRand;
        int downRand;
        count = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                //all walls in running to be broken down
                rightCheck  = 1;
                leftCheck  = 1;
                upCheck  = 1;
                downCheck  = 1;
                //get edge walls out of the running
                if (j == (n-1)){
                rightCheck = 0;
                }
                if (j == 0){
                leftCheck = 0;
                }
                if (i == 0){
                upCheck = 0;
                }
                if (i == (n-1)){
                downCheck = 0;
                }
                //randomly set walls in running to number
                rightRand = (rand.nextInt(10000)+1)*rightCheck;
                leftRand = (rand.nextInt(10000)+1)*leftCheck;
                upRand = (rand.nextInt(10000)+1)*upCheck;
                downRand = (rand.nextInt(10000)+1)*downCheck;
                //find max of those wall's number
                int max = 0;
                if (rightRand > max){
                    max = rightRand;
                }
                if (leftRand > max){
                    max = leftRand;
                }
                if (upRand > max){
                    max = upRand;
                }
                if (downRand > max){
                    max = downRand;
                }
                // knock down right wall, add right space to set
                if (rightRand == max){
                    breakRightWall(count, i, j);
                }
                // knock down left wall, add left space to set
                if (leftRand == max){
                    breakLeftWall(count, i, j);
                }
                // knock down upper wall, add upper space to set
                if (upRand == max){
                    breakTopWall(count, i, j);
                }
                // knock down bottom wall, add left bottom to set
                if (downRand == max){
                    breakBottomWall(count, i, j);
                }
                count++;
            }
        }
    }

    public void breakRightWall(int count, int i, int j){
        if (find(count) != find(count+1)){
            union(count, (count+1));
            letters [j][i] =  letters[j][i] - 1;
            letters [j+1][i] = letters [j+1][i] -4;
        }
    }

    public void breakLeftWall(int count, int i, int j){
        if (find(count) != find(count-1)){
            union(count, (count-1));
            letters [j][i] =  letters[j][i] - 4;
            letters [j-1][i] = letters [j-1][i] -1;
        }
    }

    public void breakBottomWall(int count, int i, int j){
        if (find(count) != find(count+n)){
            union(count, (count+n));
            letters [j][i] =  letters[j][i] - 2;
            letters [j][i+1] = letters [j][i+1] -8;
        }
    }

    public void breakTopWall (int count, int i, int j){
        if (find(count) != find(count-n)){
            union(count, (count-n));
            letters [j][i] =  letters[j][i] - 8;
            letters [j][i-1] = letters [j][i-1] -2;
        }
    }

    // Break down rooms into one set. Could make legal maze with this function
    // and no calls to randomizer, but that would make the same maze every time
    // By end, all rooms are part of same Disjointed Set Forest
    public void roomBreaker(){
        count = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                // remove rooms to the left if they're not in set
                if (j != 0){
                    breakLeftWall(count, i, j);
                }
                // remove rooms to the bottom if they're not in set
                if ((i != (n-1)) && ((count+1) != (n*n))){
                    breakBottomWall(count, i, j);
                }
                // remove rooms to the right if they're not in set
                if ((j != (n-1)) && ((count +1) != (n*n))){
                    breakRightWall(count, i, j);
                }
                // remove rooms to the top if they're not in set
                if ((i != 0) && ((count +1) != (n*n))){
                    breakTopWall(count, i, j);
                }
                count++;
            }
        }
    }
    public void printIt(){
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                // ASCII offset from numbers to letters (they're not adjacent)
                // means we can't perform wall breaking with -8, -4, -2, -1.
                // In adjusting output, output is of type int and Char. While
                // way around this, I thought it would lead to slower run time
                switch(letters[j][i]){
                    case 10:
                        System.out.print('a');
                        break;
                    case 11:
                        System.out.print('b');
                        break;
                    case 12:
                        System.out.print('c');
                        break;
                    case 13:
                        System.out.print('d');
                        break;
                    case 14:
                        System.out.print('e');
                        break;
                    default:
                        System.out.print(letters[j][i]);
                }
                /*
                                if (letters[j][i] == 10){
                                    System.out.print('a');
                                }
                                else if(letters[j][i] == 11){
                                    System.out.print('b');
                                }
                                else if (letters[j][i] == 12){
                                    System.out.print('c');
                                }
                                else if (letters[j][i] == 13){
                                    System.out.print('d');
                                }
                                else if (letters[j][i] == 14){
                                    System.out.print('e');
                                }
                                else {
                                    System.out.print(letters[j][i]);
                }
                */
            }
            System.out.println();
        }
    }
}