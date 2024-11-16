package src;

import src.database.Database;
import src.helper.Helper;
import src.view.HospitalAppView;

public class HospitalApp {
    public static void main(String[] args) {
        while(true){
                Helper.clearScreen();
                printHMSTitle();
                Helper.pressAnyKeyToContinue();
                String hospitalID = "";
                HospitalAppView hospitalAppView = new HospitalAppView();
                hospitalID = hospitalAppView.userLogin();
                hospitalAppView.viewApp(hospitalID);
                Database.saveAllFiles();
                System.out.println("Thank you for using Hospital Management System");
                Helper.pressAnyKeyToContinue();
        }
    }

    private static void printHMSTitle() {
        System.out.println();
        System.out.println(
                "╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println(
                "║                          __    __    __   ______   ______    __       __                            ║");
        System.out.println(
                "║                         /  |  /  |  /  | /      \\ /      \\  /  \\     /  |                           ║");
        System.out.println(
                "║                         ▐▐ |  ▐▐ |  ▐▐ |▐▐  ▐▐▐▐▐▐▐  ▐▐▐▐▐▐  ▐▐  \\   /▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐ |__▐▐ |  ▐▐▐|▐▐ |  ▐▐ |▐▐ |__▐▐ |▐▐▐▐▐▐\\ /▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐    ▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐    ▐▐ |▐▐ |▐▐▐ |▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐▐▐▐▐▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐▐▐▐▐▐▐ |▐▐ | ▐▐ |▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐ |  ▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐ | ▐▐ |▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐ |  ▐▐ |  ▐▐ |▐▐ |__▐▐ |▐▐ |__▐▐ |▐▐ | ▐▐ |▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐/   ▐▐/   ▐▐/  ▐▐▐▐▐▐▐/ ▐▐▐▐▐▐▐/ ▐▐/  ▐▐/ ▐▐/                            ║");
        System.out.println(
                "║                                                                                                     ║");
        System.out.println(
                "║                            Welcome to Hospital Management System                                     ║");
        System.out.println(
                "║                                                                                                     ║");
        System.out.println(
                "╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }

}
