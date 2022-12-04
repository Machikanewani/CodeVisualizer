package code;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class drawBluePrint extends JPanel {
    private final ArrayList<ArrayList<String>> list;
    private static int cnt = 0;
    public drawBluePrint(ArrayList<ArrayList<String>> list){
        this.setBackground(Color.white);
        this.list = list;
    }

    public void draw(String className){
        JFrame frame = new JFrame(className + " BluePrint");
        frame.setLocation(cnt * 50, cnt * 50);
        cnt++;
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(900, 450));
        drawBluePrint panel = new drawBluePrint(list);
        panel.setPreferredSize(new Dimension(900, 450));
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        int size = list.size();
        int windowWidth = this.getWidth();
        int windowHeight = this.getHeight();

        //calculate number of rows
        int rows;
        int remainder = size % 5;
        if(remainder == 0) rows = size / 5;
        else rows = size / 5 + 1;

        g.setFont(new Font(g.getFont().getFontName(), Font.ITALIC, g.getFont().getSize()));

        //get max value of row
        int rowMax = Integer.MIN_VALUE;
        for(int i = 0; i < rows; i++){
            int rowTotal = 0;
            for(int j = i; j < i * 5 + 5 && j < size; j++){
                rowTotal += Integer.parseInt(list.get(j).get(1));
            }
            rowMax = Math.max(rowMax, rowTotal);
        }

        int widthSpaceNum = 2;
        //calculate
        double tmpWidthUnit = windowWidth / rowMax;
        double widthUnit = (windowWidth - 6 * widthSpaceNum * tmpWidthUnit) / rowMax;

        //get max value of column
        int columnMax = 0;

        for(int i = 0; i < rows; i++){
            int rowColMax = Integer.MIN_VALUE;
            for(int j = i; j < size && j < i * 5 + 5; j++){
                rowColMax = Math.max(rowColMax, Integer.parseInt(list.get(j).get(2)));
            }
            columnMax += rowColMax;
        }

        int heightSpaceNum = 2;
        //calculate LOC unit of height
        double tmpHeightUnit = windowHeight / columnMax;
        double heightUnit = (windowHeight - (rows + 1) * heightSpaceNum * tmpHeightUnit) / columnMax;

        //draw rectangles
        int widthSpace = (int)(2 * widthSpaceNum * widthUnit);
        int heightSpace = (int)(2 * heightSpaceNum * heightUnit);
        int strHeight = (int)g.getFontMetrics().getLineMetrics(list.get(0).get(0),g).getHeight();
        int x, y = heightSpace + strHeight;
        int hMax;
        for(int i = 0; i < rows; i++){
            int col = 0;
            hMax = 0;
            x = widthSpace;
            for(int j = i * 5; col < 5 && j < size; j++){
                int width = (int)(Integer.parseInt(list.get(j).get(1)) * widthUnit);
                int height = (int)(Integer.parseInt(list.get(j).get(2)) * heightUnit);
                int strWidth = g.getFontMetrics().stringWidth(list.get(j).get(0));

                if(width < strWidth){
                    g.setColor(Color.BLACK);
                    g.drawString(list.get(j).get(0), x, y);
                    int recX = x + (strWidth / 2) - (width / 2);
                    g.setColor(new Color(100, 210, 200));
                    g.drawRect(recX, y + 4, width, height);
                    g.setColor(new Color(180, 250, 240));
                    g.fillRect(recX, y + 4, width, height);
                }
                else{
                    g.setColor(Color.BLACK);
                    int strX = x + (width / 2) - (strWidth / 2);
                    g.drawString(list.get(j).get(0), strX, y);
                    g.setColor(new Color(100, 210, 200));
                    g.drawRect(x, y + 4, width, height);
                    g.setColor(new Color(180, 250, 240));
                    g.fillRect(x, y + 4, width, height);
                }
                int xPlus = Math.max(width,strWidth);
                x += widthSpace + xPlus;
                hMax = Math.max(hMax, height);
                col++;
            }
            y += heightSpace + hMax + 20;
        }
    }
}
