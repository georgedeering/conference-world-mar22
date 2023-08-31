import java.util.Objects;

/**
 * The Speaker class is the blueprint for all speakers at events.
 *
 * @author George Deering
 * @version 1.0 31/03/2022
 */

public class Speaker {
    private String name;
    private String phone;

    /**
     * Speaker(String name) is the constructor to create a Speaker with just a name
     *
     * @param name the speaker's name
     */
    public Speaker(String name) {
        this.name = name;
    }

    /**
     * Speaker(String name, String phone) is the constructor to create a Speaker with a name and phone number
     *
     * @param name  the speaker's name
     * @param phone the speaker's phone number
     */
    public Speaker(String name, String phone) {
        this(name);
        this.phone = phone;

    }

    /**
     * getName() gets the speaker's name
     *
     * @return String the speaker's name
     */
    public String getName() {
        return name;
    }

    /**
     * setName(String name) sets the name of a Speaker
     *
     * @param name the Speaker's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getPhone() gets the phone number of a Speaker
     *
     * @return phone number of a Speaker
     */
    public String getPhone() {
        return phone;
    }

    /**
     * setPhone(String phone) sets the phone number of a Speaker
     *
     * @param phone the Speaker's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
        Speaker speaker = (Speaker) o;
        return Objects.equals(name, speaker.name) &&
                Objects.equals(phone, speaker.phone);
    }

    /**
     * toString() uses StringBuilder to return the Speaker's information
     *
     * @return String the Speaker's information
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Speaker's name= ");
        sb.append(name);
        sb.append(", Speaker's phone number= ");
        sb.append(phone);
        sb.append(".");
        String str = sb.toString();
        return str;
    }
}
