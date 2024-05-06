import java.io.*;
import java.util.*;

public class Method {
    // Nyimpen word 
    static Map<Integer, List<String>> mapOfArray = initialize();

    // Nyimpen word yang udah pernah dipake
    public static List<String> wordExist = new ArrayList<String>();

    // List char dari A sampe Z
    private static final List<Character> LIST_OF_CHAR = new ArrayList<>();

    // Generate huruf dari a sampe z, simpen di LIST_OF_CHAR
    static {
        for (char c = 'A'; c <= 'Z'; c++) {
            LIST_OF_CHAR.add(c);
        }
    }

    // Cek wordnya udah pernah dipake belum, return true kalo udah pernah dipake 
    private static boolean isUsed(String word){
        for (String s : wordExist){
            if (s.equals(word)){
                return true;
            }
        }
        return false;
    }

    // Read word dari txt, simpen di map (key: length, value: list of word)
    private static final Map<Integer, List<String>> initialize(){
        Map<Integer, List<String>> mapOfArray = new HashMap<Integer, List<String>>();
        String filePath = "words.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = br.readLine()) != null) {
                int wordLength = word.length();
                if (!mapOfArray.containsKey(wordLength)){
                    mapOfArray.put(wordLength, new ArrayList<String>());
                }
                mapOfArray.get(wordLength).add(word.toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapOfArray;
    }

    // Cek word ada di dictionary (words.txt) ato ga, return true kalo ada 
    public static boolean isExist(String word, int length) {
        List<String> words = mapOfArray.get(length);
        if (words == null) {
            return false;
        }
        for (String w : words) {
            if (w.equals(word)) {
                return true;
            }
        }
        return false;
    }
    

    // Ngitung beda karakter word yang lagi dicek sama targetnya 
    public static int calculateCost(String start, String target, int length){
        int count = 0;
        for (int i = 0; i < length; i++){
            if (start.charAt(i) != target.charAt(i)){
                count++;
            }
        }
        return count;
    }

    // Cek wordnya udah sama kaya target belom, kalo udah return true
    public static boolean isMatch(String start, String target, int length){
        return calculateCost(start, target, length) == 0;
    }

    // Nyari semua word yang bisa dikunjungi dari node ori, simpen hasilnya di list, tiap step diitung costnya 1
    public static int findChildUCS(Node ori, int length, String target, List<Node> list){
        int visited = 0;
        for (int i = 0; i < length; i++){
            for (char c : LIST_OF_CHAR){

                List <String> listOri = ori.getList();
                String lastWord = listOri.get(listOri.size()-1);

                // Iterasi perhuruf
                String wordTemp = lastWord.substring(0, i) + c + lastWord.substring(i + 1);
                char currChar = lastWord.charAt(i);

                if (Method.isExist(wordTemp, length) && currChar != c){
                // if (Method.isExist(wordTemp, length) && currChar != c && !isUsed(wordTemp)){

                    visited++;

                    Node foundChild = new Node(ori);
                    if (!foundChild.isFound(wordTemp)){
                        wordExist.add(wordTemp);
                        foundChild.addList( wordTemp);
                        foundChild.addGn();
                        list.add(foundChild);
                    }
                }
            }
        }
        return visited;
    }

    // Nyari semua word yang bisa dikunjungi dari node ori, simpen hasilnya di list, tiap step diitung costnya sesuai beda karakter sama targetnya 
    public static void findChildGreedy(Node ori, int length, String target, List<Node> list){

        for (int i = 0; i < length; i++){
            for (char c : LIST_OF_CHAR){
                List <String> listOri = ori.getList();
                String lastWord = listOri.get(listOri.size()-1);
                String wordTemp = lastWord.substring(0, i) + c + lastWord.substring(i + 1);
                char currChar = lastWord.charAt(i);

                // Cek wordnya ada di dictionary ato engga
                if (Method.isExist(wordTemp, length) && currChar != c){

                    // foundChild = CCtor dari node ori
                    Node foundChild = new Node(ori);
                    if (!foundChild.isFound(wordTemp)){

                        // Itung cost
                        int cost = Method.calculateCost(wordTemp, target, wordTemp.length());

                        // wordExist: list word yang udah pernah dikunjungi
                        wordExist.add(wordTemp);

                        // Add word baru ke node 
                        foundChild.addList( wordTemp);
                        foundChild.addHn(cost);
                        
                        // Add node ke list
                        list.add(foundChild);
                    }
                }
            }
        }
    }

    // Nyari semua word yang bisa dikunjungi dari node ori, simpen hasilnya di list, tiap step diitung costnya sesuai beda karakter sama targetnya + 1
    public static int findChildAStar(Node ori, int length, String target, List<Node> list){
        int visited = 0;
        for (int i = 0; i < length; i++){
            for (char c : LIST_OF_CHAR){
                List <String> listOri = ori.getList();
                String lastWord = listOri.get(listOri.size()-1);
                String wordTemp = lastWord.substring(0, i) + c + lastWord.substring(i + 1);
                char currChar = lastWord.charAt(i);

                // Cek wordnya ada di dictionary ato engga
                if (Method.isExist(wordTemp, length) && currChar != c && !isUsed(wordTemp)){

                    visited++;

                    // foundChild = CCtor dari node ori
                    Node foundChild = new Node(ori);
                    if (!foundChild.isFound(wordTemp)){
                        int cost = Method.calculateCost(wordTemp, target, wordTemp.length());

                        // wordExist: list word yang udah pernah dikunjungi
                        wordExist.add(wordTemp);

                        // Add word baru ke node 
                        foundChild.addList( wordTemp);
                        foundChild.addHn(cost);
                        foundChild.addGn();

                        // Add node ke list
                        list.add(foundChild);
                    }
                }
            }
        }
        return visited;
    }

    // Cari word paling deket sama target di list, dipake buat greedy
    public static Node findMinimumNode(List<Node> list) throws NoSuchElementException {
        if (list.isEmpty()){
            throw new NoSuchElementException("Oh no! Solution not found T_T");
        }
        else{
            int minCost = list.get(0).getCost();
            Node choosenNode = list.get(0);
            for (Node n : list){
                if (n.getCost() < minCost){
                    minCost = n.getCost();
                    choosenNode = n;
                }
            }
            return choosenNode;
        }
    }
}
