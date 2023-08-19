import Plot.PlotWindow;
import Plot.ScatterPlot;

import java.nio.file.Path;
import java.util.ArrayList;


public class CreatePlot {
    private static final String TEST_FILE_FOLDER = "testFiles/blk3";

    public static void main(String[] args) {
        Pedometer counter = new Pedometer();  /* instantiate your step counter here */

        ArrayList<Path> paths = MainTester.getPaths(TEST_FILE_FOLDER);

        Path pathToPlot = paths.get(23);  // <-- file to plot

        System.out.println("Plotting data for: " + pathToPlot.toString());
        FileData data = MainTester.processPath(pathToPlot);

        int prediction = counter.countSteps(data.text);
        System.out.println("Your prediction: " + prediction + " steps.  Actual: " + data.correctNumberOfSteps + " steps");

        /* --------------- display plot ------------------------- */
        String[] lines = data.text.split("\n");

        ArrayList<Double> accX = counter.createColList(lines, 0);
        ArrayList<Double> accY = counter.createColList(lines, 1);
        ArrayList<Double> accZ = counter.createColList(lines, 2);
        ArrayList<Double> mags = counter.findMagnitudes(accX, accY, accZ);

        ArrayList<Double> peaks = counter.plotPeaks(mags);
        ArrayList<Integer> peakIndexes = counter.getPeakIndexes(peaks);
        ArrayList<Double> peakValues = counter.getPeakValuesFromIndexes(peakIndexes, peaks);

        ScatterPlot plt = new ScatterPlot(100,100,1100, 700);

        for (int i = 0; i < peaks.size(); i++) {
            plt.plot(0, i, peaks.get(i)).strokeColor("red").strokeWeight(2).style("-");
        }

        for (int i = 0; i < peakIndexes.size(); i++) {
            plt.plot(1, peakIndexes.get(i), peakValues.get(i)).strokeColor("blue").strokeWeight(5).style(".");
        }

        PlotWindow window = PlotWindow.getWindowFor(plt, 1200,800);
        window.show();
    }

}

