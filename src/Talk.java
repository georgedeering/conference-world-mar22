import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * The Talk class is the blueprint for all talks. Inherits from the Event class and adds Speakers.
 *
 * @author George Deering
 * @version 1.1 31/03/2022
 */

public class Talk extends Event {
    private ArrayList<Speaker> speakers = new ArrayList<>();

    /**
     * setSpeakers(List<Speaker> speakers) sets the speakers for a talk
     *
     * @param speakers a list of speakers
     */
    public final void setSpeakers(List<Speaker> speakers) {
        // We make a true copy of the speakers ArrayList to make sure that we
        // don't break encapsulation: i.e. don't share object references with
        // other code
        if (speakers == null) {
            throw new IllegalArgumentException("speakers must not be null");
        }
        this.speakers.clear();
        for (Speaker s : speakers) {
            Speaker copy = new Speaker(s.getName(), s.getPhone());
            this.speakers.add(copy);
        }
    }

    /**
     * Returns a copy of the speakers
     *
     * @return A copy of the speakers as an array
     */
    public Speaker[] getSpeakers() {
        Speaker[] result = new Speaker[speakers.size()];
        result = speakers.toArray(result);
        return result;
    }

    /**
     * toString() uses StringBuilder to output information about a talk
     *
     * @return String information about a talk
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("startDateTime= ");
        sb.append(dateTimeToString(startDateTime));
        sb.append(", endDateTime= ");
        sb.append(dateTimeToString(endDateTime));
        sb.append(", name= ");
        sb.append(name);
        sb.append(", venue= ");
        sb.append(venue);
        sb.append(", dateProjectorRequired= ");
        sb.append(dataProjectorRequired);
        sb.append(", speakers= ");
        for (Speaker speaker : speakers) {
            sb.append(speaker);
        }
        String str = sb.toString();
        return str;
    }

    /**
     * load(Scanner infile) loads information about the talks from the file into the variables
     *
     * @param infile An open file
     */
    public void load(Scanner infile) {
        if (infile == null) {
            throw new IllegalArgumentException("infile must not be null");
        }
        name = infile.next();
        startDateTime = readDateTime(infile);
        endDateTime = readDateTime(infile);

        dataProjectorRequired = infile.nextBoolean();

        int numSpeakers = infile.nextInt();
        Speaker speaker = null;
        speakers.clear();
        for (int i = 0; i < numSpeakers; i++) {
            String speakerName = infile.next();
            String phone = infile.next();
            speaker = new Speaker(speakerName, phone);
            speakers.add(speaker);
        }
    }

    /**
     * save(PrintWriter outfile) saves information about the talks from the variables to the file
     *
     * @param outfile An open file
     */
    public void save(PrintWriter outfile) {
        if (outfile == null)
            throw new IllegalArgumentException("outfile must not be null");
        outfile.println(name);
        writeDateTime(outfile, startDateTime);
        writeDateTime(outfile, endDateTime);

        outfile.println(dataProjectorRequired);

        outfile.println(speakers.size());
        for (Speaker speaker : speakers) {
            outfile.println(speaker.getName());
            outfile.println(speaker.getPhone());
        }
    }
}
