import sun.awt.SubRegionShowable;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BlendedAmerica{
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
            double total = REP+DEM+OTH;
            Color Blend = new Color((int)((REP/total)*255), (int)((OTH/total)*255), (int)((DEM/total)*255));
            color = Blend;
            }
    }

    public static void main(String[] args) throws Exception {
        String region = "USA-county";
        String year = "2000";
        for(int i = 1960 ; i <= 2016 ; i+=4) {
            getGeoData(region);
            getVotingData(Integer.toString(i));
            draw(region, Integer.toString(i));
            Thread.sleep(2000);
        }

//        getGeoData(region);
//        getVotingData(year);
//        draw(region, year);
    }
    public static void getVotingData(String year) throws FileNotFoundException {
        for (String key : allRegions.keySet()) {
            File f2 = new File("./input/" + key + year + ".txt");
            Scanner inputObjectVotes = new Scanner(f2);
            inputObjectVotes.nextLine();
            while (inputObjectVotes.hasNextLine()) {
                String[] votes = inputObjectVotes.nextLine().split(",");
                if (allRegions.get(key).containsKey(votes[0])) {
                    allRegions.get(key).get(votes[0]).changeVotes(votes);
                }
            }
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

            if(subRegionName.contains(" Parish"))
            {
                subRegionName = subRegionName.substring(0,subRegionName.indexOf(" Parish"));
            }
            if(subRegionName.contains(" city"))
            {
                subRegionName = subRegionName.substring(0,subRegionName.indexOf(" city"));
            }

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
    public static void draw(String region, String year) throws FileNotFoundException
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
                    if(allRegions.get(key).get(innerKey).color == null) {
                        System.out.println(innerKey);
                        allRegions.get(key).get(innerKey).color = StdDraw.BLACK;
                    }
                    StdDraw.setPenColor(allRegions.get(key).get(innerKey).color);
                    StdDraw.filledPolygon(allRegions.get(key).get(innerKey).xcoords.get(i), allRegions.get(key).get(innerKey).ycoords.get(i));
                }
            }
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.textLeft(dub[0], dub[2] + .5, year);
        StdDraw.show();
    }

}
