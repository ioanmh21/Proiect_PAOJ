package model;

public class Camera extends Equipment {
    private int resolutionMegapixels;
    private String cameraType; // DSLR, Mirrorless, Film

    public Camera(String name, double pricePerDay, String condition, String serialNumber,
                  int resolutionMegapixels, String cameraType) {
        super(name, pricePerDay, condition, serialNumber);
        this.resolutionMegapixels = resolutionMegapixels;
        this.cameraType = cameraType;
    }

    @Override
    public String getType() { return "Camera"; }

    public int getResolutionMegapixels() { return resolutionMegapixels; }
    public void setResolutionMegapixels(int resolutionMegapixels) { this.resolutionMegapixels = resolutionMegapixels; }

    public String getCameraType() { return cameraType; }
    public void setCameraType(String cameraType) { this.cameraType = cameraType; }

    @Override
    public String toString() {
        return super.toString() + " | " + resolutionMegapixels + "MP | Tip: " + cameraType;
    }
}
