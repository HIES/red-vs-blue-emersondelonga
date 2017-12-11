import java.io.File;
import java.util.Scanner;
import java.lang.Exception;
import java.lang.String;

public class EmptyMap {
    public static void main(String[] args) throws Exception {

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
        StdDraw.setXscale(dub[0], dub[2]);
        StdDraw.setYscale(dub[1], dub[3]);

        StdDraw.circle(dub[0] + 2, dub[1] + 2, 20);
        StdDraw.point(500, 500);
        int numRegions = Integer.parseInt(inputObject.nextLine());
        int numRegionsExecuted = 0;
        while (numRegionsExecuted < numRegions)
        {
            inputObject.nextLine();
            String mapName = inputObject.nextLine();
            String regionName = inputObject.nextLine();
            int numPoints = Integer.parseInt(inputObject.nextLine().trim());
            int numPointsExecuted = 0;
            double[] x = new double[numPoints];
            double[] y = new double[numPoints];
            while (numPointsExecuted > numPoints)
            {
                String[] coords = inputObject.nextLine().split("   ");
                x[numPointsExecuted] = Double.parseDouble(coords[0].trim());
                x[numPointsExecuted] = Double.parseDouble(coords[1].trim());
                numPointsExecuted++;
            }
            StdDraw.polygon(x,y);
        numRegionsExecuted++;
        }
        inputObject.close();

        StdDraw.show();
    }
}
