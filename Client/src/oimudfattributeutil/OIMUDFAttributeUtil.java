package oimudfattributeutil;
 
import java.util.Hashtable;
import javax.security.auth.login.LoginException;
import oracle.iam.configservice.api.ConfigManager;
import oracle.iam.configservice.api.Constants;
import oracle.iam.configservice.api.Constants.Encryption;
import oracle.iam.configservice.exception.AttributeAlreadyExistsException;
import oracle.iam.configservice.exception.AttributeCannotBeRequiredException;
import oracle.iam.configservice.exception.AttributeCreateException;
import oracle.iam.configservice.exception.CategoryDoesNotExistException;
import oracle.iam.configservice.exception.ConfigManagerException;
import oracle.iam.configservice.exception.InvalidCharacterException;
import oracle.iam.configservice.exception.NoSuchEntityException;
import oracle.iam.configservice.vo.AttributeDefinition;
import oracle.iam.platform.OIMClient;
import oracle.iam.platform.kernel.ValidationFailedException;
 
/**
 * This demonstrates how to add user defined attributes in OIM User
 * profile using the OIM APIs. The ConfigManager contains the services
 * to add and remove attributes and categories.
 */

public class OIMUDFAttributeUtil
{
    public static void main(String[] args) 
    {
        //Info required for the OIMClient
        String ctxFactory = "weblogic.jndi.WLInitialContextFactory";
        String oimServerURL = "t3://oim1admin.example.com:14000";
        String authwlConfigPath = "E:/designconsole/config/authwl.conf";
        String username = "xelsysadm";
        String password = "Welcome01";
        OIMClient oimClient = null;
 
        System.setProperty("java.security.auth.login.config", authwlConfigPath); 
        System.setProperty("OIM.AppServerType", "wls");  
        System.setProperty("APPSERVER_TYPE", "wls");
        Hashtable<String,String> env = new Hashtable<String,String>();
        env.put(OIMClient.JAVA_NAMING_FACTORY_INITIAL, ctxFactory);
        env.put(OIMClient.JAVA_NAMING_PROVIDER_URL, oimServerURL);
 
        try
        {    
            oimClient = new OIMClient(env);
            oimClient.login(username, password.toCharArray());
            ConfigManager configMgrOps = oimClient.getService(ConfigManager.class);
 
            //Minimum amount of info needed to create a string attribute type
            String attrName = "Global DN";
            String columnName = "USR_UDF_globaldn";
            String categoryName = "Other User Attributes";
            String displayType = "TEXT";
            boolean isReadOnly = false;
            Encryption encryptionType = Constants.Encryption.CLEAR;
            boolean isVisible = true;
            Integer attrSize = 100;
            boolean isSearchable = true;
            boolean isBulkUpdatable = false;
            boolean isCustomAttr = true;
            String attrBackEndType = "string";
            boolean isUserSearchable = true;
            boolean isRequired = false;
            boolean isSystemControlled = false;
 
            //Stage the attribute object to be added
            AttributeDefinition attrObj = new AttributeDefinition(attrName);
            attrObj.setBackendName(columnName);
            attrObj.setCategory(categoryName);
            attrObj.setDisplayType(displayType);
            attrObj.setReadOnly(isReadOnly);
            attrObj.setEncryption(encryptionType);
            attrObj.setVisible(isVisible);
            attrObj.setMaxSize(attrSize);
            attrObj.setSearchable(isSearchable);
            attrObj.setBulkUpdatable(isBulkUpdatable);
            attrObj.setCustomAttribute(isCustomAttr);
            attrObj.setBackendType(attrBackEndType);
            attrObj.setUserSearchable(isUserSearchable);
            attrObj.setRequired(isRequired);
            attrObj.setSystemControlled(isSystemControlled);
 
            //Add User attribute to the OIM User Profile
            configMgrOps.addAttribute(Constants.Entity.USER, attrObj);
        } 
 
        catch (NoSuchEntityException e) {e.printStackTrace();} 
        catch (InvalidCharacterException e) {e.printStackTrace();}
        catch (AttributeAlreadyExistsException e) {e.printStackTrace();}
        catch (CategoryDoesNotExistException e) {e.printStackTrace();}
        catch (AttributeCannotBeRequiredException e) {e.printStackTrace();}
        catch (ValidationFailedException e) {e.printStackTrace();}
        catch (AttributeCreateException e) {e.printStackTrace();}
        catch (ConfigManagerException e) {e.printStackTrace();}  
        catch (LoginException e) {e.printStackTrace();}     
        finally{try{oimClient.logout();} catch(Exception e) {}} 
    }
}