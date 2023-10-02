import java.util.*;

public class ArrArray {

    ArrayList<ArrayList<Integer>> gl_tabela = new ArrayList<>();
    ArrayList<Integer> tmp = new ArrayList<>();
    HashMap<Integer, Integer> stevci = new HashMap<>();

    public void insert(int e){
        tmp.add(e);
        if (gl_tabela.size() == 0){
            add();
        }
        if (stevci.containsKey(e)){
            stevci.put(e, stevci.get(e) + 1);
            tmp.clear();
            return;
        }
        for (int i = 0; i < gl_tabela.size(); i++) {
            if (check_empty(gl_tabela.get(i))){
                dodeli(i);
                tmp.clear();
                stevci.put(e, 1);
                if (i > 0){
                    for (int j = i - 1; j >= 0; j--) {
                        gl_tabela.get(j).clear();
                    }
                }
                return;
            } else{
                for (int key : stevci.keySet()){
                    if (gl_tabela.get(i).contains(key) && stevci.get(key) == 0){
                        for (int j = 0; j < gl_tabela.get(i).size(); j++) {
                            if (gl_tabela.get(i).get(j) == key){
                                tmp.clear();
                                tmp.add(e);
                                gl_tabela.get(i).set(j, e);
                                tmp.clear();
                                zlij(gl_tabela.get(i));
                                dodeli(i);
                                tmp.clear();
                                stevci.remove(key);
                                if (stevci.containsKey(e)){
                                    stevci.put(e, stevci.get(e) + 1);
                                } else{
                                    stevci.put(e, 1);
                                }
                                return;
                            }
                        }
                    }
                }
                zlij(gl_tabela.get(i));
            }
        }
        add();
        zlij(gl_tabela.get(gl_tabela.size()- 1));
        dodeli(gl_tabela.size()- 1);
        stevci.put(e, 1);
        tmp.clear();
        for (int j = gl_tabela.size()- 1 - 1; j >= 0; j--) {
            gl_tabela.get(j).clear();
        }
    }

    public void find(int e) {
        if (stevci.containsKey(e)){
            if (stevci.get(e) == 0){
                System.out.println("false");
                return;
            }
        }
        for (int i = 0; i < gl_tabela.size(); i++) {
            if (!check_empty(gl_tabela.get(i))){
                if (e == gl_tabela.get(i).get(gl_tabela.get(i).size() - 1)){
                    System.out.println("true");
                    return;
                } else if (e == gl_tabela.get(i).get(0)) {
                    System.out.println("true");
                    return;
                } else if (!(e > gl_tabela.get(i).get(gl_tabela.get(i).size() - 1) ||
                        e < gl_tabela.get(i).get(0))) {
                    if (gl_tabela.get(i).contains(e)){
                        System.out.println("true");
                        return;
                    }
                }
            }
        }
        System.out.println("false");
    }

    public void delete(int e){
        if (stevci.containsKey(e)){
            if (stevci.get(e) == 0){
                System.out.println("false");
                return;
            }
            stevci.put(e, stevci.get(e) - 1);
        }
        System.out.println("true");
    }

    public void add(){
        gl_tabela.add(new ArrayList<>());
    }

    public void dodeli(int index){
        gl_tabela.get(index).clear();
        gl_tabela.get(index).addAll(tmp);
    }

    public void zlij(ArrayList<Integer> podtabela){
        tmp.addAll(podtabela);
        Collections.sort(tmp);
    }

    public boolean check_empty(ArrayList<Integer> podtabela){
        return podtabela.size() == 0;
    }

    public void printOut() {
        StringBuilder sb = new StringBuilder();
        if (gl_tabela.size() == 0){
            sb.append("empty");
        }
        else{
            for (int i = 0; i < gl_tabela.size(); i++) {
                if (check_empty(gl_tabela.get(i))){
                    sb.append("A_").append(i).append(":").append(" ...");
                    if (i != gl_tabela.size() - 1){
                        sb.append("\n");
                    }
                }
                else {
                    boolean prazna = true;
                    sb.append("A_").append(i).append(": ");
                    for (int j = 0; j < gl_tabela.get(i).size(); j++) {
                        if (j != gl_tabela.get(i).size() - 1) {
                            if (stevci.get(gl_tabela.get(i).get(j)) == 0){
                                sb.append("x, ");
                            } else{
                                prazna = false;
                                sb.append(gl_tabela.get(i).get(j)).append("/").append(stevci.get(gl_tabela.get(i).get(j)))
                                        .append(", ");
                            }
                        } else {
                            if (stevci.get(gl_tabela.get(i).get(j)) == 0){
                                sb.append("x");
                            } else{
                                prazna = false;
                                sb.append(gl_tabela.get(i).get(j)).append("/").append(stevci.get(gl_tabela.get(i).get(j)));
                            }
                        }
                    }
                    if (prazna){
                        sb.delete(0,sb.length());
                        sb.append("empty");
                    }
                    if (i != gl_tabela.size() - 1){
                        sb.append("\n");
                    }
                }
            }
        }
        System.out.println(sb);
    }

    public ArrArray(){

    }

    public static void main(String[] args) {
        ArrArray tbl = new ArrArray();

        tbl.insert(7); tbl.insert(5); tbl.insert(9); tbl.insert(3); tbl.insert(15); tbl.insert(11);
        tbl.insert(17); tbl.delete(15); tbl.insert(11); tbl.delete(5); tbl.delete(11); tbl.insert(22);
        tbl.insert(33);
        tbl.insert(12);
        tbl.insert(9);
        tbl.printOut();
        tbl.find(11); tbl.find(9); tbl.find(42);
    }

}
