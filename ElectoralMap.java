import sun.awt.SubRegionShowable;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ElectoralMap{
    private class subRegion{
        String name;
        double[] xcoords;
        double[] ycoords;
        int[] votes;
        Color color;
        private subRegion(double[] x,double[] y,String myname)
        {
            name = myname;
            xcoords = x;
            ycoords = y;
        }
        public void changeVotes(String[] newVotes)
        {
            votes[0] = Integer.parseInt(newVotes[1]);
            votes[1] = Integer.parseInt(newVotes[2]);
            votes[2] = Integer.parseInt(newVotes[3]);
        }

    }

    public static void main(String[] args) throws Exception {
        visualize("WV","1988");
    }

    public static void visualize(String region, String year) throws Exception {
        String[] firstLine = new String[4];
        File f1 = new File("./input/" + region + ".txt");
        Scanner inputObjectCoords = new Scanner(f1);
        File f2 = new File("./input/" + region + year + ".txt");
        Scanner inputObjectVotes = new Scanner(f2);
        inputObjectVotes.nextLine();
        String[] line = new String[2];
        String[] line2 = new String [2];
        line = inputObjectCoords.nextLine().split("   ");
        line2 = inputObjectCoords.nextLine().split("   ");
        StdDraw.enableDoubleBuffering();
        double[] dub = new double[4];
        dub[0] = Double.parseDouble(line[0]);
        dub[1] = Double.parseDouble(line2[0]);
        dub[2] = Double.parseDouble(line[1]);
        dub[3] = Double.parseDouble(line2[1]);
        int xSize = (int) (dub[1] - dub[0]);
        int ySize = (int) ((dub[3] - dub[2]));

        StdDraw.setCanvasSize(512 * (xSize / ySize), 512);
        StdDraw.setXscale(dub[0], dub[1]);
        StdDraw.setYscale(dub[2], dub[3]);

        int numRegions = Integer.parseInt(inputObjectCoords.nextLine());
        int numRegionsExecuted = 0;
        while (numRegionsExecuted < numRegions)
        {
            inputObjectCoords.nextLine();
            String [] votes = inputObjectVotes.nextLine().split(",");
            String regionName = inputObjectCoords.nextLine();
            String mapName = inputObjectCoords.nextLine();
            int numPoints = Integer.parseInt(inputObjectCoords.nextLine().trim());
            int numPointsExecuted = 0;
            double[] x = new double[numPoints];
            double[] y = new double[numPoints];
            while (numPointsExecuted < numPoints)
            {
                String[] coords = inputObjectCoords.nextLine().split("   ");
                x[numPointsExecuted] = Double.parseDouble(coords[0].trim());
                y[numPointsExecuted] = Double.parseDouble(coords[1].trim());
                numPointsExecuted++;
            }
            if(Integer.parseInt(votes[1]) > Integer.parseInt(votes [2]) && Integer.parseInt(votes[1]) > Integer.parseInt(votes [3])){
                StdDraw.setPenColor(StdDraw.RED);
            }
            else if(Integer.parseInt(votes[2]) > Integer.parseInt(votes [1]) && Integer.parseInt(votes[2]) > Integer.parseInt(votes [3])){
                StdDraw.setPenColor(StdDraw.BLUE);
            }
            else {
                StdDraw.setPenColor(StdDraw.GREEN);
            }
            StdDraw.filledPolygon(x,y);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.polygon(x,y);
            numRegionsExecuted++;
        }
        inputObjectCoords.close();

        StdDraw.show();
    }
}
