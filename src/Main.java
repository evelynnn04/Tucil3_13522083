
// import java.awt.List;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Word Ladder Solver!");
        System.out.println("Make sure you enter valid words in English!");

        String start;
        String goal;

        do {
            System.out.print("Enter start word: ");
            start = scanner.next().toUpperCase();
            System.out.print("Enter goal word: ");
            goal = scanner.next().toUpperCase();

            if (start.length() != goal.length()) {
                System.out.println("Start and goal words must have the same length.");
            } else if (!Method.isExist(start, start.length()) || !Method.isExist(goal,
                    goal.length())) {
                System.out.println("One or both words do not exist in the dictionary.");
            }
        } while (start.length() != goal.length() ||
                !Method.isExist(start, start.length()) || !Method.isExist(goal,
                        goal.length()));

        int method = 0;
        boolean validMethod = false;

        while (!validMethod) {
            try {
                System.out.println();
                System.out.println("Method: ");
                System.out.println("1. UCS");
                System.out.println("2. Greedy");
                System.out.println("3. A*");
                System.out.println("Just enter the number!");
                System.out.print("Enter method: ");
                method = scanner.nextInt();

                if (method >= 1 && method <= 3) {
                    validMethod = true;
                } else {
                    System.out.println("Invalid input! Please enter a number between 1 and 3.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
            }
        }
        scanner.close();

        // Solve 

        if (start.equals(goal)){
            System.out.println();
            System.out.println("You have found the answer!");
            System.out.println();
            return;
        }

        Solver solver = new Solver(start.length(), start, goal);
        Node nodeRes = new Node(start, goal);

        try{
            if (method == 1) { // UCS
                nodeRes = solver.solveUCS();
            }
            
            else if (method == 2) { // Greedy
                nodeRes = solver.solveGreedy();
            }
            
            else { // A Star
                nodeRes = solver.solveAStar();
            }
    
            System.out.println();
            System.out.println("Result: ");
            nodeRes.print();
    
            System.out.println("Execution time: " + String.format("%.2f", solver.getTimeExecution()) + " ms");
            System.out.println("Memory used: " + String.format("%.2f", solver.getMemoryUsed()) + " mb");
            System.out.println("Node visited: " + solver.getVisitedWords() + " nodes");   
        } catch(NoSuchElementException e){
            System.out.println();
            System.out.println(e.getMessage());
        }

        System.out.println(); 
        System.out.println("Have a nice day!");
        System.out.println(); 

    }
}
