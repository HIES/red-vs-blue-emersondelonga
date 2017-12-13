import sun.awt.SubRegionShowable;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class ElectoralMap{
    private class subRegion{
        String name;
        double[] xcoords;
        double[] ycoords;
        int[] votes;
        Color color;
        private SubRegion(double[] x,double[] y,String myname)
        {
            name = myname;
            xcoords = x;
            ycoords = y;
        }
    }

    public static void makeElectoralMap(String region, String year, HashMap electoralMap) {
        HashMap<String, subRegion> map = new HashMap<>();
        makeMapAddCoords(region);
        addVotesDraw();
        
    }
    public HashMap makeMapAddCoords(String region, HashMap map)
    {
        String[] firstLine = new String[4];
        File f = new File("./input/USA.txt");
        Scanner inputObject = new Scanner(f);
        String[] line = new String[2];
        String[] line2 = new String [2];
        line = inputObject.nextLine().split("   ");
        line2 = inputObject.nextLine().split("   ");
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

        int numRegions = Integer.parseInt(inputObject.nextLine());
        int numRegionsExecuted = 0;
        while (numRegionsExecuted < numRegions) {
            inputObject.nextLine();
            String regionName = inputObject.nextLine();
            String mapName = inputObject.nextLine();
            int numPoints = Integer.parseInt(inputObject.nextLine().trim());
            int numPointsExecuted = 0;
            double[] x = new double[numPoints];
            double[] y = new double[numPoints];
            while (numPointsExecuted < numPoints) {
                String[] coords = inputObject.nextLine().split("   ");
                x[numPointsExecuted] = Double.parseDouble(coords[0].trim());
                y[numPointsExecuted] = Double.parseDouble(coords[1].trim());
                numPointsExecuted++;
            }
            map.put(regionName, new subRegion(x, y, regionName));
            numRegionsExecuted++;
        }
        return map;
    }
    public void addVotesDraw(String region, String year)
    {
        
    }
}
