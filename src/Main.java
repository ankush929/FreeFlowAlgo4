public class Main {

    public static void main(String[] args) {

        Constants constants = new Constants();
        Solver solver = new Solver();
        boolean solvable = solver.init(constants.fileName, constants.N, constants.animate, constants.multipleOutputs);

        if (solvable)
            System.out.println("The given puzzle is solvable!\n");
        else
            System.out.println("The given puzzle is not solvable!\n");
    }
}
