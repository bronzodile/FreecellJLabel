public class Move
{
    int suite;
    int rank;
    int from;

    public Move(int s, int r, int f)
    {
        suite = s;
        rank = r;
        from = f;
    }

    public int getSuite(){
        return suite;
    }

    public int getRank(){
        return rank;
    }

    public int getFrom(){
        return from;
    }

}
