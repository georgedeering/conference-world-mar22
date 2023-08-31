import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * The main application class for the Conference. Has a command line menu.
 *
 * @author Chris Loftus, Faisal Rezwan and George Deering
 * @version 2.1 31/03/2022
 */

public class ConferenceApp {

    private String filename;
    private Scanner scan;
    private Conference conference;

    /*
     * Notice how we can make this private, since we only call from main which
     * is in this class. We don't want this class to be used by any other class.
     */

    /**
     * ConferenceApp() is the constructor and asks for the filename of the conference.
     */
    private ConferenceApp() {
        scan = new Scanner(System.in);
        System.out.print("Please enter the filename of conference information: ");
        filename = scan.next();

        conference = new Conference();
    }

    /*
     * initialise() method runs from the main and reads from a file
     */

    /**
     * initialise() loads the conference from the file if it is found.
     */
    private void initialise() {
        System.out.println("Using file " + filename);

        try {
            conference.load(filename);
        } catch (FileNotFoundException e) {
            System.err.println("The file: " + " does not exist. Assuming first use and an empty file." +
                    " If this is not the first use then have you accidentally deleted the file?");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file " + filename);
            System.err.println(e.getMessage());
        }
    }

    /*
     * runMenu() method runs from the main and allows entry of data etc
     */

    // ENTER CODE HERE: TO MAKE THE MENU MORE GENERALISED
    // FOR SUPPORTING BOTH TALKS AND SOCIAL EVENTS

