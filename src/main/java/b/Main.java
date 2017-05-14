package b;

import a.MainAspect;
import c.SequenceParser;

public class Main
{
    public static void main(String [] args)
    {
        TheEconomy s = new TheEconomy();
        Pessimist p = new Pessimist(s);
        Optimist o = new Optimist(s);
        s.attach(p);
        s.attach(o);
        s.setState("The New iPad is out today");
        p.showState();
        o.showState();
        s.setState("Hey, Its Friday!");
        p.showState();
        o.showState();
        
        new SequenceParser();
    }
}

