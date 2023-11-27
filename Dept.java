public class Dept {
    String name;
    int id;

    public Dept() {
    }
    public Dept(int i, String na)
    {
        name=na;
        id= i;
    }

    public Dept(Dept d) {
        this.name = d.name;
        this.id = d.id;
    }
}

