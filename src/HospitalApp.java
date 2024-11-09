package src;
import src.helper.*;
import src.view.*;

public class HospitalApp {
    public static void main(String[] args) {
        HospitalAppView hospitalAppView = new HospitalAppView();
        Helper.clearScreen();
        printHMSTitle();
        Helper.pressAnyKeyToContinue();
        hospitalAppView.viewApp();
    }

    private static void printHMSTitle() {
        System.out.println();
        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                          __    __    __   ______   ______    __       __                            ║");
        System.out.println("║                         /  |  /  |  /  | /      \\ /      \\  /  \\     /  |                           ║");
        System.out.println("║                         ▐▐ |  ▐▐ |  ▐▐ |▐▐  ▐▐▐▐▐▐▐  ▐▐▐▐▐▐  ▐▐  \\   /▐▐ |                           ║");
        System.out.println("║                         ▐▐ |__▐▐ |  ▐▐▐|▐▐ |  ▐▐ |▐▐ |__▐▐ |▐▐▐▐▐▐\\ /▐▐ |                           ║");
        System.out.println("║                         ▐▐    ▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐    ▐▐ |▐▐ |▐▐▐ |▐▐ |                           ║");
        System.out.println("║                         ▐▐▐▐▐▐▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐▐▐▐▐▐▐ |▐▐ | ▐▐ |▐▐ |                           ║");
        System.out.println("║                         ▐▐ |  ▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐ | ▐▐ |▐▐ |                           ║");
        System.out.println("║                         ▐▐ |  ▐▐ |  ▐▐ |▐▐ |__▐▐ |▐▐ |__▐▐ |▐▐ | ▐▐ |▐▐ |                           ║");
        System.out.println("║                         ▐▐/   ▐▐/   ▐▐/  ▐▐▐▐▐▐▐/ ▐▐▐▐▐▐▐/ ▐▐/  ▐▐/ ▐▐/                            ║");
        System.out.println("║                                                                                                     ║");
        System.out.println("║                            Welcome to Hospital Management System                                     ║");
        System.out.println("║                                                                                                     ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
    
}

