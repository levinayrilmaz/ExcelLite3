package de.ayrilmaz.client.hanlder;

public class MathHandler {
    String input;

    public MathHandler(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public boolean checkForMathOperations(String input){


        if(input.contains("+")){
            return true;
        }else if(input.contains("-")) {

            return true;
        }else if(input.contains("*")) {

            return true;
        }else if(input.contains("/")) {

            return true;
        }else{
            return false;
        }

    }

    public double checkForAdd(){
        String[] splitted = input.split("\\+");
        try{
            double a = Double.parseDouble(splitted[0]);
            double b = Double.parseDouble(splitted[1]);
            return add(a, b);
        }catch (Exception e){
            System.out.println("Fehler");
        }
        return Double.MAX_VALUE;
    }

    public double checkForSub(){
        String[] splitted = input.split("\\-");
        try{
            double a = Double.parseDouble(splitted[0]);
            double b = Double.parseDouble(splitted[1]);
            return sub(a, b);
        }catch (Exception e){
            System.out.println("Fehler");
        }
        return Double.MAX_VALUE;
    }

    public double checkForMul(){
        String[] splitted = input.split("\\*");
        try{
            double a = Double.parseDouble(splitted[0]);
            double b = Double.parseDouble(splitted[1]);
            return mul(a, b);
        }catch (Exception e){
            System.out.println("Fehler");
        }
        return Double.MAX_VALUE;
    }

    public double checkForDiv(){
        String[] splitted = input.split("\\/");
        try {
            double a = Double.parseDouble(splitted[0]);
            double b = Double.parseDouble(splitted[1]);
            return div(a, b);
        }catch (Exception e){
            System.out.println("Fehler");
        }
        return Double.MAX_VALUE;
    }

    public double add(double a, double b){
        return a + b;
    }
    public double sub(double a, double b){
        return a - b;
    }
    public double mul(double a, double b){
        return a * b;
    }
    public double div(double a, double b){
        return a / b;
    }

    public double calculate() {
        if(input.contains("+")){

            return checkForAdd();
        }else if(input.contains("-")) {

            return checkForSub();
        }else if(input.contains("*")) {

            return checkForMul();
        }else if(input.contains("/")) {

            return checkForDiv();
        }else{
            return 0;
        }
    }
}
