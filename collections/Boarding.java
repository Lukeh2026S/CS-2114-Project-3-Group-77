import java.util.NoSuchElementException;

public class Boarding {
     public static class Queue {
        private static class Node {
            private final User data;
            Node prev;
            Node next;

            Node(User user) {
                this.data = user;
            }
        }
        private Node head;
        private Node tail;
        private int size;

        void addFirst(User user) {
            Node node = new Node(user);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                node.next = head;
                head.prev = node;
                head = node;
            }
            size++;
        }

        void addLast(User user) {
            Node node = new Node(user);
            if (tail == null) {
                head = node;
                tail = node;
            } else {
                node.prev = tail;
                tail.next = node;
                tail = node;
            }
            size++;
        }

        User removeFirst() {
            if (head == null) {
                return null;
            }
            Node removed = head;
            head = head.next;
            if (head == null) {
                tail = null;
            } else {
                head.prev = null;
            }
            size--;
            return removed.data;
        }

        User peekFirst() {
            if (head == null) {
                return null;
            }
                return head.data;
        }

        User peekLast() {
            if (tail == null) {
                return null;
            }
                return tail.data;
        }

        int indexOf(User user) {
            int i = 0;
            for (Node n = head; n != null; n = n.next) {
                if (n.data == user) {
                    return i;
                }
                i++;
            }
            return -1;
        }

        int size() {
            return size;
        }

        boolean isEmpty() {
            return size == 0;
        }

        public boolean contains(User user) {
            return indexOf(user) != -1;
        }
    }

    private final Queue[] queues = new Queue[4];
    private final Queue boarded = new Queue();
    public Boarding() {
        for (int i = 0; i < 4; i++) {
            queues[i] = new Queue();
        }
    }

    public Queue getQueue(int boardingGroup) {
        return queues[boardingGroup];
    }

    public void addToLine(User user) {
        int g = groupOf(user);
        if (queues[g].contains(user) || boarded.contains(user)) {
            throw new IllegalArgumentException("That user is already in a line");
        }
        queues[g].addLast(user);
    }

    public void addToLineFirst(User user) {
        int g = groupOf(user);
        if (queues[g].contains(user) || boarded.contains(user)) {
            throw new IllegalArgumentException("That user is already in a line");
        }
        queues[g].addFirst(user);
    }

    public User dequeue() {
        for (int g = 0; g < 4; g++) {
            if (!queues[g].isEmpty()) {
                return queues[g].removeFirst();
            }
        }
        throw new NoSuchElementException("Nobody is waiting to board");
    }

    public User peek() {
        for (int g = 0; g < 4; g++) {
            if (!queues[g].isEmpty()) {
                return queues[g].peekFirst();
            }
        }
        throw new NoSuchElementException("Nobody is waiting to board");
    }

    public boolean isEmpty() {
        for (int g = 0; g < 4; g++) {
            if (!queues[g].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private int groupOf(User u) {
        if (u == null) {
            throw new IllegalArgumentException("Cannot queue a null user");
        }
        if (u.crew) {
            return 0;
        }
        if (u.getTicket() == null) {
            throw new IllegalArgumentException("Only users with a ticket can board");
        }
        return u.getTicket().getBoardingGroup();
    }

    public static int groupFor(boolean vip, boolean randomSeat, boolean rearHalf) {
        if (vip) {
            return 0;
        }
        if (randomSeat) {
            return 3;
        }
        if (rearHalf) {
            return 1;
        }
        return 2;
    }
}
