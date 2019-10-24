import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class battle {
    public static void main(String[] args) {
        char map[][]= init();
        welcome(map);
        title();
        play(map,0,0);
    }

    public static char[][] init(){
        //return new char[][]{{'o','o','o','o','o','o','o','o','o','o'},{'o','o','o','o','o','o','o','o','o','o'},{'o','o','o','o','o','o','o','o','o','o'},{'o','o','o','o','o','o','o','o','o','o'},{'o','o','o','o','o','o','o','o','o','o'},{'o','o','o','o','o','o','o','o','o','o'},{'o','o','o','o','o','o','o','o','o','o'},{'o','o','o','o','o','o','o','o','o','o'},{'o','o','o','o','o','o','o','o','o','o'},{'o','o','o','o','o','o','o','o','o','o'}};
        return new char[][]{{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '}};
    }

    public static void printMap(char map[][]){
        // Prints user view of the map
        System.out.print("   0 1 2 3 4 5 6 7 8 9\n");
        for (int i=0; i<10;i++){
            System.out.print(i+" |");
            for (int j=0; j<10;j++){
                // Just to not show the result
                if (map[i][j]==(char)'2'){
                    System.out.print("  ");
                } else{
                    System.out.print(map[i][j] + " ");
                }
                // System.out.print(map[i][j] + " ");
            }
            System.out.println("| "+i);
            if (i == 4){
                System.out.println("");
            }
        }
        System.out.print("   0 1 2 3 4 5 6 7 8 9\n");
    }
    public static void printMapAdmin(char map[][]){
        // Cheat map print
        System.out.print("   0 1 2 3 4 5 6 7 8 9\n");
        for (int i=0; i<10;i++){
            System.out.print(i+" |");
            for (int j=0; j<10;j++){
                 System.out.print(map[i][j] + " ");
            }
            System.out.println("| "+i);
            if (i == 4){
                System.out.println("");
            }
        }
        System.out.print("   0 1 2 3 4 5 6 7 8 9\n");
    }

    public static char[][] welcome(char map[][]){
        System.out.println("Welcome to Battle ship, this is the Map");
        printMap(map);
        System.out.println("Now you have to deploy your ships");
        return deployment(map);
    }

    public static char[][] deployment(char map[][]){
        userDeploy(map);
        compDeploy(map);

        // printMap(map);
        return map;
    }
    public static char[][] userDeploy(char map[][]){
        //Scanner input = new Scanner(System.in);
        System.out.println("You have to put the coordinates separated by commas, 3,1 or 4,2");
        int n =0;
        while (n<5){
            //System.out.print("Where do you want to put your ship?");
            int[] coordinates = { (int) (Math.random() * 5+5),(int) (Math.random() *10) };
            //int[] coordinates = translate(input.nextLine());
            if (checkUserField(coordinates) && checkPosition(map,coordinates, (char) ' ')){
                map[coordinates[0]][coordinates[1]]='1';
                n++;
            } else {
                System.out.println("You cannot put ships there");
                System.out.println("Try again");
            }
        }
        printMap(map);
        return map;
    }
    public static char[][] compDeploy(char map[][]){
        int n =0;
        while (n<5){
            int[] coordinates = { (int) (Math.random() * 10),(int) (Math.random() * 10) };
            if (checkUserField(coordinates)){
                System.out.println("Calculating");
            } else if (checkPosition(map,coordinates,(char) ' ')){
                map[coordinates[0]][coordinates[1]]='2';
                System.out.println("One ship has been deployed");
                n++;
            }
        }
        return map;
    }

    public static void play(char[][] map, int hits, int downs){
        Scanner input = new Scanner(System.in);

        printMap(map);
        System.out.println("Hits: "+ hits +"\t Downs: "+downs);

        System.out.print("Where do you want to fire?");
        int[] coordinates = {0,0};
        try {
            coordinates = translate(input.nextLine());
        } catch (Exception e){
            System.out.println("Not valid input");
            play(map,hits,downs);
        }
        //System.out.println(Arrays.toString(coordinates));

        if (checkUserField(coordinates)){
            System.out.println("You can not shot at your own field");
            play(map,hits,downs);

        }else if (map[coordinates[0]][coordinates[1]] == '2'){
            System.out.println("Boom! You sunk the ship!");
            hits++;
            map[coordinates[0]][coordinates[1]] = (char) 'x';
            if (hits==5){
                win();
            }
            comp(map,hits,downs);

        }else if (map[coordinates[0]][coordinates[1]] == '-') {
            System.out.println("You cannot shot two times at the same spot\nTry again");
            play(map,hits,downs);

        } else {
            map[coordinates[0]][coordinates[1]] = '-';
            comp(map,hits,downs);
        }
    }
    public static void comp(char[][] map, int hits, int downs){
        System.out.println("Computer's turn");
        int[] coordinates = { (int) (Math.random() * 5+5),(int) (Math.random() *10) };
        System.out.println("Computer aims to "+ Arrays.toString(coordinates));

        if (checkUserField(coordinates) && checkPosition(map,coordinates, '1')){
            map[coordinates[0]][coordinates[1]]='x';
            downs++;
            if (downs==5){
                lose();
            }else {
                int left = 5-downs;
                System.out.println("Ship down. " + left + " left.");
            }
        } else {
            map[coordinates[0]][coordinates[1]]='-';
            System.out.println("Miss");
        }
        play(map,hits,downs);
    }

    public static int[] translate(String coordinates){
        char ic = coordinates.charAt(0);
        char jc = coordinates.charAt(2);
        //System.out.println(ic);
        //System.out.println(jc);
        return new int[]{Character.getNumericValue(ic),Character.getNumericValue(jc)};
    }
    public static boolean checkPosition(char[][] map, int[] coordinates, char ch){
        // Returns True every time there is a given character
        if (map[coordinates[0]][coordinates[1]] == '-'){
            return false;
        }else if (map[coordinates[0]][coordinates[1]] == ch){
            return true;

        }else{
            return false;
        }
    }
    public static boolean checkUserField(int[] coordinate){
        // Returns True when coordinate relate to the User's field
        if (coordinate[0]>4){
            return true;
        } else{
            return false;
        }
    }


    public static void title(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n  ____            _     _     _             _____   _       _               \n" +
                " |  _ \\          | |   | |   | |           / ____| | |     (_)              \n" +
                " | |_) |   __ _  | |_  | |_  | |   ___    | (___   | |__    _   _ __    ___ \n" +
                " |  _ <   / _` | | __| | __| | |  / _ \\    \\___ \\  | '_ \\  | | | '_ \\  / __|\n" +
                " | |_) | | (_| | | |_  | |_  | | |  __/    ____) | | | | | | | | |_) | \\__ \\\n" +
                " |____/   \\__,_|  \\__|  \\__| |_|  \\___|   |_____/  |_| |_| |_| | .__/  |___/\n" +
                "                                                               | |          \n" +
                "                                                               |_|          ");
        System.out.println("The game Starts.\n");
    }
    public static void win(){
        System.out.println("All computer ships are down.\nUser wins");
        System.out.println("Congratulations, you win!!");
        exit();
    }
    public static void lose(){
        System.out.println("All user ships are down.\nComputer wins");
        System.out.println("Sorry you loose.");
        exit();
    }
    public static void exit(){
        System.exit(0);
    }

}


