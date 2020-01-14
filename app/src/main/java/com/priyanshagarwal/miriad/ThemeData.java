package com.priyanshagarwal.miriad;

public class ThemeData {
    private String ThemeName;
    private String imageURI;
    private String exitMessage;

    public String getExitSound() {
        return exitSound;
    }

    public String getStuckSound() {
        return stuckSound;
    }

    public String getCorrectSound() {
        return correctSound;
    }

    public String getWrongSound() {
        return wrongSound;
    }

    private String exitSound;
    private String positiveMessage;

    public String getPositiveMessage() {
        return positiveMessage;
    }

    public String getNegativeMessage() {
        return negativeMessage;
    }

    private String negativeMessage;
    private String stuckSound;
    private String correctSound;
    private String wrongSound;

    public String getExitMessage() {
        return exitMessage;
    }
    public String getThemeName() {
        return ThemeName;
    }

    public String getImageURI() {
        return imageURI;
    }
}
