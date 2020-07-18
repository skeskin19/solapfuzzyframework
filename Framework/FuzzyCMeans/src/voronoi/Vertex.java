package voronoi;



public class Vertex extends Coord {

    public VList edges = null;
    public int number;

    public Vertex() {
        edges = new VList();
    }
}
