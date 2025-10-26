class Power{
    public
    void start()
    {
        System.out.println("system is on");

    }
}
class BIOS{
    public
    void start()
    {
        System.out.println("bios on");

    }
}
class CPU{
    public
    void start()
    {
        System.out.println("CPU on");
    }
}
class Client{
    private Power power;
    private BIOS bios;
    private CPU cpu;
    public
    Client()
    {
        power= new Power();
        bios= new BIOS();
        cpu= new CPU();
    }
    void execute()
    {
        cpu.start();
        bios.start();
        power.start();

    }
}