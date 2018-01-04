import sun.awt.SubRegionShowable;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ElectoralMap{
    static HashMap<String, HashMap<String, subRegion> allRegions = new HashMap<>();
    private static class subRegion{
        String name;
        ArrayList<double[]> xcoords;
        ArrayList<double[]> ycoords;
        int[] votes;
        Color color;
        public subRegion(double[] x,double[] y,String myname)
        {
            name = myname;
            xcoords.add(x);
            ycoords.add(y);
        }
        public void changeVotes(String[] newVotes)
        {
            votes[0] = Integer.parseInt(newVotes[1]);
            votes[1] = Integer.parseInt(newVotes[2]);
            votes[2] = Integer.parseInt(newVotes[3]);
        }

    }

    public static void main(String[] args) throws Exception {
        getGeoData();
        getVotingData();
        draw();
    }
    public static void getVotingData(String region, String year)
    {
        Scanner inputObjectCoords = new Scanner(f1);
        File f2 = new File("./input/" + region + year + ".txt");
        Scanner inputObjectVotes = new Scanner(f2);
        inputObjectVotes.nextLine();
    }
    public static void getGeoData(String region)
    {
        File f1 = new File("./input/" + region + ".txt");
        Scanner inputObjectCoords = new Scanner(f1);
        inputObjectCoords.nextLine(); // Skip canvas calibration
        inputObjectCoords.nextLine();

        int numRegions = Integer.parseInt(inputObjectCoords.nextLine());
        int numRegionsExecuted = 0;
        while (numRegionsExecuted < numRegions)
        {
            inputObjectCoords.nextLine();
            String subRegionName = inputObjectCoords.nextLine();
            String regionName = inputObjectCoords.nextLine();
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
            if(!allRegions.containsKey(regionName))
            {
                HashMap<String, subRegion> regionHashMap = new HashMap<>();
              allRegions.put(regionName, regionHashMap);
            }
            if(!allRegions.get(regionName).containsKey(subRegionName)) {
                subRegion tempSubRegion = new subRegion(x, y, subRegionName);
                allRegions.get(regionName).put(subRegionName, tempSubRegion );
            }
            else
            {
                allRegions.get(regionName).get(subRegionName).xcoords.add(x);
                allRegions.get(regionName).get(subRegionName).ycoords.add(y);
            }
            }
    }
    public static void draw(String region)
    {
        String[] firstLine = new String[4];
        File f1 = new File("./input/" + region + ".txt");
        Scanner inputObjectCoords = new Scanner(f1);
        String[] line;
        String[] line2;
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
        inputObjectCoords.close();
    }

}