    /**
     * runMenu() runs form the main and displays options to the user for them to select.
     */
    private void runMenu() {
        String response;
        do {
            printMenu();
            System.out.println("What would you like to do:");
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    addEvent();
                    break;
                case "2":
                    searchForEvent();
                    break;
                case "3":
                    removeEvent();
                    break;
                case "4":
                    addVenue();
                    break;
                case "5":
                    changeConferenceName();
                    break;
                case "6":
                    printAll();
                    break;
                case "7":
                    System.out.println(Arrays.toString(conference.obtainAllEvents()));
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Invalid input, try again");
            }
        } while (!(response.equals("Q")));
    }

    /**
     * printMenu() prints out the menu for the user to view.
     */
    private void printMenu() {
        System.out.println("1 -  Add a new event");
        System.out.println("2 -  Search for an event");
        System.out.println("3 -  Remove an event");
        System.out.println("4 -  Add a venue");
        System.out.println("5 -  Change conference name ");
        System.out.println("6 -  Display everything");
        System.out.println("7 -  Display all events");
        System.out.println("Q -  Quit");
    }

    /**
     * addEvent() creates a new talk or social event from the Talk or Social classes, inheriting from Event.
     */
    private void addEvent() {
        System.out.println("1 -  Add a new talk");
        System.out.println("2 -  Add a new social event");
        System.out.println("Q -  Back to main menu");
        String response = scan.nextLine().toUpperCase();
        switch (response) {
            case "1":
                System.out.println("You are creating a new talk");
                System.out.println("Get speakers: ");
                ArrayList<Speaker> speakers = getSpeakers();

                Talk talk = new Talk();
                talk.setSpeakers(speakers);

                populateAndAddToConference(talk);
                break;
            case "2":
                System.out.println("You are creating a new social event");
                Social social = new Social();
                System.out.println("Is food or drink required? (Y/N): ");
                String foodOrDrinkResponse = scan.nextLine().toUpperCase();
                switch (foodOrDrinkResponse) {
                    case "Y":
                        social.setFoodOrDrink(true);
                        break;
                    case "N":
                        social.setFoodOrDrink(false);
                        break;
                    default:
                        System.out.println("Invalid input, try again");
                }
                System.out.println("Is it invitation only? (Y/N): ");
                String inviteOnlyResponse = scan.nextLine().toUpperCase();
                switch (inviteOnlyResponse) {
                    case "Y":
                        social.setInvitationOnly(true);
                        break;
                    case "N":
                        social.setInvitationOnly(false);
                        break;
                    default:
                        System.out.println("Invalid input, try again");
                }
                populateAndAddToConference(social);
                break;
            case "Q":
                break;
            default:
                System.out.println("Invalid input, try again");
        }
    }

    /*
     * Adds event general data. This is common to all events. Then
     * adds to the conference.
     */

    /**
     * populateAndAddToConference() defines all attributes for new events.
     *
     * @param event The Event that is being created
     */
    private void populateAndAddToConference(Event event) {
        System.out.println("Event name: ");
        String name = scan.nextLine();

        System.out.println("Enter start time for event");
        Calendar startDateTime = getDateTime();
        System.out.println("Enter end time for event");
        Calendar endDateTime = getDateTime();

        System.out.println("Is a data projector required?(Y/N)");
        String answer = scan.nextLine().toUpperCase();
        boolean projectorRequired = true;
        if (answer.equals("N")) {
            projectorRequired = false;
        }

        Venue venue = new Venue();
        do {
            System.out.println("Enter venue name");
            String venueName = scan.nextLine();
            //venue.setName(venueName);
            answer = "N";

            venue = conference.searchForVenue(venueName);
            if (venue != null) {
                if (projectorRequired && !venue.hasDataProjector()) {
                    System.out.println("Selected venue does not have a data projector. Choose a different venue");
                    answer = "Y";
                } else {
                    event.setName(name);
                    event.setDataProjectorRequired(projectorRequired);
                    event.setStartDateTime(startDateTime);
                    event.setEndDateTime(endDateTime);
                    event.setVenue(venue);

                    conference.addEvent(event);
                }
            } else {
                System.out.println("Venue " + venue + " does not exist. Try a different venue? (Y/N)");
                answer = scan.nextLine().toUpperCase();
            }
        } while (answer.equals("Y"));
    }

    /**
     * getDateTime() gets the date and time from a Calendar
     *
     * @return String: the date and time
     */
    private Calendar getDateTime() {
        Calendar result = Calendar.getInstance();
        System.out.println("On one line (numbers): year month day hour minutes");

        // Note that an ArrayIndexOutOfBoundsException is thrown if an
        // illegal value is entered. For simplicity, we will pretend that won't happen.

        int year = scan.nextInt();
        // Note that months start from 0 so we have to subtract 1
        // when reading and then add 1 when displaying the result
        int month = scan.nextInt() - 1;
        int day = scan.nextInt();
        int hour = scan.nextInt();
        int minutes = scan.nextInt();
        scan.nextLine(); // Clear the end of line character

        result.clear();
        result.set(year, month, day, hour, minutes);

        System.out.println("The date/time you entered was: " +
                result.get(Calendar.YEAR) + "/" +
                (result.get(Calendar.MONTH) + 1) + "/" +
                result.get(Calendar.DAY_OF_MONTH) + ":" +
                result.get(Calendar.HOUR_OF_DAY) + ":" +
                result.get(Calendar.MINUTE));
        return result;
    }

    /**
     * getSpeakers() gets all the speakers as input and adds them to an event
     *
     * @return ArrayList<Speaker> speakers: an arraylist of all the speakers at that particular event
     */
    private ArrayList<Speaker> getSpeakers() {
        ArrayList<Speaker> speakers = new ArrayList<>();
        String answer;
        do {
            System.out.println("Enter on separate lines: speaker-name speaker-phone");
            String speakerName = scan.nextLine();
            String speakerPhone = scan.nextLine();
            Speaker speaker = new Speaker(speakerName, speakerPhone);
            speakers.add(speaker);
            System.out.println("Another owner (Y/N)?");
            answer = scan.nextLine().toUpperCase();
        } while (!answer.equals("N"));
        return speakers;
    }

    /**
     * changeConferenceName() changes the conference name
     */
    private void changeConferenceName() {
        String name = scan.nextLine();
        conference.setName(name);
    }

    /**
     * searchForEvent() searches for the event and outputs it if found, or else if not found
     */
    private void searchForEvent() {
        System.out.println("Which event do you want to search for");
        String name = scan.nextLine();
        Event event = conference.searchForEvent(name);
        if (event != null) {
            System.out.println(event);
        } else {
            System.out.println("Could not find event: " + name);
        }
    }

    /**
     * removeEvent() deletes a specified event
     */
    private void removeEvent() {
        System.out.println("Which event do you want to remove");
        String eventToBeRemoved;
        eventToBeRemoved = scan.nextLine();
        conference.removeEvent(eventToBeRemoved);
    }

    /**
     * addVenue() creates a new venue
     */
    private void addVenue() {
        Venue venue;
        String venueName;
        boolean tryAgain;
        do {
            tryAgain = false;
            System.out.println("Enter the venue name");
            venueName = scan.nextLine();
            venue = conference.searchForVenue(venueName);
            if (venue != null) {
                System.out.println("This venue already exists. Give it a different name");
                tryAgain = true;
            }
        } while (tryAgain);

        System.out.println("Does it have a data projector?(Y/N)");
        String answer = scan.nextLine().toUpperCase();
        boolean hasDataProjector = answer.equals("Y");

        venue = new Venue(venueName);
        venue.setHasDataProjector(hasDataProjector);

        conference.addVenue(venue);
    }

    /**
     * printAll() prints all information about the conference, including events, venues and speakers.
     */
    private void printAll() {
        // ENTER CODE HERE:THIS IS NOT SORTED. 
        // YOU WILL NEED TO UPDATE THIS TO DISPLAY SORTED EVENTS
        StringBuilder sb = new StringBuilder("");
        sb.append("Conference name: ");
        sb.append(conference.getName());
        sb.append("\n");
        sb.append("Events: \n");
        Event Events[] = conference.obtainAllEvents();
        int loop = Events.length;
        for (int i = 0; i < loop; i++) {
            sb.append(Events[i].toString());
        }
        String str = sb.toString();
        System.out.println(str);
    }

    /*
     * save() method runs from the main and writes back to file
     */

    /**
     * save() saves all the information about the conference to the file.
     */
    private void save() {
        try {
            conference.save(filename);
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file: " + filename);
        }

    }

    // /////////////////////////////////////////////////

    /**
     * The main method where the program begins.
     *
     * @param args
     */
    public static void main(String args[]) {
        System.out.println("**********HELLO***********");

        ConferenceApp app = new ConferenceApp();
        app.initialise();
        app.runMenu();
        app.printAll();
        // MAKE A BACKUP COPY OF conf.txt JUST IN CASE YOU CORRUPT IT
        app.save();

        System.out.println("***********GOODBYE**********");
    }


}
