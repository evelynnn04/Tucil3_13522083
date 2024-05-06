import java.util.*;

public class Solver {
    private int length;
    private String start;
    private String target;
    private PQueue pqueue;
    private double timeExecution; // dalam ms 
    private double memoryUsed; // dalam mb
    private int visitedWords; 

    // Ctor 
    public Solver(int length, String start, String target){
        this.length = length;
        this.start = start;
        this.target = target;
        this.pqueue = new PQueue();
        this.timeExecution = 0;
        this.memoryUsed = 0;
        this.visitedWords = 1;
    }

    // Solve dengan metode UCS, return node 
    public Node solveUCS(){

        // Time & memory 
        long startTime = System.currentTimeMillis(); 
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        Node first = new Node(this.start, this.target);
        List<Node> listTemp = new ArrayList<Node>();

        // Cari semua child 
        int visited;
        visited = Method.findChildUCS(first, this.length, this.target, listTemp);
        this.visitedWords += visited;

        // Masukin ke priority queue
        for (Node solution : listTemp){
            pqueue.add(solution);
        }

        Node firstElmtPQueue = pqueue.getFirstElmtWithoutRemove();
        String word = firstElmtPQueue.getList().get(firstElmtPQueue.getList().size() - 1);

        
        while (!Method.isMatch(word, this.target, this.length)){
            
            List<Node> listNode = new ArrayList<Node>();

            // Ambil first + remove
            firstElmtPQueue = pqueue.getFirstElmt();

            // Find child dari elemen pertama 
            Node ntemp = new Node(firstElmtPQueue);
            visited = Method.findChildUCS(ntemp, this.length, this.target, listNode);
            this.visitedWords += visited;

            // Masukin hasilnya
            for (Node solution : listNode){ 
                pqueue.add(solution);
            }

            firstElmtPQueue = pqueue.getFirstElmtWithoutRemove();
 
            word = firstElmtPQueue.getList().get(firstElmtPQueue.getList().size() - 1);
        }

        // Time & memory 
        long endTime = System.currentTimeMillis();
        this.timeExecution = endTime - startTime; 
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory(); 
        long memoryUsage = memoryAfter - memoryBefore; 
        this.memoryUsed = memoryUsage / (1024.0 * 1024.0);

        // Return 
        return pqueue.getFirstElmtWithoutRemove();
    }

    // Solve dengan metode Greedy, return node 
    public Node solveGreedy(){
        
        // Time & memory 
        long startTime = System.currentTimeMillis(); 
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        Node first = new Node(this.start, this.target);
        
        // Tambahin cost di awal 
        int initialCost = Method.calculateCost(start, target, length);
        first.addHn(initialCost);

        List<Node> listTemp = new ArrayList<Node>();

        // Cari semua child 
        Method.findChildGreedy(first, this.length, this.target, listTemp);
        this.visitedWords++;

        // Cari node dengan cost minimal 
        Node choosen = Method.findMinimumNode(listTemp);

        String word = choosen.getList().get(choosen.getList().size() - 1);

        while (!Method.isMatch(word, this.target, this.length)){

            List<Node> listNode = new ArrayList<Node>();
            Method.findChildGreedy(choosen, this.length, this.target, listNode);
            this.visitedWords++;

            choosen = Method.findMinimumNode(listNode);

            word = choosen.getList().get(choosen.getList().size() - 1);
        }

        // Time & memory 
        long endTime = System.currentTimeMillis();
        this.timeExecution = endTime - startTime; 
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory(); 
        long memoryUsage = memoryAfter - memoryBefore; 
        this.memoryUsed = memoryUsage / (1024.0 * 1024.0);

        // Return 
        return choosen;
    }

    // Solve dengan metode A*, return node
    public Node solveAStar(){

        // Time 
        long startTime = System.currentTimeMillis(); 
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        Node first = new Node(this.start, this.target);
        List<Node> listTemp = new ArrayList<Node>();

        // Cari semua child 
        int visited;
        visited = Method.findChildAStar(first, this.length, this.target, listTemp);
        this.visitedWords += visited;

        // Masukin ke priority queue
        for (Node solution : listTemp){
            pqueue.add(solution);
        }

        Node firstElmtPQueue = pqueue.getFirstElmtWithoutRemove();
        String word = firstElmtPQueue.getList().get(firstElmtPQueue.getList().size() - 1);

        
        while (!Method.isMatch(word, this.target, this.length)){
            
            List<Node> listNode = new ArrayList<Node>();

            // Ambil first + remove
            firstElmtPQueue = pqueue.getFirstElmt();

            // Find child dari elemen pertama 
            Node ntemp = new Node(firstElmtPQueue);
            visited = Method.findChildAStar(ntemp, this.length, this.target, listNode);
            this.visitedWords += visited;

            // Masukin hasilnya
            for (Node solution : listNode){ 
                pqueue.add(solution);
            }

            firstElmtPQueue = pqueue.getFirstElmtWithoutRemove();
 
            word = firstElmtPQueue.getList().get(firstElmtPQueue.getList().size() - 1);
        }

        // Time & memory 
        long endTime = System.currentTimeMillis();
        this.timeExecution = endTime - startTime; 
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory(); 
        long memoryUsage = memoryAfter - memoryBefore; 
        this.memoryUsed = memoryUsage / (1024.0);

        //Return 
        return pqueue.getFirstElmtWithoutRemove();
    }

    // Return time execution dalam ms 
    public double getTimeExecution(){
        return this.timeExecution;
    }

    // Return memory used dalam mb 
    public double getMemoryUsed(){
        return this.memoryUsed;
    }

    public int getVisitedWords(){
        return this.visitedWords;
    }

}


