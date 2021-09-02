package mp2;

import java.util.*;

public class Main {
    private final static ArrayList<Integer> carrotList = new ArrayList<>();
    private final static ArrayList<Double> exponentList = new ArrayList<>();
    private final static ArrayList<Integer> xList = new ArrayList<>();
    private final static ArrayList<Integer> signList = new ArrayList<>();
    private final static ArrayList<String> stringNumList = new ArrayList<>();
    private final static ArrayList<String> stringExponentList = new ArrayList<>();
    private final static ArrayList<Double> numList = new ArrayList<>();
    private static Scanner enter = new Scanner(System.in);
    private static String equation;
    private static String[] eq;

    public static void main(String[] args) {
        String repeat;
        String ans = "yes";
        int isEquation;
        while (ans.substring(0,1).equalsIgnoreCase("y")) {
            repeat = "";
            System.out.println("Gimme equation: ");
            equation = enter.next();
            isEquation = start(equation);
            if (isEquation > 0) {
                create();
                while (!repeat.equalsIgnoreCase("end")){
                    System.out.println("What would you like to do? (roots, table, search, all, toString, end)");
                    repeat = enter.next();
                    options(repeat);
                }
            }
            else {
                System.out.println("Not a valid equation");
            }
            System.out.println("Would you like to add another equation?");
            ans = enter.next();
        }
    }
    public static void options(String s){
        double num, a, b, c;
        if (s.equalsIgnoreCase("roots")){
            System.out.println(Equation.returnEquation(Equation.getNumOfEquations()-1).root());
        }
        if (s.equalsIgnoreCase("search")){
            System.out.println("What value are you looking for?");
            num = enter.nextDouble();
            Equation.returnEquation(Equation.getNumOfEquations()-1).search(num);
        }
        if (s.equalsIgnoreCase("table")){
            System.out.println("First number?");
            a = enter.nextDouble();
            System.out.println("End number?");
            b = enter.nextDouble();
            System.out.println("Index of growth/rate?");
            c = enter.nextDouble();
            while (c<=0){
                System.out.println("Number greater than 0 plz");
                c= enter.nextDouble();
            }
            Equation.returnEquation(Equation.getNumOfEquations()-1).table(a, b, c);
        }
        if (s.equalsIgnoreCase("all")){
            if (Equation.getNumOfEquations()>1) {
                System.out.println("First number?");
                a = enter.nextDouble();
                System.out.println("End number?");
                b = enter.nextDouble();
                System.out.println("Index of growth/rate?");
                c = enter.nextDouble();
                for (int i = 0;i<Equation.getNumOfEquations();i++){
                    Equation.returnEquation(i).table(a,b,c);
                }
            }
            else {
                System.out.println("You haven't made any equations yet buddy");
            }
        }
        if (s.equalsIgnoreCase("toString")){
            System.out.println(Equation.returnEquation(Equation.getNumOfEquations()-1));
        }
    }

    public static void create() {
        String eq = equation;
        equation = "+" + equation + "+";
        edit();
        setCarrotList();
        setSignList();
        setXList();
        toNumList();
        mesh(stringNumList, numList);
        toExponentList();
        mesh(stringExponentList, exponentList);
        Equation.makeEquation(exponentList, numList, eq);
    }

    private static int start(String equation){
        int thing = 1;
        if (equation.contains("^2")){
            thing++;
        }
        for (char c='A';c<='Z';c++){
            if (equation.indexOf(c)!=-1){
                return 0;
            }
        }
        for (char c='a';c<='w';c++){
            if (equation.indexOf(c)!=-1){
                return 0;
            }
        }
        for (char c='y';c<='z';c++){
            if (equation.indexOf(c)!=-1){
                return 0;
            }
        }
        return thing;
    }

    public static void toNumList(){
        stringNumList.clear();
        numList.clear();
        int place=0;
        for (int cap = 0; cap < 10; cap++) {
            for (int i = 0; i < 1; i++) {
                if (eq[i].equals(Integer.toString(cap))) {
                    stringNumList.add(eq[i]);
                    if (i == eq.length - 1) {
                        stringNumList.add("|");
                    } else if (eq[i + 1].equals("x")) {
                        stringNumList.add("|");
                    }
                }
            }
        }
        if (eq[0].equals("-")){
            stringNumList.add("-");
        }
        for (int i = 1; i < xList.get(xList.size()-1); i++) {
            for (int cap = 0; cap < 10; cap++) {
                if (eq[i].equals(Integer.toString(cap)) && !eq[i - 1].equals("^")) {
                    stringNumList.add(eq[i]);
                    if (i == eq.length - 1) {
                        stringNumList.add("|");
                    } else if (eq[i + 1].equals("x")) {
                        stringNumList.add("|");
                    }
                }
            }
            if (eq[i].equals(".")){
                stringNumList.add(eq[i]);
            }
            if (eq[i].equals("-")){
                stringNumList.add(eq[i]);
            }
            if (eq[i].equals("x") && place<signList.size()){
                i=signList.get(place)-1;
                place++;
            }
        }
    }

