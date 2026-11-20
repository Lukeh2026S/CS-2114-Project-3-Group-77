import java.io.UncheckedIOException;
import java.util.NoSuchElementException;
import java.util.Random;

public class Main {
    public static void main(String args[]) {
        Random rng = new Random();
        Airplane[] flights = {
            new Airplane(101, "Atlanta", 450, "Standard", rng),
            new Airplane(102, "Chicago", 600, "Standard", rng),
            new Airplane(103, "Los Angeles", 2200, "Standard", rng)
        };
        UserSim[] sims = new UserSim[flights.length];
        for (int i = 0; i < flights.length; i++) {
            int fill = (int) (flights[i].getTotalSeats() * (0.2 + 0.4 * rng.nextDouble()));
            sims[i] = new UserSim(fill, rng);
        }

        Boarding boarding = new Boarding();
        Airplane chosen = null;
        Menu view = new Menu();
        try {
            view.showMessage("Welcome to Lift Off!");
            String name = null;
            while (name == null) {
                try {
                    String raw = view.ask("Your name: ");
                    if (raw == null || raw.isBlank()) {
                        throw new IllegalArgumentException("Name cannot be blank");
                    }
                    name = raw.trim();
                } catch (IllegalArgumentException e) {
                    view.showError(e.getMessage());
                }
            }
         
            boolean vip = askYesNo(view, "Are you a VIP member? (y/n): ");
            User user = new User(name, 3000, vip);
            

            //Gameloop
            boolean running = true;
            while (running) {
                view.showMainMenu(user.getName(), user.getMoney(), user.getMiles());
                int choice = askNumber(view, "Choose an option: ", 1, 3, false);
                if (chosen != null) {
                        view.showSeatMap(chosen.getVipSeat(), chosen.getSeat(), chosen.getPrices());
                }

                if (choice == 1) {
                    // choose a flight and fill the plane with simulated passengers
                    if (chosen != null) {
                        view.showError("You already picked a flight to " + chosen.getDestination());
                    }
                        view.showFlights(flights);
                        int pick = askNumber(view, "Pick a flight (b to go back): ", 1, flights.length, true);
                        if (pick != -1) {
                            chosen = flights[pick - 1];
                            sims[pick - 1].populate(chosen, boarding);
                            view.showMessage("You picked the flight to " + chosen.getDestination() + ".");
                        }
                } else if (choice == 2) {
                    if (chosen == null) {
                        view.showError("Choose a flight first.");
                    } else if (user.getTicket() != null) {
                        view.showError("You already have a ticket.");
                    } else {
                        view.showSeatMap(chosen.getVipSeat(), chosen.getSeat(), chosen.getPrices());
                        int mode = askNumber(view, "1 = pick my seat, 2 = random seat (b to go back): ", 1, 2, true);
                        if (mode != -1) {
                            Ticket ticket = null;
                            if (mode == 1) {
                                boolean back = false;
                                while (ticket == null && !back) {
                                    String raw = view.ask("Seat, like 3C (b to go back): ");
                                    if (raw.trim().equalsIgnoreCase("b")) {
                                        back = true;
                                    } else {
                                        try {
                                            int[] seat = parseSeat(raw);
                                            ticket = chosen.buyChosenSeat(user, seat[0], seat[1]);
                                        } catch (IllegalArgumentException | IndexOutOfBoundsException
                                                 | IllegalStateException | UnsupportedOperationException e) {
                                            view.showError(e.getMessage());
                                        }
                                    }
                                }
                            } else {
                                try {
                                    ticket = chosen.buyRandomSeat(user);
                                } catch (NoSuchElementException | IllegalArgumentException e) {
                                    view.showError(e.getMessage());
                                }
                            }
                            if (ticket != null) {
                                boarding.addToLine(user);
                                view.showTicket(ticket.getName(), ticket.getPlane().getDestination(),
                                        ticket.getSeatNum(), ticket.getSeatChar(),
                                        ticket.getBoardingGroup(), ticket.getPrice());
                            }
                        }
                    }
                } else {
                    view.showMessage("Goodbye!");
                    running = false;
                }
            }
        } catch (UncheckedIOException e) {
            view.showMessage("");
            view.showMessage("Input ended. Goodbye!");
        }
        
    }

    private static int askNumber(Menu view, String prompt, int min, int max, boolean allowBack) {
        while (true) {
            String raw = view.ask(prompt);
            if (allowBack && raw.trim().equalsIgnoreCase("b")) {
                return -1;
            }
            try {
                String message = "Enter a whole number from " + min + " to " + max;
                if (raw == null) {
                    throw new IllegalArgumentException(message);
                }
                int value;
                try {
                    value = Integer.parseInt(raw.trim());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(message);
                }
                if (value < min || value > max) {
                    throw new IllegalArgumentException(message);
                }
                return value;
                
            } catch (IllegalArgumentException e) {
                view.showError(e.getMessage());
            }
        }
    }

    private static boolean askYesNo(Menu view, String prompt) {
        while (true) {
            try {
                String raw = view.ask(prompt);
                if (raw != null) {
                    String text = raw.trim().toLowerCase();
                    if (text.equals("y") || text.equals("yes")) {
                        return true;
                    }
                    if (text.equals("n") || text.equals("no")) {
                        return false;
                    }
                }
                throw new IllegalArgumentException("Please answer y or n");
            } catch (IllegalArgumentException e) {
                view.showError(e.getMessage());
            }
        }
    }

    static int[] parseSeat(String raw) {
        String message = "Enter a seat like 3C (row number, then a letter)";
        if (raw == null) {
            throw new IllegalArgumentException(message);
        }
        String text = raw.trim().toUpperCase();
        if (text.length() < 2 || text.length() > 4) {
            throw new IllegalArgumentException(message);
        }
        String rowText = text.substring(0, text.length() - 1);
        String letterText = text.substring(text.length() - 1);
        char letter = letterText.charAt(0);
        if (letter < 'A' || letter > 'Z') {
            throw new IllegalArgumentException(message);
        }
        for (int i = 0; i < rowText.length(); i++) {
            char digit = rowText.substring(i, i + 1).charAt(0);
            if (digit < '0' || digit > '9') {
                throw new IllegalArgumentException(message);
            }
        }
        return new int[] {Integer.parseInt(rowText), letter - 'A'};
    }

}
