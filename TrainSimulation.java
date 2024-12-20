import java.util.Scanner;

class Passenger {
    int id;
    String name;
    int trainId;

    public Passenger(int id, String name, int trainId) {
        this.id = id;
        this.name = name;
        this.trainId = trainId;
    }

    public String toString() {
        return "Passenger{id=" + id + ", name='" + name + "', trainId=" + trainId + "}";
    }
}

class MyArrayList {
    private Train[] arr;
    private int size;

    public MyArrayList() {
        arr = new Train[10];
        size = 0;
    }

    public void add(Train train) {
        if (size == arr.length) {
            resize();
        }
        arr[size] = train;
        size++;
    }

    public Train get(int index) {
        if (index >= 0 && index < size) {
            return arr[index];
        }
        return null;
    }

    public int size() {
        return size;
    }

    public String toString() {
        String result = "[";
        for (int i = 0; i < size; i++) {
            result += arr[i].toString();
            if (i < size - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }

    private void resize() {
        Train[] newArr = new Train[arr.length * 2];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }
}

class MyLinkedList {
    private class Node {
        Passenger passenger;
        Node next;

        public Node(Passenger passenger) {
            this.passenger = passenger;
            this.next = null;
        }
    }

    private Node head;

    public MyLinkedList() {
        head = null;
    }

    public void add(Passenger passenger) {
        Node newNode = new Node(passenger);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public Passenger removeFirst() {
        if (head == null) {
            return null;
        }
        Passenger passenger = head.passenger;
        head = head.next;
        return passenger;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public String toString() {
        String result = "[";
        Node current = head;
        while (current != null) {
            result += current.passenger.toString();
            if (current.next != null) {
                result += ", ";
            }
            current = current.next;
        }
        result += "]";
        return result;
    }
}

class MyStack {
    private class Node {
        Passenger passenger;
        Node next;

        public Node(Passenger passenger) {
            this.passenger = passenger;
            this.next = null;
        }
    }

    private Node head;

    public MyStack() {
        head = null;
    }

    public void push(Passenger passenger) {
        Node newNode = new Node(passenger);
        newNode.next = head;
        head = newNode;
    }

    public Passenger pop() {
        if (head == null) {
            return null;
        }
        Passenger passenger = head.passenger;
        head = head.next;
        return passenger;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public String toString() {
        String result = "[";
        Node current = head;
        while (current != null) {
            result += current.passenger.toString();
            if (current.next != null) {
                result += ", ";
            }
            current = current.next;
        }
        result += "]";
        return result;
    }
}

class MyQueue {
    private MyLinkedList list;

    public MyQueue() {
        list = new MyLinkedList();
    }

    public void enqueue(Passenger passenger) {
        list.add(passenger);
    }

    public Passenger dequeue() {
        return list.removeFirst();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public String toString() {
        return list.toString();
    }
}

class Train {
    int id;
    int capacity;
    MyLinkedList passengers;

    public Train(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.passengers = new MyLinkedList();
    }

    public boolean boardPassenger(Passenger passenger) {
        if (passengers.size() < capacity) {
            passengers.add(passenger);
            return true;
        }
        return false;
    }

    public void reverseCompartments() {
        MyStack stack = new MyStack();
        while (!passengers.isEmpty()) {
            stack.push(passengers.removeFirst());
        }
        while (!stack.isEmpty()) {
            passengers.add(stack.pop());
        }
    }

    public String toString() {
        String result = "Train{id=" + id + ", capacity=" + capacity + ", passengers=";
        result += passengers.toString();
        result += "}";
        return result;
    }
}

class TrainStation {
    MyArrayList trains;
    MyQueue waitingQueue;

    public TrainStation() {
        trains = new MyArrayList();
        waitingQueue = new MyQueue();
    }

    public void addTrain(Train train) {
        trains.add(train);
    }

    public void addPassenger(Passenger passenger) {
        boolean trainFound = false;
        for (int i = 0; i < trains.size(); i++) {
            Train train = trains.get(i);
            if (train.id == passenger.trainId) {
                trainFound = true;
                waitingQueue.enqueue(passenger);
                break;
            }
        }
        if (!trainFound) {
            System.out.println("No train with ID " + passenger.trainId + " found.");
        }
    }

    public void boardPassengers(int trainId) {
        boolean trainFound = false;
        for (int i = 0; i < trains.size(); i++) {
            Train train = trains.get(i);
            if (train.id == trainId) {
                trainFound = true;
                while (!waitingQueue.isEmpty() && train.passengers.size() < train.capacity) {
                    Passenger passenger = waitingQueue.dequeue();
                    if (passenger.trainId == trainId) {
                        train.boardPassenger(passenger);
                    }
                }
                break;
            }
        }
        if (!trainFound) {
            System.out.println("Train with ID " + trainId + " not found.");
        }
    }

    public void reverseTrain(int trainId) {
        boolean trainFound = false;
        for (int i = 0; i < trains.size(); i++) {
            Train train = trains.get(i);
            if (train.id == trainId) {
                trainFound = true;
                train.reverseCompartments();
                break;
            }
        }
        if (!trainFound) {
            System.out.println("Train with ID " + trainId + " not found.");
        }
    }

    public void viewTrainInfo(int trainId) {
        boolean trainFound = false;
        for (int i = 0; i < trains.size(); i++) {
            Train train = trains.get(i);
            if (train.id == trainId) {
                trainFound = true;
                System.out.println(train.toString());
                break;
            }
        }
        if (!trainFound) {
            System.out.println("Train with ID " + trainId + " not found.");
        }
    }

    public void viewStationInfo() {
        System.out.println("Station Information:");
        for (int i = 0; i < trains.size(); i++) {
            Train train = trains.get(i);
            System.out.println(train.toString());
        }
    }
}

public class TrainSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrainStation station = new TrainStation();

        while (true) {
            System.out.println("\n=== Train Station Simulation ===");
            System.out.println("1. Add Train");
            System.out.println("2. Add Passenger");
            System.out.println("3. Board Passengers for Train");
            System.out.println("4. Reverse Train Compartments");
            System.out.println("5. View Train Information");
            System.out.println("6. View Station Information");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter Train ID: ");
                    int trainId = scanner.nextInt();
                    System.out.print("Enter Train Capacity: ");
                    int capacity = scanner.nextInt();
                    station.addTrain(new Train(trainId, capacity));
                    System.out.println("Train added successfully.");
                    break;

                case 2:
                    System.out.print("Enter Passenger ID: ");
                    int passengerId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Passenger Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Train ID for Passenger: ");
                    int trainIdForPassenger = scanner.nextInt();
                    station.addPassenger(new Passenger(passengerId, name, trainIdForPassenger));
                    break;

                case 3:
                    System.out.print("Enter Train ID to Board Passengers: ");
                    int boardTrainId = scanner.nextInt();
                    station.boardPassengers(boardTrainId);
                    break;

                case 4:
                    System.out.print("Enter Train ID to Reverse Compartments: ");
                    int reverseTrainId = scanner.nextInt();
                    station.reverseTrain(reverseTrainId);
                    break;

                case 5:
                    System.out.print("Enter Train ID to View Information: ");
                    int viewTrainId = scanner.nextInt();
                    station.viewTrainInfo(viewTrainId);
                    break;

                case 6:
                    station.viewStationInfo();
                    break;

                case 7:
                    System.out.println("Exiting simulation. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
