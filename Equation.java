package mp2;

import java.util.ArrayList;

public class Equation {
    private String equation;
    private int assignedNum;
    private static int numOfEquations = 0;
    private double[] exponents;
    private double[] nums;
    private final static Equation[] y = new Equation[100];
    private ArrayList<Double> output = new ArrayList<>();
    private ArrayList<Double> input = new ArrayList<>();

    /**
     *
     * @param e none of the elements can be equal to null, e.size()>0
     * @param n none of the elements can be equal to null, n.size()>0
     * @param equation != null, equation.length()>0
     * Post-condition: creates and Equation object
     */
    public Equation(ArrayList<Double> e, ArrayList<Double> n, String equation){
        exponents = new double[e.size()];
        exponents = fill(exponents, e);
        nums = new double[n.size()];
        nums = fill(nums, n);
        this.equation = equation;
        assignedNum = numOfEquations;
    }
    public static void makeEquation(ArrayList<Double> e, ArrayList<Double> n, String s){
        y[numOfEquations] = new Equation(e, n, s);
        numOfEquations++;
    }
    public static Equation returnEquation(int i){
        return y[i];
    }

    public void table(double first, double last, double rate){
        double thing;
        if (first<last) {
            for (double x = first; x <= last; x += rate) {
                thing = 0;
                for (int i = 0; i < nums.length; i++) {
                    thing += nums[i] * Math.pow(x, exponents[i]);
                }
                input.add(x);
                output.add(thing);
            }
            print();
            input.clear();
            output.clear();
        }
        else {
            for (double x = first; x >= last; x -= rate) {
                thing = 0;
                for (int i = 0; i < nums.length; i++) {
                    thing += nums[i] * Math.pow(x, exponents[i]);
                }
                input.add(x);
                output.add(thing);
            }
            print();
            input.clear();
            output.clear();
        }
    }
    public void print(){
        System.out.println("y" + (assignedNum) + " = " + equation + " {");
        for (int i = 0;i<input.size();i++){
            System.out.print(input.get(i));
            for (int z = 0; z<numOfSpaces(i)+1;z++){
                System.out.print(" ");
            }
            System.out.println("| " + output.get(i));
        }
        System.out.println("}");
    }
    public int numOfSpaces(int i){
        return Double.toString(input.get(input.size()-1)).length() - Double.toString(input.get(i)).length();
    }

    public void search(double val){
        double thing;
        thing=0;
        for (int i = 0; i < nums.length; i++) {
            thing += nums[i] * Math.pow(val, exponents[i]);
        }
        System.out.println(thing);
    }

    public String root(){
        double a=0;
        double b=0;
        double c=0;
        if (contains(exponents, 2.0)) {//exponents.length<4 &&
            for (int i = 0; i < exponents.length; i++) {
                if (exponents[i]==2 || exponents[i]==1 || exponents[i]==0) {
                    if (exponents[i] == 2) {
                        a += nums[i];
                    }
                    if (exponents[i] == 1) {
                        b += nums[i];
                    }
                    if (exponents[i] == 0) {
                        c += nums[i];
                    }
                }
                else {
                    return "not valid";
                }
            }
        }
        else {
            return "not valid";
        }
        if (Math.pow(b, 2) - (4*a*c)>=0){
            return "x1 = " + (-1*b + Math.sqrt(Math.pow(b, 2) - 4*a*c))/(2*a) + "\nx2 = " + (-1*b - Math.sqrt(Math.pow(b, 2) - 4*a*c))/(2*a);

        }
        else {
            return "x1 = (" + -1*b + "+(√" + (Math.pow(b, 2) - 4*a*c) + "))/" + 2*a + "\nx2 = " + -1*b + "(-(√" + (Math.pow(b, 2) - 4*a*c) + "))/" + 2*a;
        }
    }


    public String toString(){
        String bean = "";
        double[] exponentClone = new double[exponents.length];
        fill(exponentClone, exponents);
        exponentClone = shiftRight(exponentClone);
        exponentClone = reverse(exponentClone);
        if ((int)max(exponentClone)==max(exponentClone)){
            if (max(exponentClone)%2==0){
                bean = "n even";
            }
            else {
                bean = "n odd";
            }
        }
        if (exponentClone.length==1){
            return "This equation has a" + bean + " degree of " + max(exponentClone) + " and a total of " + exponentClone.length + " term\n" +
                    "with coefficients sum of " + sum(nums) + ", average of " + mean(nums) + ", and mode of " + mode(nums);
        }
        return "This equation has a" + bean + " degree of " + max(exponentClone) + " and a total of " + exponentClone.length + " terms\n" +
                "with coefficients sum of " + sum(nums) + ", average of " + mean(nums) + ", and mode of " + mode(nums);
    }
    public double[] fill(double[] arr, ArrayList<Double> list){
        for (int i=0;i<list.size();i++){
            arr[i]=list.get(i);
        }
        return arr;
    }
    public double[] fill(double[] arr, double[] list){
        for (int i=0;i<list.length;i++){
            arr[i]=list[i];
        }
        return arr;
    }
    public double sum(double[] arr){
        double sum=0;
        for (double i: arr){
            sum+=i;
        }
        return sum;
    }
    public boolean contains(double[] arr, double num){
        for (double i:arr){
            if (i == num){
                return true;
            }
        }
        return false;
    }
    public double[] shiftRight(double[] arr){
        double[] thing = new double[arr.length];
        for (int i=0;i<arr.length-1;i++){
            thing[i+1] = arr[i];
        }
        thing[0] = arr[arr.length-1];
        return thing;
    }
    public double[] reverse(double[] arr){
        int index = 0;
        double[] thing = new double[arr.length];
        for (int i = arr.length-1;i>=0;i--){
            for (int z=index;z<thing.length;z++){
                thing[z]=arr[i];
            }
            index++;
        }
        return thing;
    }
    public double mean(double[] arr){
        return sum(arr)/arr.length;
    }
    public double mode(double[] arr){
        ArrayList<Double> list = new ArrayList<>();
        for (double i: arr){
            if (!list.contains(i)){
                list.add(i);
            }
        }
        int[] thing = new int[list.size()];
        for (double i: arr){
            for (int z = 0;z<list.size();z++){
                if (i == list.get(z)){
                    thing[z]++;
                }
            }
        }
        for (int cap = arr.length;cap>-1;cap--){
            for (int i =0;i<thing.length; i++){
                if (thing[i]==cap){
                    return list.get(i);
                }
            }
        }
        return 0;
    }
    public double max(double[] darr){
        double maximum = Integer.MIN_VALUE;
        for (int i=0;i<darr.length;i++){
            if (darr[i] > maximum){
                maximum = darr[i];
            }
        }
        return maximum;
    }
    public static int getNumOfEquations(){
        return numOfEquations;
    }
    public double[] getNumList(){
        return nums;
    }

}
