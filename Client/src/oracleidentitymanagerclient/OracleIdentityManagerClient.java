package oracleidentitymanagerclient;
 
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import oracle.iam.configservice.api.ConfigManager;
import oracle.iam.configservice.api.Constants;
import oracle.iam.configservice.exception.*;
import oracle.iam.platform.OIMClient;
 
/*
 * Uses the OIMClient to access the ConfigManager services.
 * This class shows how to delete a User Defined Field in the backend.
 * The attribute is removed from SDC and USR. 
 * Also it is removed from MDS, to be specific "/file/User.xml".
 * Restart the oim server or purge the cache after executing code. 
 *
 * WARNING: USE AT YOUR OWN RISK. MAKE SURE NOTHING ELSE IS RUNNING IN OIM.
 * The method call to delete an attribute may fail to remove from the USR, SDC, or User.xml.
 */
public class OracleIdentityManagerClient 
{
    public static final String OIM_HOSTNAME = "oim1admin.example.com";
    public static final String OIM_PORT = "14000";
    public static final String OIM_PROVIDER_URL = "t3://oim1admin.example.com:14000";
    public static final String OIM_USERNAME = "xelsysadm";
    public static final String OIM_PASSWORD = "Welcome01";
    public static final String OIM_CLIENT_HOME = "E:/designconsole";
    public static final String AUTHWL_PATH = OIM_CLIENT_HOME + "/config/authwl.conf";
 
    public static void main(String[] args) 
    {
        OIMClient oimClient = null;
       
        try
        {
            //Set system properties required for OIMClient
            System.setProperty("java.security.auth.login.config", AUTHWL_PATH);
            System.setProperty("APPSERVER_TYPE", "wls"); 
            
            // Create an instance of OIMClient with OIM environment information 
            Hashtable<String,String> env = new Hashtable<String,String>();
            env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL, "weblogic.jndi.WLInitialContextFactory");
            env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, OIM_PROVIDER_URL);
            oimClient = new OIMClient(env);
 
            // Login to OIM with the approriate credentials
            oimClient.login(OIM_USERNAME, OIM_PASSWORD.toCharArray());
             
            // Access ConfigManager services
            ConfigManager configMgrOps = oimClient.getService(ConfigManager.class);
             
            // Change this value to the attribute you want to delete
            String attributeName = "PSFT_CS"; //Use attribute unique name
             
            // Deletes the SDC and USR attribute records. Removes attribute from User.xml
            configMgrOps.deleteAttribute(Constants.Entity.USER, attributeName);
        }
         
        catch (LoginException ex) {
            Logger.getLogger(OracleIdentityManagerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchEntityException ex) {
            Logger.getLogger(OracleIdentityManagerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessDeniedException ex) {
            Logger.getLogger(OracleIdentityManagerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAttributeException ex) {
            Logger.getLogger(OracleIdentityManagerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeleteSystemAttributeException ex) {
            Logger.getLogger(OracleIdentityManagerClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConfigManagerException ex) {
            Logger.getLogger(OracleIdentityManagerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        finally
        {
            // Logout user from OIMClient
            if(oimClient != null)
                oimClient.logout();
        }
    }
}