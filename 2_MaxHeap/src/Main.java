public class Main {

    public static void main(String[] args) {
        Heap heap = new Heap(1, true);
        for (int i = 2; i <= 31; i++) {
            heap.add(i);
        }
//        System.out.println(heap);
        System.out.println(heap.sort());
    }


}
