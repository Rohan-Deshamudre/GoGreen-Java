package gogreen.application.communication;

public class ChangeAchievements extends ClientMessage {

    private String achievements;

    /**
     * Constructor of the class.
     * @param loginData the login data.
     * @param achievements the achievements.
     */
    public ChangeAchievements(LoginData loginData, String achievements) {
        super(loginData);
        this.achievements = achievements;
    }

    /**
     * To String method.
     * @return String version of the object.
     */
    public String toString() {
        return "<ChangeAchievements[" + achievements + "]>";
    }
}
