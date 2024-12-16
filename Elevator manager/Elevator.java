
public class Elevator  {
    public int lift; // the elevator
    private int current_floor;
    private boolean move_flag; // wheter elevator moves or not

    // according th my algorithm: different directions of moving lives
    private int direction; // 1 for up, -1 for down, 0 for not moving

    //  constructor of the elevator
    //  initialization the lift with the specified number and initial floor
    public Elevator(int lift, int initial_floor) {
        this.lift = lift;
        this.current_floor = initial_floor;
        this.move_flag = false;
        this.direction = 0;
    }

    public void move_to_floor(int final_floor) {
        try {
            // if the elevator is already on the request floor
            if (final_floor == current_floor) {
                System.out.println("Лифт №" + lift + " уже на этаже " + final_floor);
            } else {
                move_flag = true;

                if (final_floor > current_floor) {
                    direction = 1;
                } else {
                    direction = -1;
                }

                // it shows which lift take the request
                System.out.println("Лифт №" + lift + " начал движение на этаж " + final_floor);
                Thread.sleep(2000); // 2 sec
                current_floor = final_floor;
                System.out.println("Лифт №" + lift + " достиг этажа " + final_floor);

                // change move_flag and direction bc lift stopped (not moving)
                move_flag = false;
                direction = 0;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // methods 
    // 1) to get the floor (current)
    // 2) check whether it moevs
    // 3) get_direction (if moves)


    public int get_current_floor() {
        return current_floor;

    }

    public boolean is_move_flag() {
        return move_flag;

    }

    public int get_direction() {
        return direction;

    }

}

