package EchoSimulationPackage;

import java.util.HashMap;

public class ImagingRegion {
    private Long regionNum;
    private double xAngMin;
    private double yAngMin;
    private double zAngMin;
    private double xAngMax;
    private double yAngMax;
    private double zAngMax;

    public ImagingRegion(Long regionNum, double xAng, double yAng, double zAng) {
        double angTol = 34.00001;

        this.regionNum = regionNum;
        this.xAngMin = xAng - angTol;
        this.xAngMax = xAng + angTol;
        this.yAngMin = yAng - angTol;
        this.yAngMax = yAng + angTol;
        this.zAngMin = zAng - angTol;
        this.zAngMax = zAng + angTol;
        System.out.println("xAng: " + xAngMin + ", " + xAngMax);
        System.out.println("yAng: " + yAngMin + ", " + yAngMax);
        System.out.println("zAng: " + zAngMin + ", " + zAngMax);
    }

    public ImagingRegion(HashMap<String, Number> hashMap) {
        this.xAngMin = (Double) hashMap.get("xAngMin");
        this.xAngMax = (Double) hashMap.get("xAngMax");
        this.yAngMin = (Double) hashMap.get("yAngMin");
        this.yAngMax = (Double) hashMap.get("yAngMax");
        this.zAngMin = (Double) hashMap.get("zAngMin");
        this.zAngMax = (Double) hashMap.get("zAngMax");
        this.regionNum = (Long) hashMap.get("regionNum");
    }


    public double getxAngMin() {
        return xAngMin;
    }

    public double getyAngMin() {
        return yAngMin;
    }

    public double getzAngMin() {
        return zAngMin;
    }

    public double getxAngMax() {
        return xAngMax;
    }

    public double getyAngMax() {
        return yAngMax;
    }

    public double getzAngMax() {
        return zAngMax;
    }

    public double getRegionNum() {
        return regionNum;
    }
}
