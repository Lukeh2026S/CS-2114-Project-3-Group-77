import java.util.Scanner;
public class Main {
    public static void main(String args[]) {
        Airplane plane = new Airplane(101, "Atlanta", 400, "Standard");
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter your name: ");
        String name;
        while(true) {
            name = sc.nextLine();
            name = name.trim();
            if(name.isBlank()){
                System.out.print("Please enter your name: ");
            } else {
                break;
            }
        }
        System.out.println(plane.getAvailableSeatCount());
        System.out.println(plane.getDestination());
        System.out.println(plane.getDistance());
        System.out.println(plane.getSeatsPerRow());
        System.out.println(plane.getSeatsPerRowVip());
        System.out.println(plane.getRows());
        System.out.println(plane.getVipRows());

        Ticket ticket = plane.buyChosenSeat(name, 5, "A".charAt(0));
        System.out.println(plane.getAvailableSeatCount());
        Boarding boarding = new Boarding();
        boarding.addToLine(ticket, ticket.getBoardingGroup());
        System.out.println(boarding.getQueue(ticket.getBoardingGroup()).peekFirst().getName());
        System.out.println(boarding.getQueue(ticket.getBoardingGroup()).peekFirst().getSeatChar());
        System.out.println(boarding.getQueue(ticket.getBoardingGroup()).peekFirst().getSeatNum());
        plane.displaySeatChart();
    }
}
