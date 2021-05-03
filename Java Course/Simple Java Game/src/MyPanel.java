import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JPanel;

public class MyPanel extends JPanel{

    private static int n, m, x, y;

    private static int xPos, yPos;
    public static int visited[][] = new int[Mikolaj.n][Mikolaj.m];
    public static boolean miejscePrezentu[][] = new boolean[Mikolaj.n][Mikolaj.m];
    protected static LinkedList<Line> lista = new LinkedList<Line>();

    int nSize, mSize;

    public MyPanel(int n, int m, int x, int y)
    {
        this.setSize(x + 50, y + 50);
        this.x = x;
        this.y = y;
        this.n = n;
        this.m = m;

        nSize = x / n;
        mSize = y / m;

        makeGame();
    }

    private KeyListener gameKeyListener = new KeyAdapter()
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_A:
                    System.out.println("A");
                    xPos = (xPos - 1 + Mikolaj.n) % Mikolaj.n;
                    break;

                case KeyEvent.VK_D:
                    xPos = (xPos + 1) % Mikolaj.n;
                    break;

                case KeyEvent.VK_W:
                    yPos = (yPos - 1 + Mikolaj.n) % Mikolaj.m;
                    break;

                case KeyEvent.VK_S:
                    yPos = (yPos + 1) % Mikolaj.m;
                    break;

                case KeyEvent.VK_SPACE:
                    if(Mikolaj.prezenty > 0)
                    {
                        Mikolaj.prezenty--;
                        miejscePrezentu[xPos][yPos] = true;
                    }
                    break;

                case KeyEvent.VK_ENTER:
                    Mikolaj.endGame = false;
                    break;
            }
        }
    };

    void makeGame()
    {
        this.addKeyListener(gameKeyListener);
        this.setFocusable(true);
        this.requestFocus();

        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                visited[i][j] = 0;

        for(int i = 0; i <= m; i++)
        {
            Line l = new Line(1, i * mSize, x, i * mSize);
            lista.add(l);
        }

        for(int i = 0; i <= n; i++)
        {
            Line l = new Line(i * nSize, 1,i * nSize, y);
            lista.add(l);
        }

        xPos = (int) Math.floor(Math.random() * Mikolaj.n);
        yPos = (int) Math.floor(Math.random() * Mikolaj.m);

        while(MyPanel.tryToVisit(xPos, yPos, 20) == false)
        {
            xPos = (int) Math.floor(Math.random() * Mikolaj.n);
            yPos = (int) Math.floor(Math.random() * Mikolaj.m);
        }
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        if(Mikolaj.endGame == false)
        {
             for(Line l: lista)
                 g.drawLine(l.x1, l.y1, l.x2, l.y2);

             for(Dziecko d : MyFrame.dzieci)
             {

                 if(d.awake == 1)
                 {
                     g.setColor(Color.GREEN);
                     g.fillOval(d.x * nSize, d.y * mSize, nSize, mSize);
                 }
                 if(d.awake == 0)
                 {
                     g.setColor(Color.GRAY);
                     g.fillOval(d.x * nSize, d.y * mSize, nSize, mSize);
                 }
                 if(d.awake == 2)
                 {
                     g.setColor(new Color(0,60,40));
                     g.fillOval(d.x * nSize, d.y * mSize, nSize, mSize);
                     g.setColor(Color.BLACK);
                     g.drawString("Zzz", d.x * nSize + 26, d.y * mSize + 30);
                 }

                 g.setColor(Color.BLACK);
                 g.drawString(Integer.toString(d.id), d.x * nSize, (d.y + 1) * mSize);
             }

             g.drawString("Santa", xPos * nSize + 15, yPos * mSize + 15);
             g.setColor(Color.RED);
             g.fillOval(xPos * nSize, yPos * mSize, nSize, mSize);

             g.setColor(Color.ORANGE);
             for(int i = 0; i < n; i++)
                 for(int j = 0; j < m; j++)
                     if(miejscePrezentu[i][j] == true)
                         g.fillOval(i * nSize + 17, j * mSize + 15, nSize/2, mSize/2);
        }
    }

    public static synchronized void setVisited(int a, int b, int id)
    {
        visited[a][b] = id;
    }

    public static synchronized int getSantaPositionX()
    {
        return MyPanel.xPos;
    }

    public static synchronized int getSantaPositionY()
    {
        return MyPanel.yPos;
    }

    public static synchronized void setSantaPositionX(int x)
    {
        MyPanel.xPos = x;
    }

    public static synchronized void setSantaPositionY(int y)
    {
        MyPanel.yPos = y;
    }

    public static synchronized boolean tryToVisit(int x, int y, int id)
    {
        if(visited[x][y] != 0)
            return false;
        else
            visited[x][y] = id;

        return true;
    }

    public static synchronized void setGift(int x, int y, boolean b)
    {
        miejscePrezentu[x][y] = b;
    }

    public static synchronized boolean getGift(int x, int y)
    {
        return miejscePrezentu[x][y];
    }
}
