/*Author @ Phillip Crawford
 *Email @ phillip.crawford3@gmail.com
 *Pengo @pcrawfor
 *Assignment name and number: Assignment 6 (Maze Generation with Disjoint Sets)
 *Last Modified: April 26 2014
 *Filename: p6.java
 *Purpose: Driver for MazeGen class, parameter testing for int type and >= 3
 *Code status: working/tested
 */
public class p6{
    public static void main(String args[]){
        MazeGen Alpha = new MazeGen();
        if (isInteger(args[0])){
            if (bigEnough(args[0])){
                Alpha.mazeGen(Integer.parseInt(args[0]));
            }
            else System.out.println("Bad input, needs to be int 3 or larger");
        }
        else System.out.println("Bad input, needs to be int 3 or larger");
    }
    public static boolean isInteger(String test){
        try {
            Integer.parseInt(test);
        } catch(NumberFormatException e) {
            return false;
        }
            return true;
    }
    public static boolean bigEnough(String test){
        if (Integer.parseInt(test)>=3)
            return true;
        return false;
    }
}
