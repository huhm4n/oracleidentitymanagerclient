package oimclient;
 
import java.util.HashMap;
import java.util.Hashtable;
import javax.security.auth.login.LoginException;
 
import oracle.iam.identity.exception.NoSuchUserException;
import oracle.iam.identity.exception.UserAlreadyExistsException;
import oracle.iam.identity.exception.UserCreateException;
import oracle.iam.identity.exception.UserDisableException;
import oracle.iam.identity.exception.UserEnableException;
import oracle.iam.identity.exception.UserLockException;
import oracle.iam.identity.exception.UserManagerException;
import oracle.iam.identity.exception.UserUnlockException;
import oracle.iam.identity.exception.ValidationFailedException;
import oracle.iam.identity.usermgmt.api.UserManager;
import oracle.iam.identity.usermgmt.vo.User;
import oracle.iam.platform.OIMClient;
 
public class OIM {
     
    UserManager userManager;
     
    public OIM() {
        super();
    }
    public static void main(String[] arg) {
         
        OIM oim=new OIM();
        oim.OIMConnection();  
        oim.createUser("sachinTen");    //comment if you are calling any other methods below
        //oim.lockUser("sachinTen");    //uncomment to lock user
        //oim.unLockUser("sachinten");  //uncomment to unlock user 
        //oim.disableUser("sachinTen");   //uncomment to disabel user
        //oim.enableUser("sachinTen");    //uncomment to enable user
        //oim.resetPassword("sachinTen");   //uncommnet to reset password
                 
    }
     
    public void OIMConnection(){             //Function to Connection to OIM
         
        Hashtable<Object, Object> env = new Hashtable<Object, Object>();
        env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL, "weblogic.jndi.WLInitialContextFactory");
        env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, "t3://oim1admin.example.com:14000");        //Update localhost with your OIM machine IP
         
        System.setProperty("java.security.auth.login.config", "E:/designconsole/config/authwl.conf");   //Update path of authwl.conf file according to your environment
        System.setProperty("OIM.AppServerType", "wls");  
        System.setProperty("APPSERVER_TYPE", "wls");
        oracle.iam.platform.OIMClient oimClient = new oracle.iam.platform.OIMClient(env);
 
         try {                        
               oimClient.login("xelsysadm", "Welcome01".toCharArray());         //Update password of Admin with your environment password
               System.out.print("Successfully Connected with OIM ");
             } catch (LoginException e) {
               System.out.print("Login Exception"+ e);
            }            
          
        userManager = oimClient.getService(UserManager.class);
    }
     
    public void createUser(String userId) {                                             //Function to create User
        HashMap<String, Object> userAttributeValueMap = new HashMap<String, Object>();
                userAttributeValueMap.put("act_key", new Long(1));
                userAttributeValueMap.put("User Login", userId);
                userAttributeValueMap.put("First Name", "Sachin");
                userAttributeValueMap.put("Last Name", "Ten");
                userAttributeValueMap.put("Email", "Sachin.Ten@abc.com");
                userAttributeValueMap.put("usr_password", "Password123");
                userAttributeValueMap.put("Role", "OTHER");
                User user = new User("Sachin", userAttributeValueMap);
        try {
            userManager.create(user);
            System.out.println("\nUser got created....");
        } catch (ValidationFailedException e) {
            e.printStackTrace();
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        } catch (UserCreateException e) {
            e.printStackTrace();
        }
    }
}