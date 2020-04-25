package it.polimi.ingsw.view.client.cli;

public enum Color {

    ANSI_RED("\u001B[31m"), ANSI_GREEN("\u001B[32m"), ANSI_YELLOW("\u001B[33m"), ANSI_BLUE("\u001B[34m"), ANSI_BLACK("\u001B[30m"),
    ANSI_PURPLE("\u001B[35m"), ANSI_CYAN("\u001B[36m"), ANSI_WHITE("\u001B[37m"),

    BACKGROUND_RED("\u001B[41m"), BACKGROUND_GREEN("\u001B[42m"), BACKGROUND_YELLOW("\u001B[43m"), BACKGROUND_BLUE("\u001B[44m"),
    BACKGROUND_PURPLE("\u001B[45m"), BACKGROUND_WHITE("\u001B[47m"),

    CLEAR_LINE("\33[1A\33[2K"), CLEAR_CONSOLE("\033[H\033[2J");

    static final String RESET = "\u001B[0m";
    private String escape;

    Color(String escape) {
        this.escape = escape;
    }

    public String getEscape() {
        return escape;
    }

    public void setEscape(String escape) {
        this.escape = escape;
    }

    public static void clearConsole() { System.out.println(CLEAR_CONSOLE); }

    @Override
    public String toString() {
        return escape;
    }


}