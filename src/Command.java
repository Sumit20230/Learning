interface ICommand{
    public
    void excute();
    void undo();
}
class Light{
    public
    void On()
    {
        System.out.println("Light is on");

    }
     void Off()
     {
         System.out.println("Light is off");
     }

}
class FAN{
    public
    void On()
    {
        System.out.println("FAN is on");
    }
    void Off()
    {
        System.out.println("FAN is off");
    }
}
class LightCommand implements ICommand{
    private Light lt;
    public
    LightCommand()
    {
       lt= new Light();
    }
    @Override
    public void excute() {
        lt.On();
    }

    @Override
    public void undo() {
        lt.Off();
    }
}
class FanCommand implements ICommand{
    private FAN fn;
    public
    FanCommand()
    {
        fn= new FAN();
    }

    @Override
    public void undo() {
        fn.Off();
    }

    @Override
    public void excute() {
        fn.On();
    }
}
class Remote{
    private
    ICommand icom;
    public  Remote(ICommand icom)
    {
       this.icom=icom;
    }
    void excute()
    {
        icom.excute();
    }
    void  undo()
    {
        icom.undo();
    }
}