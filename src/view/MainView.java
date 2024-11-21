package src.view;

/**
 * The abstract class MainView represents the base structure for all view classes in the hospital application.
 * It provides abstract methods for printing menus and viewing the app, which should be implemented by subclasses.
 * @author Seann
 * @version 1.0
 * @since 2024-11-20
 */
public abstract class MainView {
    /**
     * Abstract method to print the menu specific to the view.
     */
    protected abstract void printMenu();

    /**
     * Abstract method to display the application based on the provided hospital ID.
     * @param hospitalID The ID of the hospital for which the app should be displayed.
     */
    protected abstract void viewApp(String hospitalID);

    /**
     * Constructor for MainView.
     */
    public MainView() {

    }

    /**
     * Prints the breadcrumb navigation at the top of the screen for the current view.
     * @param breadcrumb The breadcrumb string to display.
     */
    protected void printBreadCrumbs(String breadcrumb) {
        String spaces = String.format("%" + (105 - breadcrumb.length()) + "s", "");
        System.out.println(
                "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + breadcrumb + spaces + "║");
        System.out.println(
                "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
}