package model;

public class Projector extends Equipment {
    private int lumens;
    private String resolution;
    private String projectorType; // LCD, DLP, Laser

    public Projector(String name, double pricePerDay, String condition, String serialNumber,
                     int lumens, String resolution, String projectorType) {
        super(name, pricePerDay, condition, serialNumber);
        this.lumens = lumens;
        this.resolution = resolution;
        this.projectorType = projectorType;
    }

    @Override
    public String getType() { return "Projector"; }

    public int getLumens() { return lumens; }
    public void setLumens(int lumens) { this.lumens = lumens; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    public String getProjectorType() { return projectorType; }
    public void setProjectorType(String projectorType) { this.projectorType = projectorType; }

    @Override
    public String toString() {
        return super.toString() + " | " + lumens + " lumeni | " + resolution + " | Tip: " + projectorType;
    }
}
