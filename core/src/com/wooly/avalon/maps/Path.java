package com.wooly.avalon.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Path {
    // These define the turningpoints in the path that enemies take.
    Coordinate[] coordinates;
    private int length;

    public Path(Coordinate[] coordinates) {
        this.coordinates = coordinates;
        enrichSelf(10); //we add extra points
        this.length=this.coordinates.length;
        //System.out.println("PATH IS:");
        //for (int i = 0; i < length; i++) {
        //    System.out.println(this.coordinates[i]);
        //}
    }

    /**
     * Returns the index of the coordinate from the path which is closest to the given goal coordinate.
     */
    public int closestTo(Coordinate goal){
        int closest=0;
        float distance=coordinates[0].distanceFrom(goal);
        for (int i = 1; i < length; i++) {
            float nextDist=coordinates[i].distanceFrom(goal);
            if(nextDist < distance){
                distance=nextDist;
                closest=i;
            }
        }
        return closest;
    }

    /**
     * Based on the original turningpoints adds a lot of extra points to help Hero movement.
     */
    private void enrichSelf(int enrichmentDistance){
        List<Coordinate> enriched=new ArrayList<>();
        for (int i = 0; i < coordinates.length-1; i++) {
            enriched.add(coordinates[i]);
            Coordinate vector= coordinates[i+1].subtract(coordinates[i]).normalize().multiplyByScalar(enrichmentDistance);
            Coordinate addition=coordinates[i].add(vector);
            while(addition.distanceFrom(coordinates[i+1])>enrichmentDistance){
                enriched.add(addition.round());
                addition=addition.add(vector);
            }
        }
        enriched.add(coordinates[coordinates.length-1]);
        coordinates=enriched.toArray(new Coordinate[0]);
    }
    public Coordinate getCoordinate(int position) {
        return coordinates[position];
    }
    public Coordinate[] getCoordinates() {
        return coordinates;
    }
    public int getLength() {
        return length;
    }
    @Override
    public String toString() {
        return "Path{" +
                "coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
