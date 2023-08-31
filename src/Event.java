import java.io.PrintWriter;
import java.util.*;

/**
 * The Event class where Talk and Social inherit from.
 * Contains variables and methods that all events will use.
 *
 * @author George Deering
 * @version 2.1 31/03/2022
 */
public class Event {
    Calendar startDateTime;
    Calendar endDateTime;
    String name;
    Venue venue;
    boolean dataProjectorRequired;

    public Event() {
    }

    /**
     * Constructor for Event
     *
     * @param name          The event title
     * @param startDateTime When it starts
     * @param endDateTime   When it ends
     */
    public Event(String name, Calendar startDateTime, Calendar endDateTime) {
        this.name = name;
        // We should really check that the start time is before the end time
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * getName() gets the name of the event
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * setName(String name) sets the name of the event
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the venue. This will only be allowed if the meets the data projector requirement.
     * Otherwise displays an error message. This should really throw an exception
     *
     * @param venue The venue for the event
     */
    public void setVenue(Venue venue) {
        // Only allow this if the venue spec matches the
        // the event requirement
        if (dataProjectorRequired && !venue.hasDataProjector()) {
            System.err.println("Event " + name + " requires a data projector. " +
                    "Venue " + venue.getName() + " does not have one");
        } else {
            this.venue = venue;
        }
    }

    /**
     * getVenue() gets the venue
     *
     * @return Venue venue
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * isDataProjectorRequired() returns if a data projector is required
     *
     * @return boolean dataProjectorRequired
     */
    public boolean isDataProjectorRequired() {
        return dataProjectorRequired;
    }

    /**
     * Sets the data projector requirement.
     * This will only be allowed if there is an associated venue that meets the requirement.
     * Otherwise displays an error message. This should really throw an exception
     *
     * @param dataProjectorRequired Whether required or not
     */
    public void setDataProjectorRequired(boolean dataProjectorRequired) {
        if (venue != null && (dataProjectorRequired && !venue.hasDataProjector())) {
            System.err.println("Event " + name + " currently has a venue " +
                    venue.getName() + " that does not have a data projector. Change the venue first");
        } else {
            this.dataProjectorRequired = dataProjectorRequired;
        }
    }

    /**
     * getStartDateTime() gets the start date and time of the event
     *
     * @return Calendar startDateTime
     */
    public Calendar getStartDateTime() {
        return startDateTime;
    }

    /**
     * setStartDateTime(Calendar startDateTime) sets the start date and time of the event
     *
     * @param startDateTime the start date and time of the event
     */
    public void setStartDateTime(Calendar startDateTime) {
        // We should really check that the start time is before the end time
        this.startDateTime = startDateTime;
    }

    /**
     * getEndDateTime() gets the end date and time of the event
     *
     * @return Calendar endDateTime the end date and time of the event
     */
    public Calendar getEndDateTime() {
        return endDateTime;
    }

    /**
     * setEndDateTime(Calendar endDateTime) sets the end date and time of the event
     *
     * @param endDateTime the end date and time of the event
     */
    public void setEndDateTime(Calendar endDateTime) {
        // We should really check that the end time is after the start time
        this.endDateTime = endDateTime;
    }

    /**
     * toString() uses StringBuilder to create a string of an event
     *
     * @return All the string data for the event
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
        String str = sb.toString();
        return str;
    }

    /**
     * dateTimeToString(Calendar dateTime) uses StringBuilder
     * To turn a Calendar into a string of date and time
     *
     * @param dateTime Calendar to be converted to string by StringBuilder
     * @return date and time as a string
     */
    String dateTimeToString(Calendar dateTime) {
        int year = dateTime.get(Calendar.YEAR);
        int month = dateTime.get(Calendar.MONTH) + 1; // We have to add 1 since months start from 0
        int day = dateTime.get(Calendar.DAY_OF_MONTH);
        int hour = dateTime.get(Calendar.HOUR_OF_DAY);
        int minutes = dateTime.get(Calendar.MINUTE);
        StringBuilder sb = new StringBuilder("");
        sb.append(year);
        sb.append(":");
        sb.append(month);
        sb.append(":");
        sb.append(day);
        sb.append(":");
        sb.append(hour);
        sb.append(":");
        sb.append(minutes);
        String str = sb.toString();
        return str;
    }

    /**
     * Reads in Event specific information from the file
     *
     * @param infile An open file
     * @throws IllegalArgumentException if infile is null
     */
    public void load(Scanner infile) {
        if (infile == null) {
            throw new IllegalArgumentException("infile must not be null");
        }
        name = infile.next();
        startDateTime = readDateTime(infile);
        endDateTime = readDateTime(infile);

        dataProjectorRequired = infile.nextBoolean();
    }

    /**
     * readDateTime(Scanner scan) takes the date and time from a Scanner input
     *
     * @param scan Scanner input of date and time
     * @return Calendar output of date and time
     */
    Calendar readDateTime(Scanner scan) {
        Calendar result = Calendar.getInstance();

        int year = scan.nextInt();
        int month = scan.nextInt();
        int day = scan.nextInt();
        int hour = scan.nextInt();
        int minutes = scan.nextInt();

        result.clear();
        result.set(year, month, day, hour, minutes);

        return result;
    }

    /**
     * Writes out information about the Event to the file
     *
     * @param outfile An open file
     * @throws IllegalArgumentException if outfile is null
     */
    public void save(PrintWriter outfile) {
        if (outfile == null)
            throw new IllegalArgumentException("outfile must not be null");
        outfile.println(name);
        writeDateTime(outfile, startDateTime);
        writeDateTime(outfile, endDateTime);

        outfile.println(dataProjectorRequired);
    }

    /**
     * writeDateTime(PrintWriter outfile, Calendar dateTime) writes the date and time
     * To the conference file
     *
     * @param outfile  PrintWriter to be output to file
     * @param dateTime Calendar date and time
     */
    void writeDateTime(PrintWriter outfile, Calendar dateTime) {
        outfile.println(dateTime.get(Calendar.YEAR));
        outfile.println(dateTime.get(Calendar.MONTH));
        outfile.println(dateTime.get(Calendar.DAY_OF_MONTH));
        outfile.println(dateTime.get(Calendar.HOUR_OF_DAY));
        outfile.println(dateTime.get(Calendar.MINUTE));
    }

    /**
     * Note that this only compares equality based on an event's name.
     *
     * @param o the other event to compare against.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Are they the same object?
        if (o == null || getClass() != o.getClass()) return false; // Are they the same class?
        Event event = (Event) o;  // Do the cast to Event
        // Now just check the names
        return Objects.equals(name, event.name); // Another way of checking equality. Also checks for nulls
    }

}
