interface IAdapter{
    public
    void convertTextToXML(String temp);
}
class XML{
    public String JsonToXMl(String text)
    {
        return "<h1>"+text+"<h1/>";
    }
}
class Adapter implements IAdapter{
    private XML xml;
    public
    Adapter()
    {
        xml= new XML();
    }
    @Override
    public void convertTextToXML(String temp) {
        System.out.println(xml.JsonToXMl(temp)+"vscfcsx");

    }
}
class clients{
    private
    IAdapter adapt;
    public
    clients(IAdapter adapt)
    {
        this.adapt=adapt;
    }
    public

    void  convert(String temp)
    {
        adapt.convertTextToXML(temp);
    }
}

