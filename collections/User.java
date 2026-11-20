public class User {
    private final String name;
    private int money;
    private int miles;
    private final boolean vip;
    private Ticket ticket;
    final boolean sim;
    final boolean crew;

    public User(String name, int money, boolean vip) {
        this(name, money, vip, false, false);
    }

    User(String name, int money, boolean vip, boolean sim, boolean crew) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
        this.name = name.trim();
        this.money = money;
        this.vip = vip;
        this.sim = sim;
        this.crew = crew;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public int getMiles() {
        return miles;
    }

    public boolean isVip() {
        return vip;
    }

    public Ticket getTicket() {
        return ticket;
    }
    void spend(int amount) {
        if (amount > money) {
            throw new IllegalArgumentException(
                    "Not enough money: that costs $" + amount + " and you have $" + money);
        }
        money -= amount;
    }

    void addMiles(int distance) {
        miles += distance;
    }

    void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}