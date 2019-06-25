package snake;

public class Main {

    private static final int DELAY = 120;
    private static final int BOARDWIDTH = 50;
    private static final int BOARDHEIGHT = 30;

    public static void main(String[] args) {
        System.out.println("Welcome to ASCII snake!\nHave fun!\n");
        Game g = new Game(BOARDWIDTH, BOARDHEIGHT);
        while (!g.isOver()){
            g.refreshBoard();
            g.drawBoard();

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Game over!");
    }

}
