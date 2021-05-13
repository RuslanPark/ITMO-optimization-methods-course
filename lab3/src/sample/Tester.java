package sample;

public class Tester {
    public static void main(String[] args) {
        MatrixGenerator.task2Generator("test1", 1000, 0);
        //MatrixGenerator.task3Generate("test2", 3);

        LUDecomposition decomposition = new LUDecomposition();
        decomposition.calculate("test1");
    }
}
