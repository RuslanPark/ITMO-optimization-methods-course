package sample;

public class Tester {
    public static void main(String[] args) {
        MatrixGenerator.task2Generator("test1", 47, 0);
        //MatrixGenerator.task3Generate("test2", 3);
        //ProffilMatrix matrix = new ProffilMatrix();
        //matrix.readMatrix("test1");

        //LUDecomposition decomposition = new LUDecomposition();
        //decomposition.calculate("test1");

        GaussMethod gaussMethod = new GaussMethod();
        gaussMethod.calculate("test1");
    }
}
