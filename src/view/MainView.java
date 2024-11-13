package src.view;

public abstract class MainView {
    //abstract method to view menu
    protected abstract void printMenu();
    //abstract method to view app
    protected abstract void viewApp(String hospitalID);
    
    //constructor for MainView
    public MainView() {
        
    }

    protected void printBreadCrumbs(String breadcrumb) {
        String spaces = String.format("%" + (105 - breadcrumb.length()) + "s", "");
        System.out.println(
                "╔══════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ " + breadcrumb + spaces + "║");
        System.out.println(
                "╚══════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
}
