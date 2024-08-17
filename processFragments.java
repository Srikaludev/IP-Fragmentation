import java.util.*;
import java.io.*;

class Fragment implements Comparable<Fragment> {
    //creating a struct equivalent for IP address,flag offset ,md and payload
    String ipAddress;
    int id;
    int fragOffset;
    int mf;
    int payloadLength;

    public Fragment(String ipAddress, int id, int fragOffset, int mf, int payloadLength) {
        //constructor for setting the instance variables to the class variables 
        this.ipAddress = ipAddress;
        this.id = id;
        this.fragOffset = fragOffset;
        this.mf = mf;
        this.payloadLength = payloadLength;
    }

    @Override
    public int compareTo(Fragment other) {
        int ipComparison = this.ipAddress.compareTo(other.ipAddress);
        if (ipComparison != 0)
            return ipComparison;
        else
            return this.id - other.id;
    }
}

public class processFragments {
    private static final int MAX_ROWS = 100;
    private static final int MAX_COLS = 5;

    public static void main(String[] args) {
        Fragment[] fragments = new Fragment[MAX_ROWS];
        int count = 0;
        //file input output operations from reading from input file
        try {
            Scanner fileScanner = new Scanner(new File("input.txt"));
            String[] header = new String[MAX_COLS];
            for (int i = 0; i < MAX_COLS; i++) {
                header[i] = fileScanner.next();
            }

            while (fileScanner.hasNext()) {
                String ipAddress = fileScanner.next();
                int id = fileScanner.nextInt();
                int fragOffset = fileScanner.nextInt();
                int mf = fileScanner.nextInt();
                int payloadLength = fileScanner.nextInt();

                fragments[count] = new Fragment(ipAddress, id, fragOffset, mf, payloadLength);
                count++;
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            //error handling--java feature
            System.err.println("Error opening file: " + e.getMessage());
            return;
        }
        Arrays.sort(fragments, 0, count);
        //writing the file output to output.txt 
        try {
            FileWriter writer = new FileWriter("output.txt");

            writer.write(String.format("%-15s %-10s %-15s%n", "IPsourceaddr", "ID", "Result"));
            processFragments(fragments, count, writer);

            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void processFragments(Fragment[] fragments, int count, FileWriter writer) throws IOException {
        //this is a class constructor for our main public class processFragments 
        //setting the parameter values to the instance variables
        String currentIp = "";
        int currentId = -1;
        int totalLength = 0;
        int expectedOffset = 0;
        int mfCount = 0;
        boolean mfFlag = false;
        boolean duplicateFlag = false;
        boolean missingFlag = false;
        boolean mismatchFlag = false;
        List<String> results = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            if (!fragments[i].ipAddress.equals(currentIp) || fragments[i].id != currentId) {
                if (currentId != -1) {
                    if (mfCount > 1)
                        results.add(String.format("%-15s %-10s %-15s", currentIp, currentId, "Duplicate fragment"));
                    else if (!mfFlag)
                        results.add(String.format("%-15s %-10s %-15s", currentIp, currentId, "Missing fragment"));
                    else if (mismatchFlag)
                        results.add(String.format("%-15s %-10s %-15s", currentIp, currentId, "Mismatch in offset"));
                    else
                        results.add(String.format("%-15s %-10s %-15s", currentIp, currentId, totalLength));
                }
                currentIp = fragments[i].ipAddress;
                currentId = fragments[i].id;
                totalLength = 0;
                expectedOffset = 0;
                mfCount = 0;
                mfFlag = false;
                duplicateFlag = false;
                missingFlag = false;
                mismatchFlag = false;
            }

            if (fragments[i].fragOffset != expectedOffset)
                mismatchFlag = true;

            if (fragments[i].mf == 0) {
                mfCount++;
                mfFlag = true;
            }

            if (mfCount > 1)
                duplicateFlag = true;

            totalLength += fragments[i].payloadLength;
            expectedOffset += fragments[i].payloadLength / 8;
        }

        if (currentId != -1) {
            if (mfCount > 1)
                results.add(String.format("%-15s %-10s %-15s", currentIp, currentId, "Duplicate fragment"));
            else if (!mfFlag)
                results.add(String.format("%-15s %-10s %-15s", currentIp, currentId, "Missing fragment"));
            else if (mismatchFlag)
                results.add(String.format("%-15s %-10s %-15s", currentIp, currentId, "Mismatch in offset"));
            else
                results.add(String.format("%-15s %-10s %-15s", currentIp, currentId, totalLength));
        }

        for (String result : results) {
            writer.write(result + "\n");
        }
    }
}
