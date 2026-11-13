public class Main {
    public static void main(String args[]) {
        Airplane plane = new Airplane(101, "Atlanta", 400, "Standard");
        plane.displaySeatChart();
        Ticket ticket = plane.buyChosenSeat("Sam", 5, "A".charAt(0));
        Boarding boarding = new Boarding();
        boarding.addToLine(ticket, ticket.getBoardingGroup());
        System.out.println(boarding.getQueue(ticket.getBoardingGroup()).peekFirst().getName());
        System.out.println(boarding.getQueue(ticket.getBoardingGroup()).peekFirst().getSeatChar());
        System.out.println(boarding.getQueue(ticket.getBoardingGroup()).peekFirst().getSeatNum());
        plane.displaySeatChart();
    }
}
