package oimclient;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import oracle.iam.platform.OIMClient;

import oracle.iam.configservice.api.ConfigManager;
import oracle.iam.configservice.api.Constants.Entity;
import oracle.iam.configservice.exception.AccessDeniedException;
import oracle.iam.configservice.exception.ConfigManagerException;
import oracle.iam.configservice.exception.NoSuchEntityException;
import oracle.iam.configservice.vo.AttributeDefinition;
import javax.security.auth.login.LoginException;

public class GetFields {
    private static OIMClient oimClient;
        Map<String, AttributeDefinition> attrMap;
        public static void main(String[] arg) {
                 
                GetFields orgmgmt=new GetFields();                               
        try {
            orgmgmt.init();
            
        } catch (LoginException e) {
            System.out.println(e);
        }
           orgmgmt.getAllUdf();               
            }

    public void init() throws LoginException {
           System.out.println("Creating client....");
           String ctxFactory = "weblogic.jndi.WLInitialContextFactory";
           String serverURL = "t3://oim1admin.example.com:14000"; 
           String username = "xelsysadm";
           String password = "Welcome01";
           Hashtable env;
           env = new Hashtable();
            
            env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL,ctxFactory);
            env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, serverURL);
            System.setProperty("java.security.auth.login.config","E:/designconsole/config/authwl.conf");
            System.setProperty("APPSERVER_TYPE","wls");
            oimClient = new OIMClient(env);
            System.out.println("Logging in");               
            oimClient.login(username, password.toCharArray());
            System.out.println("Log in successful");
        }
 
 public void getAllUdf(){
        ConfigManager cfgMgr = oimClient.getService(ConfigManager.class);               

        try {
            attrMap = cfgMgr.getAttributes(Entity.USER);
        } catch (AccessDeniedException e) {
        } catch (NoSuchEntityException e) {
        } catch (ConfigManagerException e) {
        }
        Iterator<String> itr = attrMap.keySet().iterator();
                while (itr.hasNext()) {
                    String attributes = itr.next();
                    
                    System.out.println("Attributes are"+attributes);
                }
    }
    
}