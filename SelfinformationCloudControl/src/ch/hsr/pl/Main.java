package ch.hsr.pl;

import static java.lang.Thread.currentThread;

import java.net.URI;
import java.net.URISyntaxException;


import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * 
 * This class launches the web application in an embedded Jetty container.
 * This is the entry point to your application. The Java command that is used for
 * launching should fire this main method.
 *
 */
public class Main {
    
	public static void main(String[] args) throws Exception
    {
        Integer serverPort = Integer.valueOf(System.getenv("PORT"));
        Server server = new Server(serverPort);

        ContextHandler context = newContextHandler("/war", "WEB-INF/web.xml");
//        ContextHandler resources = newContextHandler("/static", "static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {context});
        server.setHandler(handlers);

        server.start();
        server.join();
    }

    private static ContextHandler newContextHandler(String contextPath, String resourceBase) throws URISyntaxException {
        ContextHandler context = new ContextHandler(contextPath);
        context.setHandler(new ResourceHandler());
        context.setResourceBase(classpathResource(resourceBase).toString());
        return context;
    }

    private static URI classpathResource(String resourceBase) throws URISyntaxException {
        ClassLoader classLoader = currentThread().getContextClassLoader();
        return classLoader.getResource(resourceBase).toURI();
    }
//    /**
//     * @param args
//     */
//    public static void main(String[] args) throws Exception{
//        String webappDirLocation = "war/";
//        
//        //The port that we should run on can be set into an environment variable
//        //Look for that variable and default to 8080 if it isn't there.
//        String webPort = System.getenv("PORT");
//        if(webPort == null || webPort.isEmpty()) {
//            webPort = "8080";
//        }
//
//        Server server = new Server(Integer.valueOf(webPort));
//        WebAppContext root = new WebAppContext();
//
//        root.setContextPath("/");
//        root.setDescriptor(webappDirLocation+"/WEB-INF/web.xml");
//        root.setResourceBase(webappDirLocation);
//        
//        //Parent loader priority is a class loader setting that Jetty accepts.
//        //By default Jetty will behave like most web containers in that it will
//        //allow your application to replace non-server libraries that are part of the
//        //container. Setting parent loader priority to true changes this behavior.
//        //Read more here: http://wiki.eclipse.org/Jetty/Reference/Jetty_Classloading
//        root.setParentLoaderPriority(true);
//        
//        server.setHandler(root);
//        
//        server.start();
//        server.join();   
//    }

}
