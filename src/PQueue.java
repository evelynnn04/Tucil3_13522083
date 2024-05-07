import java.util.*;

public class PQueue {
    private List<Node> pqueue;

    // Ctor 
    public PQueue(){
        this.pqueue = new ArrayList<Node>();
    }

    // Nambahin node ke priority queue, yang costnya kecil taruh depan
    public void add(Node n) {
        int idx = 0;
        for (Node node : this.pqueue) {
            if (node.getCost() > n.getCost()) {
                break;
            }
            idx++;
        }
        pqueue.add(idx, n);
    }
    
    
    // Return first elemen which is node dengan cost paling kecil + remove node tsb
    public Node getFirstElmt() throws NoSuchElementException {
        if (this.pqueue.isEmpty()) {
            throw new NoSuchElementException("Oh no! Solution not found T_T");
        }
        else{
            Node n = this.pqueue.get(0);
            this.pqueue.remove(0);
            return n;
        }
    }

    // Return first elemen which is node dengan cost paling kecil tanpa diremove
    public Node getFirstElmtWithoutRemove() throws NoSuchElementException {
        if (this.pqueue.isEmpty()) {
            throw new NoSuchElementException("Oh no! Solution not found T_T");
        }
        else{
            Node n = this.pqueue.get(0);
            return n;        
        }
    }

    // Print
    public void print(){
        for (Node elmt : this.pqueue){
            elmt.print();
        }
    }

    // Return size of priority queue
    public int getSize(){
        return this.pqueue.size();
    }
}