    public static void toExponentList(){
        stringExponentList.clear();
        exponentList.clear();
        int place=1;
        for (int i = xList.get(0)+1; i < eq.length; i++) {
            for (int cap = 0; cap < 10; cap++) {
                if (eq[i].equals(Integer.toString(cap))) {
                    stringExponentList.add(eq[i]);
                    if (i == eq.length - 1) {
                        stringExponentList.add("|");
                    }
                    else if (eq[i + 1].equals("+") || eq[i + 1].equals("-")) {
                        stringExponentList.add("|");
                    }
                    if (i<eq.length-1) {
                        if ((eq[i + 1].equals("+") || eq[i + 1].equals("-")) && place < xList.size()) {
                            i = xList.get(place);
                            place++;
                        }
                    }
                }
            }
            if (eq[i].equals("-")){
                stringExponentList.add(eq[i]);
            }
            if (eq[i].equals(".")){
                stringExponentList.add(eq[i]);
            }
        }
    }

    public static void mesh(ArrayList<String> s, ArrayList<Double> n){
        String thing = "";
        int place = 0;
        for (int i = 0; i < s.size(); i++) {
            if (s.get(i).equals("|")) {
                for (int z = place; z < i; z++) {
                    thing += s.get(z);
                }

                n.add(Double.parseDouble(thing));
                thing = "";
                place = i + 1;
            }
        }
    }

    public static void edit(){
        int place=-1;
        int place2=-1;
        int place3=-1;
        String thing="";
        setSignList(equation);
        setCarrotList(equation);
        for (int z = 0;z<signList.size()-1;z++) {
            for (int i = signList.get(z); i < signList.get(z+1); i++) {
                if (equation.charAt(i) == 'x' && !(equation.charAt(i + 1) == '^')) {
                    place = i + 1;
                }
            }
            if (place != -1) {
                for (int i = 0; i < equation.length(); i++) {
                    if (i == place) {
                        thing += "^1";
                    }
                    thing += equation.charAt(i);
                }
                equation = thing;
                setSignList(equation);
                setCarrotList(equation);
                place = -1;
                thing="";
            }
        }
        for (int z = 0;z<signList.size();z++) {
            for (int i = 0; i < signList.size() - 1; i++) {
                if (!equation.substring(signList.get(i), signList.get(i + 1)).contains("x") && signList.get(i+1)-signList.get(i)>1) {
                    place2 = signList.get(i + 1);
                }
            }
            if (place2 != -1) {
                for (int i = 0; i < equation.length(); i++) {
                    if (i == place2) {
                        thing += "x^0";
                    }
                    thing += equation.charAt(i);
                }
                equation = thing;
                setSignList(equation);
                setCarrotList(equation);
                place2 = -1;
                thing="";
            }
        }
        for (int z = 0; z<carrotList.size(); z++) {
            for (int i = signList.get(z); i < carrotList.get(z); i++) {
                if ((equation.charAt(i) == '+' || equation.charAt(i) == '-') && equation.charAt(i + 1) == 'x') {
                    place3 = i + 1;
                }
            }
            if (place3 != -1) {
                for (int i = 0; i < equation.length(); i++) {
                    if (i == place3) {
                        thing += "1";
                    }
                    thing += equation.charAt(i);
                }
                equation = thing;
                setSignList(equation);
                setCarrotList(equation);
                place3 = -1;
                thing = "";
            }
        }
        equation = equation.substring(1, equation.length()-1);
        eq = equation.split("", equation.length());

    }
    public static void setCarrotList(){
        carrotList.clear();
        for (int i = 0; i < eq.length; i++) {
            if (eq[i].equals("^")) {
                carrotList.add(i);
            }
        }
    }
    public static void setCarrotList(String eq){
        carrotList.clear();
        for (int i = 0; i < eq.length(); i++) {
            if (eq.charAt(i) == '^') {
                carrotList.add(i);
            }
        }
    }
    public static void setSignList() {
        signList.clear();
        for (int i = 1; i < eq.length; i++) {
            if ((eq[i].equals("+") || eq[i].equals("-")) && !eq[i-1].equals("^")) {
                signList.add(i);
            }
        }
    }
    public static void setSignList(String str){
        signList.clear();
        signList.add(0);
        for (int i=1;i<str.length();i++){
            if ((equation.charAt(i) == '+' || equation.charAt(i) == '-') && !(equation.charAt(i-1) == '^')){
                signList.add(i);
            }
        }
    }
    public static void setXList(){
        xList.clear();
        for (int i = 0; i < eq.length; i++) {
            if (eq[i].equals("x")) {
                xList.add(i);
            }
        }
    }

    public static void print(){
        for (int i=0;i<eq.length;i++){
            System.out.print(eq[i]);
        }
        System.out.println();
    }
    public static void print(double[] eq){
        for (int i=0;i<eq.length;i++){
            System.out.print(eq[i] + " ");
        }
    }
}

