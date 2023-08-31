/**
 * The Social class is the blueprint for all social events.
 * Inherits from the Event class and adds isFoodOrDrink and isInvitationOnly.
 *
 * @author George Deering
 * @version 1.1 31/03/2022
 */

public class Social extends Event {
    private boolean isFoodOrDrink;
    private boolean isInvitationOnly;

    /**
     * isFoodOrDrink() determines whether food or drink is required at a social event
     *
     * @return isFoodOrDrink boolean
     */
    public boolean isFoodOrDrink() {
        return isFoodOrDrink;
    }

    /**
     * setFoodOrDrink(boolean foodOrDrink) sets whether food or drink is required at a social event
     *
     * @param foodOrDrink boolean if food or drink is required
     */
    public void setFoodOrDrink(boolean foodOrDrink) {
        isFoodOrDrink = foodOrDrink;
    }

    /**
     * isInvitationOnly() determines if the social event is invitation only
     *
     * @return isInvitationOnly boolean whether event is invitation only
     */
    public boolean isInvitationOnly() {
        return isInvitationOnly;
    }

    /**
     * setInvitationOnly(boolean invitationOnly) sets whether a social event is invitation only
     *
     * @param invitationOnly boolean if a social event is invitation only
     */
    public void setInvitationOnly(boolean invitationOnly) {
        isInvitationOnly = invitationOnly;
    }

    /**
     * toString() uses StringBuilder to return all information about a Social event
     * @return String with all information about a social event
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        sb.append("Social event name= ");
        sb.append(name);
        sb.append(", Venue= ");
        sb.append(venue);
        sb.append(", Start date / time= ");
        sb.append(startDateTime);
        sb.append(", End date / time= ");
        sb.append(endDateTime);
        sb.append(", Data projector required?= ");
        sb.append(dataProjectorRequired);
        sb.append(", Food or drink required?= ");
        sb.append(isFoodOrDrink);
        sb.append(", Invitation required?= ");
        sb.append(isInvitationOnly);
        sb.append(".");
        String str = sb.toString();
        return str;
    }
}
