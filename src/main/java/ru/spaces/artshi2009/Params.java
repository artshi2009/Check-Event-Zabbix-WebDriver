package ru.spaces.artshi2009;

public class Params {
    private static int flag = 0;
    private static String loginPage = "http://loginURL";
    private static String dashboardPage = "http://etcURL";
    private static String login = "username";
    private static String password = "password";


    static String getNameButton() {
        return flag == 1 ? "Off autocheck":"On autocheck";
    }

    static int getFlag() {
        return flag;
    }

    static void setFlag(int flag) {
        Params.flag = flag;
    }

    static String getLogin() {
        return login;
    }

    static String getPassword() {
        return password;
    }

    static String getLoginPage() {
        return loginPage;
    }

    static String getDashboardPage() {
        return dashboardPage;
    }
}
