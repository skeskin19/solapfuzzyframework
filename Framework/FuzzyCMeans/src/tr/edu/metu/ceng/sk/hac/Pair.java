package tr.edu.metu.ceng.sk.hac;

public class Pair<P, Q>
{
    private P p;
    private Q q;

    public Pair(P p, Q q) {
        this.p = p;
        this.q = q;
    }

    public P getP() {
        return p;
    }

    public Q getQ() {
        return q;
    }
}
