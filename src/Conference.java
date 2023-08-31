import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * To model a Conference - a collection of talks
 *
 * @author Chris Loftus, Faisal Rezwan and George Deering
 * @version 2.1 31/03/2022
 */
public class Conference {
    private String name;
    private ArrayList<Event> events;
    private ArrayList<Venue> venues;

    /**
     * Creates a conference
     */
    public Conference() {
        events = new ArrayList<>();
        venues = new ArrayList<>();
    }

    /**
     * This method gets the value for the name attribute. The purpose of the
     * attribute is: The name of the Conference e.g. "QCon London 2019"
     *
     * @return String The name of the conference
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the value for the name attribute. The purpose of the
     * attribute is: The name of the conference e.g. "QCon London 2019"
     *
     * @param name The name of the conference
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Enables a user to add an event to the Conference
     *
     * @param event A new event
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Add a new venue to the conference
     *
     * @param venue Must be a unique name
     * @return true if venue successfully added or false if the venue already exists
     */
    public boolean addVenue(Venue venue) {
        boolean success = false;
        if (!venues.contains(venue)) {
            venues.add(venue);
            success = true;
        }
        return success;
    }

    /**
     * Enables a user to delete an Event from the Conference.
     *
     * @param eventName The talk to remove
     */
    public boolean removeEvent(String eventName) {
        // Search the event by name
        Event which = null;
        for (Event event : events) {
            if (eventName.equals(event.getName())) {
                which = event;
                break;
            }
        }
        if (which != null) {
            events.remove(which); // Requires that Event has an equals method
            System.out.println("removed " + which);
            return true;
        } else {
            System.err.println("cannot remove " + eventName +
                    " - not in conference " + name);
            return false;
        }
    }

    /**
     * Returns an array of the events in the conference
     *
     * @return An array of the correct size
     */
    public Event[] obtainAllEvents() {
        // ENTER CODE HERE: (POSSIBLY CHANGE SOME, YOU MAY CHANGE THE SIGNATURE TO DEAL
        // WITH ALL KINDS OF EVENTS: TALKS AND SOCIALS)
        // SEE Talk.getSpeakers METHOD FOR SIMILAR CODE
        // YOU MUST IMPLEMENT THIS METHOD, EVEN IF IT IS NOT CURRENTLY USED: YOU WILL NEED TO TEST IT
        // BY ADDING CODE TO ConferenceApp
        Event[] result = new Event[events.size()];
        result = events.toArray(result);
        return result;
    }

    /**
     * Searches for and returns the event, if found
     *
     * @param name The name of the event
     * @return The event or else null if not found
     */
    public Event searchForEvent(String name) {
        // ENTER CODE HERE: (POSSIBLY CHANGE SOME, YOU MAY CHANGE THE SIGNATURE TO DEAL
        // WITH ALL KINDS OF EVENTS: TALKS AND SOCIALS)
        Event result = null;
        int loop = events.size();
        for (int i = 0; i < loop; i++) {
            if (events.get(i).getName() == name) {
                result = events.get(i);
                break;
            } else {
                result = null;
            }
        }
        return result;
    }

    /**
     * Searches for and returns the venue, if found
     *
     * @param name The name of the venue
     * @return The venue or else null if not found
     */
    public Venue searchForVenue(String name) {
        Venue result = null;
        int loop = venues.size();
        for (int i = 0; i < loop; i++) {
            if (venues.get(i).getName() == name) {
                result = venues.get(i);
                break;
            } else {
                result = null;
            }
        }
        return result;
    }

    /**
     * toString() uses StringBuilder to return all information about the Conference
     * @return String showing all the information in the kennel
     */
    public String toString() {
        // ENTER CODE HERE: CHANGE TO USE StringBuilder TO MAKE MORE EFFICIENT
        StringBuilder sb = new StringBuilder("");
        sb.append("Data in conference ");
        sb.append(name);
        sb.append(" is: \n");
        int eventsLoop = events.size();
        for (int i = 0; i < eventsLoop; i++) {
            sb.append(events.get(i));
            sb.append("\n");
        }
        int venuesLoop = venues.size();
        for (int i = 0; i < venuesLoop; i++) {
            sb.append(venues.get(i));
            sb.append("\n");
        }
        String str = sb.toString();
        return str;
    }

    /**
     * Reads in Conference information from the file
     *
     * @param filename The file to read from
     * @throws IOException
     * @throws FileNotFoundException
     * @throws IllegalArgumentException if outfileName is null or empty
     */
    public void load(String filename) throws IOException {
        // Using try-with-resource. We will cover this in Seminar 8 and workshop 17, but
        // what it does is to automatically close the file after the try / catch ends.
        // This means we don't have to worry about closing the file.

        // ENTER CODE HERE: YOU WILL NEED TO UPDATE THIS METHOD 
        // TO DEAL WITH DIFFERENT KINDS OF EVENTS
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        Scanner infile = new Scanner(br).useDelimiter("\n");

        events.clear();
        venues.clear();

        // Use the delimiter pattern so that we don't have to clear end of line
        // characters after doing a infile.nextInt or infile.nextBoolean and can use infile.next
        //infile.useDelimiter("\n"); // End of line characters on Unix or DOS

        name = infile.next();

        while (infile.hasNext()) {
            Event event = new Event();
            event.load(infile);

            // Read the venue data
            String venueName = infile.next();
            boolean hasDataProjector = infile.nextBoolean();
            Venue theVenue = searchForVenue(venueName);
            if (theVenue == null) {
                theVenue = new Venue(venueName);
                theVenue.setHasDataProjector(hasDataProjector);
                venues.add(theVenue);
            }
            event.setVenue(theVenue);

            events.add(event);

        }
        br.close();
    }

    /**
     * Write out Conference information to the outfile
     *
     * @param outfileName The file to write to
     * @throws IOException
     * @throws IllegalArgumentException if outfileName is null or empty
     */
    public void save(String outfileName) throws IOException {
        // Again using try-with-resource 
        // so that I don't need to close the file explicitly

        try (FileWriter fw = new FileWriter(outfileName);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw);) {

            outfile.print(name);
            for (Event event : events) {
                outfile.println();
                event.save(outfile);

                Venue venue = event.getVenue();
                outfile.println(venue.getName());

                // Print rather than println so that we don't leave a blank line at the end
                outfile.print(venue.hasDataProjector());
            }
        }
    }
}
