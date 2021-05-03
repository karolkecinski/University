import javax.swing.JOptionPane;

public class Dziecko implements Runnable
{
    public int x, y, id;
    public boolean active = true;
    public int awake = 1;
    public static int   xDirection[] = {1, -1, 0, 0},
                        yDirection[] = {0, 0, -1, 1};

    public Dziecko(int id)
    {
        this.id = id;
        x = (int) Math.floor(Math.random() * Mikolaj.n);
        y = (int) Math.floor(Math.random() * Mikolaj.m);

        while(MyPanel.tryToVisit(x, y, id) == false)
        {
            x = (int) Math.floor(Math.random() * Mikolaj.n);
            y = (int) Math.floor(Math.random() * Mikolaj.m);
        }
    }

    @Override
    public void run()
    {
        while(active)
            try
            {
                if(distToSanta(x, y) <= 5 && awake == 1)
                {
                    if(Math.random() > 0.2)
                    {
                        chaseSanta();
                        Thread.currentThread().sleep((int)Math.random()*1000 + 1000);

                        moveAction();

                    } else {

                        int nx, ny;

                        do {
                            int choice = (int) (Math.random() * 4);
                            nx = (x + xDirection[choice] + Mikolaj.n) % Mikolaj.n;
                            ny = (y + yDirection[choice] + Mikolaj.m) % Mikolaj.m;
                        }
                        while(!MyPanel.tryToVisit(nx, ny, id));

                        awake = 2;
                        Thread.currentThread().sleep((int)(Math.random() * 4000 + 4000));
                        awake = 1;

                        moveAction();
                    }
                }
                else
                {
                    randomMove();
                    Thread.currentThread().sleep((int)((Math.random()) * 2000 + 1000));
                }
            } catch (InterruptedException e)
            {
                return;
            }
    }

    private void chaseSanta()
    {
        if(distToSanta(x, y) == 1 && !Mikolaj.endGame) {
            Mikolaj.endGame = true;
            JOptionPane.showMessageDialog(null, "You have lost!");
        }

        boolean lookingForDirection = true;

        for(int d = 0; d < 4; d++)
            if(lookingForDirection)
            {
                int xP = ((x + xDirection[d] + Mikolaj.n) % Mikolaj.n);
                int yP = ((y + yDirection[d] + Mikolaj.m) % Mikolaj.m);

                if(distToSanta(xP, yP) < distToSanta(x, y) && MyPanel.visited[xP][yP] == 0)
                {
                    MyPanel.setVisited(x, y, 0);
                    x = xP;
                    y = yP;
                    MyPanel.setVisited(x, y, id);

                    lookingForDirection = false;
                }
            }
    }

    private synchronized int distToSanta(int x, int y)
    {
        return (Math.min(Math.abs(x - MyPanel.getSantaPositionX()), Mikolaj.n - Math.abs(x - MyPanel.getSantaPositionX()))
                + Math.min(Math.abs(y - MyPanel.getSantaPositionY()), Mikolaj.m - Math.abs(y - MyPanel.getSantaPositionY())));
    }

    private synchronized void randomMove()
    {
        moveAction();
        int xP, yP;
        do {
            int r = (int) Math.floor(Math.random() * 4);
            xP = ((x + xDirection[r] + Mikolaj.n) % Mikolaj.n);
            yP = ((y + yDirection[r] + Mikolaj.m) % Mikolaj.m);
        }
        while(MyPanel.visited[xP][yP] != 0);

        MyPanel.setVisited(x, y, 0);
        x = xP;
        y = yP;
        MyPanel.setVisited(x, y, id);
    }

    private void moveAction()
    {
        for(int d = 0; d < 4; d++)
            if(MyPanel.miejscePrezentu  [(x + xDirection[d] + Mikolaj.n) % Mikolaj.n]
                    [(y + yDirection[d] + Mikolaj.m) % Mikolaj.m]){
                int xP = ((x + xDirection[d] + Mikolaj.n) % Mikolaj.n);
                int yP = ((y + yDirection[d] + Mikolaj.m) % Mikolaj.m);

                MyPanel.setVisited(xP, yP, id);
                MyPanel.setGift(xP, yP, false);
                Mikolaj.liczbaDzieci--;

                x = xP;
                y = yP;
                awake = 0;
                active = false;

                if(Mikolaj.liczbaDzieci == 0)
                {
                    JOptionPane.showMessageDialog(null, "You win!");
                }

                break;
            }
    }
}
