public  class  Singelton {
    private static Singelton obj;


    Singelton()
    {
          }
    public static Singelton getInstance()
    {
        if(obj==null)
        {
            obj=new Singelton();
        }
        return obj;
    }

}
