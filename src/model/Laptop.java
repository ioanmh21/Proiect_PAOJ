package model;

public class Laptop extends Equipment {
    private int ramGb;
    private String processor;
    private String installedSoftware; // Adobe Premiere, Lightroom, DaVinci

    public Laptop(String name, double pricePerDay, String condition, String serialNumber,
                  int ramGb, String processor, String installedSoftware) {
        super(name, pricePerDay, condition, serialNumber);
        this.ramGb = ramGb;
        this.processor = processor;
        this.installedSoftware = installedSoftware;
    }

    @Override
    public String getType() { return "Laptop"; }

    public int getRamGb() { return ramGb; }
    public void setRamGb(int ramGb) { this.ramGb = ramGb; }

    public String getProcessor() { return processor; }
    public void setProcessor(String processor) { this.processor = processor; }

    public String getInstalledSoftware() { return installedSoftware; }
    public void setInstalledSoftware(String installedSoftware) { this.installedSoftware = installedSoftware; }

    @Override
    public String toString() {
        return super.toString() + " | RAM: " + ramGb + "GB | CPU: " + processor + " | Software: " + installedSoftware;
    }
}
