package dev.terrarium.minefactoryrenewed.api.item;

public enum LauncherMode {
    CAPTURE("tooltip.safari_net_launcher.capture"),
    RELEASE("tooltip.safari_net_launcher.release");

    private final String langKey;

    LauncherMode(String langKey) {
        this.langKey = langKey;
    }

    public String getLangKey() {
        return langKey;
    }
}
