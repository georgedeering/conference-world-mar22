import java.util.Objects;

/**
 * The Venue class is the blueprint for all venues where events can be held.
 *
 * @author George Deering
 * @version 1.1 31/03/2022
 */

public class Venue {
    private String name;
    private boolean hasDataProjector;

    /**
     * Venue() constructor to create a Venue without parameters
     */
    public Venue() {
    }

    /**
     * Venue(String name) constructor to create a Venue with a name
     *
     * @param name name for the Venue
     */
    public Venue(String name) {
        this.name = name;
    }

    /**
     * getName() to get the name of the Venue
     *
     * @return String name of the venue
     */
    public String getName() {
        return name;
    }

    /**
     * setName(String name) sets the name of the Venue
     *
     * @param name the name of the Venue
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * hasDataProjector() gets whether the Venue has a data projector
     *
     * @return boolean hasDataProjector whether the Venue has a data projector
     */
    public boolean hasDataProjector() {
        return hasDataProjector;
    }

    /**
     * setHasDataProjector(boolean hasDataProjector) sets whether the Venue has a data projector
     *
     * @param hasDataProjector whether the Venue has a data projector
     */
    public void setHasDataProjector(boolean hasDataProjector) {
        this.hasDataProjector = hasDataProjector;
    }

    /**
     * equals(Object o)
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue location = (Venue) o;
        return Objects.equals(name, location.name);
    }

    /**
     * toString() uses StringBuilder to return a Venue's information
     *
     * @return String str to return a Venue's information
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Venue name= ");
        sb.append(name);
        sb.append(", Venue has data projector?= ");
        sb.append(hasDataProjector);
        sb.append(".");
        String str = sb.toString();
        return str;
    }

}
