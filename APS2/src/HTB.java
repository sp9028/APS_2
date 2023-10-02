import java.util.Arrays;

public class HTB {
    private int p;
    private int tblSize;
    private int c1;
    private int c2;

    private int currColl;
    private int collisions;
    private boolean collision;

    private HTBEl[] hashTable;

    public HTB(int p, int m, int c1, int c2) {
        this.p = p;
        this.tblSize = 0;
        this.c1 = c1;
        this.c2 = c2;
        this.collisions = 0;
        this.collision = false;
        this.currColl = 0;
        this.hashTable = new HTBEl[m];
    }

    private int hashFun(int key){
        if (collision) {
            return (((key * p) % hashTable.length) + (c1 * currColl)
                    + (c2 * currColl * currColl)) % hashTable.length;
        } else
            return (key * p) % hashTable.length;
    }

    public void insert(int key) {
        if (tblSize != 0 && containsColl(key)) {
            return;
        }
        int hash = hashFun(key);

        hashTable[hash] = new HTBEl(hash, key);

        tblSize++;
        collision = false;
        currColl = 0;
    }

    public void delete(int key) {
        if (!find(key))
            return;
        int hash = hashFun(key);
        if (hashTable[hash].value == key) {
            hashTable[hash] = null;
        }
    }


    private boolean containsColl(int key){
        if (find(key)){
            int hash = hashFun(key);
            if (hashTable[hash].value == key){
                collisions -= currColl;
                return true;
            }
            if (currColl == hashTable.length){
                resize();
            } else {
                collision = true;
                currColl++;
                collisions++;
            }
            containsColl(key);
        }
        return false;
    }

    public void resize(){
        int tableSize = hashTable.length * 2 + 1;

        HTBEl[] newTable = new HTBEl[tableSize];
        var oldTable = hashTable.clone();

        hashTable = newTable;
        tblSize = 0;
        currColl = 0;
        collision = false;

        for (HTBEl hashElement : oldTable) {
            if(hashElement != null) {
                insert(hashElement.value);
            }
        }


    }


    public boolean find(int key){
        int hash = hashFun(key);

        for (HTBEl element : hashTable)
            if (element != null && element.equalsHash(hash))
                return true;

        return false;
    }

    public void printKeys() {
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                System.out.println(i + ": " + hashTable[i].value);
            }
        }
    }

    public void printCollisions() {
        System.out.println(collisions);
    }

    public static void main(String[] args) {
        HTB ht = new HTB(7, 3, 5, 7);

        ht.insert(19); ht.insert(11); ht.insert(23); ht.insert(19); ht.insert(29);
        ht.insert(17); ht.insert(2); ht.insert(33); ht.insert(99); ht.insert(129);

        ht.printKeys();
        System.out.println("--");
        ht.printCollisions();
    }

}

class HTBEl{
    public int hash;
    public int value;

    public HTBEl(int hash, int data) {
        this.hash = hash;
        this.value = data;
    }

    public boolean equalsHash(int a) {
        return this.hash == a;
    }
}