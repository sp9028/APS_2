import java.util.Arrays;

public class binheap {

    private int heapSize;
    private int[] elements;
    private int comparisonCount;

    public binheap() {
        this.heapSize = 0;
        this.elements = new int[10];
        this.comparisonCount = 0;
    }

    public void insert(int key) {
        elements[heapSize] = key;
        siftUp(heapSize);
        heapSize++;

        if (heapSize == elements.length)
            resize();
    }

    private void siftUp(int index) {
        if (index == 0 || elements[index] == elements[(index - 1) / 2])
            return;

        comparisonCount++;
        if (elements[index] < elements[(index - 1) / 2]) {
            swapElements(index, (index - 1) / 2);
            siftUp((index - 1) / 2);
        }
    }

    private boolean isLeftChildSmaller(int leftChildIndex, int rightChildIndex) {
        return elements[leftChildIndex] <= elements[rightChildIndex];
    }

    private int getMinIndex(int leftChildIndex, int rightChildIndex, int minIndex) {
        if (leftChildIndex < heapSize) {
            comparisonCount++;
            if (elements[leftChildIndex] < elements[minIndex]) {
                return leftChildIndex;
            }
        }

        if (rightChildIndex < heapSize) {
            comparisonCount++;
            if (elements[rightChildIndex] < elements[minIndex]) {
                return rightChildIndex;
            }
        }

        return minIndex;
    }

    private void siftDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int minIndex = index;

        if (index == 0 && heapSize >= 3) {
            comparisonCount++;
            if (isLeftChildSmaller(leftChildIndex, rightChildIndex))
                minIndex = leftChildIndex;
            else
                minIndex = rightChildIndex;
        } else {
            minIndex = getMinIndex(leftChildIndex, rightChildIndex, minIndex);
        }

        if (minIndex != index) {
            swapElements(index, minIndex);
            siftDown(minIndex);
        }
    }

    public void deleteMin() {
        if (heapSize == 0) {
            System.out.println("false");
            return;
        }

        System.out.println("true: " + elements[0]);
        elements[0] = elements[heapSize - 1];
        heapSize--;

        siftDown(0);
    }

    public void printElements() {
        if (heapSize == 0) {
            System.out.println("empty");
            return;
        }

        for (int i = 0; i < heapSize; i++) {
            if (i == heapSize - 1)
                System.out.print(elements[i]);
            else
                System.out.print(elements[i] + ", ");
        }
        System.out.println();
    }

    public void printMin() {
        if (heapSize == 0) {
            System.out.println("MIN: none");
            return;
        }

        System.out.println("MIN: " + elements[0]);
    }

    public void printComparisons() {
        System.out.println("COMPARISONS: " + comparisonCount);
    }

    private void resize() {
        elements = Arrays.copyOf(elements, elements.length * 2);
    }

    private void swapElements(int i, int j) {
        int temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }
}