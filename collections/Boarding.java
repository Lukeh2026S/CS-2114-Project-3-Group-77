public class Boarding {
     static class TicketLine {
        private static class Node {
            final Ticket ticket;
            Node prev;
            Node next;

            Node(Ticket ticket) {
                this.ticket = ticket;
            }
        }

        private Node head;
        private Node tail;
        private int size;

        void addFirst(Ticket ticket) {
            Node node = new Node(ticket);
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

        void addLast(Ticket ticket) {
            Node node = new Node(ticket);
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

        Ticket removeFirst() {
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
            return removed.ticket;
        }

        Ticket peekFirst() {
            if (head == null) {
                return null;
            }
                return head.ticket;
        }

        Ticket peekLast() {
            if (tail == null) {
                return null;
            }
                return tail.ticket;
        }

        int indexOf(Ticket ticket) {
            int i = 0;
            for (Node n = head; n != null; n = n.next) {
                if (n.ticket == ticket) {
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
    }

    private final TicketLine[] queues = new TicketLine[4];
    public Boarding() {
        for (int i = 0; i < 4; i++) {
            queues[i] = new TicketLine();
        }
    }

    public void addToLine(Ticket ticket, int boardingGroup) {
        if (boardingGroup < 0 || boardingGroup >= 4) {
            throw new IllegalArgumentException("Boarding group must be between 0 and "
                    + 3);
        }
        if (ticket == null) {
            throw new IllegalArgumentException("Cannot add a null ticket to the line.");
        }
        queues[boardingGroup].addLast(ticket);
    }

    public TicketLine getQueue(int boardingGroup) {
        return queues[boardingGroup];
    }
}
