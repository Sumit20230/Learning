interface Walkable{

    public void walk();

}
interface  Talkable{
    public
    void talk();
}
class slowWalkable implements Walkable{
    @Override
    public void walk() {
        System.out.println("it is slow walking");
    }
}
class fastWalkable implements Walkable{
    @Override
    public void walk() {
        System.out.println("it is fast walkable");
    }
}
class slowTalkable implements Talkable{
    @Override
    public void talk() {
        System.out.println("it is slow talkable");
    }
}
class fastTalkable implements Talkable{
    @Override
    public void talk() {
        System.out.println("it is fast talkable");
    }
}
class client{
    public
    Talkable t;
    Walkable w;
    client(Talkable t,Walkable w)
    {
    this.t=t;
    this.w=w;
    }
   void walk()
    {
        w.walk();

    }
    void talk()
    {
        t.talk();
    }
}