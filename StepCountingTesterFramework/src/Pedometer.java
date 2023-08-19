import java.util.ArrayList;

public class Pedometer implements StepCounter {
    public static ArrayList<Double> getPeakValuesFromIndexes(ArrayList<Integer> peakIndexes, ArrayList<Double> mags) {
        ArrayList<Double> peaks = new ArrayList<>();
        for (int i = 0; i < peakIndexes.size(); i++) {
            double val = mags.get(peakIndexes.get(i));
            peaks.add(val);
        }
        return peaks;
    }

    public static ArrayList<Integer> getPeakIndexes(ArrayList<Double> mags) {
        ArrayList<Integer> peakIndexes = new ArrayList<>();

        for (int i = 1; i < mags.size() - 1; i++) {
            if (mags.get(i - 1) < mags.get(i) && mags.get(i) > mags.get(i + 1)) {
                peakIndexes.add(i);
            }
        }
        return peakIndexes;
    }
    public static ArrayList<Double> createListOfPeaks(ArrayList<Double> mags) {
        ArrayList<Double> peakList = new ArrayList<>();
        for (int i = 1; i < mags.size() - 1; i++) {
            if (mags.get(i) > mags.get(i - 1) && mags.get(i) > mags.get(i + 1)) {
                peakList.add(mags.get(i));
            }
        }
        return peakList;
    }

    public static ArrayList<Double> createListOf2Peaks(ArrayList<Double> mags1, ArrayList<Double> mags2) {
        ArrayList<Double> peakList = new ArrayList<>();
        for (int i = 1; i < mags1.size() - 1; i++) {
            if (mags1.get(i) > mags1.get(i - 1) && mags1.get(i) > mags1.get(i + 1)
            && mags2.get(i) > mags2.get(i - 1) && mags2.get(i) > mags2.get(i + 1)) {
                peakList.add(mags1.get(i));
            }
        }
        return peakList;
    }

    public static int peaksOfPeaks(ArrayList<Double> mags) {
        int peakCounter = 0;
        for (int i = 1; i < mags.size() - 1; i++) {
            if (mags.get(i) > mags.get(i - 1) && mags.get(i) > mags.get(i + 1)) {
                peakCounter++;
            }
        }
        return peakCounter;
    }

    public double findMax(ArrayList<Double> peaksList) {
        double max = -1000;

        if (peaksList.size() == 0)
            return 0;
        for (int i = 0; i < peaksList.size();i++ ){
            if (peaksList.get(i) > max)
                max = peaksList.get(i);
        }
        return max;
    }
    public static double findThreshold(ArrayList<Double> peaksList) {
        double sum = 0;

        if (peaksList.size() == 0)
            return 0;
        for (int i = 0; i < peaksList.size();i++ ){

            sum += peaksList.get(i);
        }
        Double threshold = (sum / peaksList.size());
        return threshold;
    }


    public static ArrayList<Double> listOfValsAboveThres(ArrayList<Double> peaksList, Double threshold) {
        //int peakCounter = 0;
        ArrayList<Double> outputList = new ArrayList<>();
        for (int i = 1; i < peaksList.size(); i++) {
            if (peaksList.get(i) > threshold) {
               // peakCounter++;
                outputList.add(peaksList.get(i));
            }
        }
        return outputList;
    }

    public static ArrayList<Double> findMagnitudes(ArrayList<Double> accX, ArrayList<Double> accY, ArrayList<Double> accZ) {
        ArrayList<Double> outputList = new ArrayList<>();
        if (accX.size() != accY.size() || accX.size() != accZ.size() || accY.size() != accZ.size()) {
            System.out.println("Incorrect sizes lists");
            return outputList;
        }
        for (int i = 0; i < accX.size(); i++) {
            double magnitude = Math.sqrt(Math.pow(accX.get(i),2) + Math.pow(accY.get(i),2) + Math.pow(accZ.get(i),2));
            outputList.add(magnitude);
        }
        return outputList;
    }

    public static ArrayList<Double> createColList(String[] line, int col) {
        ArrayList<Double> outputList = new ArrayList<>();
        for (int i = 1; i < line.length; i++) {
            String[] vals = line[i].split(",");
            double val = Double.parseDouble(vals[col].trim());
            outputList.add(val);
        }
        return outputList;
    }


    public int countSteps(ArrayList<Double> xAcc, ArrayList<Double> yAcc, ArrayList<Double> zAcc,
                          ArrayList<Double> xGyro, ArrayList<Double> yGyro, ArrayList<Double> zGyro) {
        ArrayList<Double> magList, magList2;
        ArrayList<Double> peaks;
        Double threshold, max;
        int stepCount;

        magList = findMagnitudes(xAcc, yAcc, zAcc);
      //  magList = findMagnitudes(xGyro, yGyro, zGyro);
        threshold = findThreshold(magList);
      //  max = findMax(magList);
        magList = listOfValsAboveThres(magList, (threshold));

      //  magList2 = findMagnitudes(xGyro, yGyro, zGyro);
      //  threshold = findThreshold(magList2);
      //  magList2 = listOfValsAboveThres(magList2, threshold);

        peaks = createListOfPeaks(magList);
       // peaks = createListOf2Peaks(magList, magList2);
        threshold = findThreshold(peaks);


      //  stepCount= peaks.size(); //Idea # 1 return all peaks
      // stepCount = listOfValsAboveThres(peaks,0.8*threshold).size(); // Idea # count peaks above a threshold. Threshold is avg of peaks in this case
        stepCount = createListOfPeaks(peaks).size(); // Idea #3 find peaks of the peaks
        return stepCount;
    }

    public static ArrayList <Double> plotPeaks (ArrayList <Double> magList){
        double threshold;
        ArrayList<Double> peaks;
         threshold = findThreshold(magList);
        magList = listOfValsAboveThres(magList, (threshold));
        peaks = createListOfPeaks(magList);
        return peaks;
    }

    public int countSteps(String fileData) {
        String[] line = fileData.split("\n");
        ArrayList<Double> accX = createColList(line, 0);
        ArrayList<Double> accY = createColList(line, 1);
        ArrayList<Double> accZ = createColList(line, 2);
        ArrayList<Double> gyroX = createColList(line, 3);
        ArrayList<Double> gyroY = createColList(line, 4);
        ArrayList<Double> gyroZ = createColList(line, 5);
        return countSteps(accX, accY, accZ, gyroX, gyroY, gyroZ);
    }

}

