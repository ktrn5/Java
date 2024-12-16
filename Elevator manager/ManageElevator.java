import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class ManageElevator {
    private Elevator[] elevators;
    private Random random;
    private int request_count;
    private ExecutorService executor;
    private BlockingQueue<Integer> queue_requests;

    // initialization of the elevator array
    //  my ssystem contains 3 elevators
    // elevator(number of elevator, the floor): init every elevator on a certain floor
    public ManageElevator() {
        elevators = new Elevator[3];
        elevators[0] = new Elevator(1, 1); // 1 elevator on 1
        elevators[1] = new Elevator(2, 4); // 2 elevator jn 4
        elevators[2] = new Elevator(3, 7); // 3 elevator on 7

        random = new Random();// generate random number
        request_count = 0; // to count number of requests from people

        executor = Executors.newFixedThreadPool(3); // thread pool
        queue_requests = new ArrayBlockingQueue<>(100); // queue of requests
    }

    // request info
    public void all_requests(int final_floor) {
        request_count++; // to control number of requests

        // here i use ascii (to make text about requests brighter --> easier to read)
        System.out.println("\u001B[31mВызов №" + request_count + ": этаж " + final_floor + "\u001B[0m");
        queue_requests.offer(final_floor);
    }

    // to analyze the requesr from the queue
    // use my algorithm to make the way and tht time gap shorter
    // the algorith in readme file
    public void request_process() throws InterruptedException {
        while (true) {
            int final_floor = queue_requests.take();
            Elevator choice_elevator = null;
            boolean elevator_move_flag = false;

            // check whether some leveators are mpving.
            // if it is moving in particular direction, we choose it
            for (int i = 0; i < elevators.length; i++) {
                Elevator elevator = elevators[i];
                if (elevator.is_move_flag()) {
                    int direction = elevator.get_direction();
                    if ((direction == 1 && final_floor > elevator.get_current_floor()) || (direction == -1 && final_floor < elevator.get_current_floor())) {
                        choice_elevator = elevator;
                        elevator_move_flag = true;
                        break;
                    }
                }
            }

            // if there are not moving elevators, choose the closest one to the request floor
            // use Math.abs, example: 1 lift -- 1 floor, 2 -- 4 floor, 3 -- 7 floor. Request: 5 floor
            // find the abs:
            // |1-5| = 4; |4-5| = 1; |7-5| = 2; the closest -- 1
            if (!elevator_move_flag) {
                int min_dist = Integer.MAX_VALUE;
                for (Elevator elevator : elevators) {
                    int distance = Math.abs(elevator.get_current_floor() - final_floor); //
                    if (distance < min_dist) {
                        min_dist = distance;
                        choice_elevator = elevator;
                    }
                }
            }

            // send task to the thread pool
            if (choice_elevator != null) {
                Elevator final_choise = choice_elevator;
                executor.submit(() -> {
                    final_choise.move_to_floor(final_floor);
                });
            }
        }
    }
    // to start the proghramme, send the task to the tgread pool
    public void start() throws InterruptedException {
        executor.submit(() -> {
            try {
                request_process();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        while (true) {
            TimeUnit.SECONDS.sleep(2); //
            int final_floor = random.nextInt(7) + 1; // overall there are 7 elevators. generate one of them
            all_requests(final_floor);
        }
    }
}
