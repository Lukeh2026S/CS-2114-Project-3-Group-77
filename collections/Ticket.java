public class Ticket {
    private final Airplane plane;
    private final String userName;
    private final int seatNum;
    private final char seatPos;
    private final int boardingGroup;
    private final boolean crew;
    public Ticket(Airplane plane, String userName, int seatNum, char seatPos, int boardingGroup) {
        this.plane = plane;
        this.userName = userName;
        this.seatNum = seatNum;
        this.seatPos = seatPos;
        this.boardingGroup = boardingGroup;
        this.crew = false;
    }
    
    public Airplane getPlane() {
        return this.plane;
    }
    
    public String getName() {
        return this.userName;
    }

    public int getSeatNum() {
        return this.seatNum;
    }

    public char getSeatChar() {
        return this.seatPos;
    }

    public int getBoardingGroup(){
        return this.boardingGroup;
    }
}
