import java.util.*;

public class Node {
    private int cost;
    private List<String> list;
    private String target;

    // Ctor 
    public Node(String word, String target){
        this.list = new ArrayList<String>();
        list.add(word);
        this.cost = 0;
        this.target = target;
    }

    // Cctor 
    public Node(Node other){
        this.list = new ArrayList<String>();
        this.cost = other.cost;
        for (String word : other.getList()){
            this.list.add(word);
        }
        this.target = other.target;
    }

    // Return cost 
    public int getCost(){
        return this.cost;
    }

    // Cost += 1
    public void addGn(){
        this.cost++;
    }

    // Cost += input cost 
    public void addHn(int cost){
        this.cost = cost;
    }

    // Return list of node 
    public List<String> getList(){
        return this.list;
    }

    // Tambahin word ke list 
    public void addList(String word){
        this.list.add(word);
    }

    // Print 
    public void print(){
        if (!this.list.isEmpty()){
            for (String word : list){
                System.out.println(word);
            }
        }
        else{
            System.out.println("No Solution!");
        }
    }

    // Return size of list 
    public int getSize(){
        return this.list.size();
    }

    // Cek word udah ada di list belom, kalo udah return true 
    public boolean isFound(String word){
        for (String s : this.list){
            if (s.equals(word)){
                return true;
            }
        }
        return false; 
    }
}
