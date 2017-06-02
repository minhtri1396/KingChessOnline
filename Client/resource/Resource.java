/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.resource;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
/**
 *
 * @author InNovaTioN
 */
public class Resource
{
    protected static ResourceBundle resources;
    static
    {
        try
        {
            resources = ResourceBundle.getBundle("Client.resource.Resprop",Locale.getDefault());
        }
        catch(Exception e)
        {
            System.out.println("resource properties not found");
            JOptionPane.showMessageDialog(null, "resource properties not found", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    public String getResourceString(String key)
    {
        String str;
        try
        {
            str = resources.getString(key);
        }
        catch(Exception e)
        {
            str = null;
        }
        return str;
    }
    
    public URL getResource(String key)
    {
        String name = getResourceString(key);
        if(name != null)
        {
            URL url = this.getClass().getResource(name);
            return url;
        }
        return null;
    }
}
