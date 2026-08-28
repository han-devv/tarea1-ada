package resources;

import java.util.Objects;

public class Par {
    private final float x;
    private final float y;

    public Par(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {return x;}
    public float getY() {return y;}

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Par par = (Par) o;
        return Float.compare(x, par.x) == 0 && Float.compare(y, par.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}
