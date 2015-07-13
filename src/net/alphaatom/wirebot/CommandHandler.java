package net.alphaatom.wirebot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CommandHandler {

  private final int commandDelay = 5000;
  private List<Class<Command>> commandClasses = new ArrayList<Class<Command>>();
  private long lastCommand = 0;
  private WireBot wirebot;

  public CommandHandler(WireBot bot) {
    List<Class> classes = null;
    try {
      classes = getClasses(ClassLoader.getSystemClassLoader(), "net/alphaatom/wirebot/commands");
    } catch (Exception e) {
      wirebot.wireLog("Failed to find command classes. Exiting.", WireBot.ERROR);
      e.printStackTrace();
    }
    for (Class c : classes) {
      if (Command.class.isAssignableFrom(c)) {
        System.out.println("Adding " + c.getSimpleName() + " to command classes.");
        commandClasses.add((Class<Command>) c);
      }
    }
    lastCommand = System.currentTimeMillis();
    wirebot = bot;
  }

  public void processCommand(String cmd, Map<String, String> cmdinfo, String[] args, WireBot wb) {
    if (lastCommand+commandDelay > System.currentTimeMillis()) {
      return;
    }
    for (Class<Command> commandClass : commandClasses) {
      try {
        Command cmdExecutor = commandClass.newInstance();
        if (Arrays.asList(cmdExecutor.getAliases()).contains(cmd)) {
          cmdExecutor.exec(cmdinfo, args, wb);
          lastCommand = System.currentTimeMillis();
          return;
        }
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  public static List<Class> getClasses(ClassLoader cl,String pack) throws Exception{

        String dottedPackage = pack.replaceAll("[/]", ".");
        List<Class> classes = new ArrayList<Class>();
        URL upackage = cl.getResource(pack);

        BufferedReader dis = new BufferedReader(new InputStreamReader((InputStream) upackage.getContent()));
        String line = null;
        while ((line = dis.readLine()) != null) {
            if(line.endsWith(".class")) {
               classes.add(Class.forName(dottedPackage+"."+line.substring(0,line.lastIndexOf('.'))));
            }
        }
        return classes;
    }

}
