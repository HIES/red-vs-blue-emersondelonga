import sun.awt.SubRegionShowable;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ElectoralMap{
    public static HashMap<String, HashMap<String, SubRegion>> allRegions = new HashMap<>();
    private static class SubRegion{
        String name;
        ArrayList<double[]> xcoords;
        ArrayList<double[]> ycoords;
        int REP;
        int DEM;
        int OTH;
        Color color;
        public SubRegion(double[] x,double[] y,String myname)
        {
            xcoords = new ArrayList<>();
            ycoords = new ArrayList<>();
            name = myname;
            xcoords.add(x);
            ycoords.add(y);
        }
        public void changeVotes(String[] newVotes)
        {
            REP = Integer.parseInt(newVotes[1]);
            DEM = Integer.parseInt(newVotes[2]);
            OTH = Integer.parseInt(newVotes[3]);
            if( REP > DEM && REP > OTH){
                color = StdDraw.RED;
            }
            else if(DEM > REP && DEM > OTH){
                color = StdDraw.BLUE;
            }
            else {
                color = StdDraw.GREEN;
            }
        }

    }

    public static void main(String[] args) throws Exception {
        getGeoData("GA");
        getVotingData("GA","2000");
        draw("GA");
    }
    public static void getVotingData(String region, String year) throws FileNotFoundException
    {
        File f2 = new File("./input/" + region + year + ".txt");
        Scanner inputObjectVotes = new Scanner(f2);
        inputObjectVotes.nextLine();
        while (inputObjectVotes.hasNextLine())
        {
            String[] votes = inputObjectVotes.nextLine().split(",");
            allRegions.get(region).get(votes[0]).changeVotes(votes);
        }
    }
    public static void getGeoData(String region) throws FileNotFoundException
    {
        File f1 = new File("./input/" + region + ".txt");
        Scanner inputObjectCoords = new Scanner(f1);
        inputObjectCoords.nextLine(); // Skip canvas calibration
        inputObjectCoords.nextLine();

        int numRegions = Integer.parseInt(inputObjectCoords.nextLine());
        int numRegionsExecuted = 0;
        while (numRegionsExecuted < numRegions) {
            inputObjectCoords.nextLine();
            String subRegionName = inputObjectCoords.nextLine();
            String regionName = inputObjectCoords.nextLine();
            int numPoints = Integer.parseInt(inputObjectCoords.nextLine().trim());
            int numPointsExecuted = 0;
            double[] x = new double[numPoints];
            double[] y = new double[numPoints];
            while (numPointsExecuted < numPoints) {
                String[] coords = inputObjectCoords.nextLine().split("   ");
                x[numPointsExecuted] = Double.parseDouble(coords[0].trim());
                y[numPointsExecuted] = Double.parseDouble(coords[1].trim());
                numPointsExecuted++;
            }
            if (!allRegions.containsKey(regionName)) {
                HashMap<String, SubRegion> regionHashMap = new HashMap<>();
                allRegions.put(regionName, regionHashMap);
            }
            if (!allRegions.get(regionName).containsKey(subRegionName)) {
                SubRegion tempSubRegion = new SubRegion(x, y, subRegionName);
                allRegions.get(regionName).put(subRegionName, tempSubRegion);
            } else {
                allRegions.get(regionName).get(subRegionName).xcoords.add(x);
                allRegions.get(regionName).get(subRegionName).ycoords.add(y);
            }
            numRegionsExecuted++;
        }
    }
    public static void draw(String region) throws FileNotFoundException
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
        for (String key: allRegions.keySet())
        {
            for (String innerKey: allRegions.get(key).keySet())
            {
                for (int i = 0; i < allRegions.get(key).get(innerKey).xcoords.size(); i++)
                {
                    StdDraw.setPenColor(allRegions.get(key).get(innerKey).color);
                    StdDraw.filledPolygon(allRegions.get(key).get(innerKey).xcoords.get(i), allRegions.get(key).get(innerKey).ycoords.get(i));
                }
            }
        }
        StdDraw.show();
    }

}
