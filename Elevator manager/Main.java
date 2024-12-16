public class Main {
    public static void main(String[] args) {
        ManageElevator start = new ManageElevator();
        try {
            start.start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
