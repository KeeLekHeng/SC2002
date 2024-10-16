package src.model;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


//need id for each type medication?
//need include checks for users before editing medicine info? like patient cannot simply access
public class MedicationInventory implements Serializable {
    private List<Medication> medicationInventory;
}