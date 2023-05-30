/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extension;

/**
 *
 * @author KHANHHERE
 */
public class LogicalProcess {
    
    public String filterString(String str_raw){
            String str = "";
            String specialChars = "`~!@#$%^&*()_+[]\\;\',./{}|:\"<>?";
            for (int i = 0; i < str_raw.length(); i++) {
                boolean check = true;
                for (int j = 0; j < specialChars.length(); j++) {
                    if (specialChars.charAt(j) == str_raw.charAt(i)) {
                        check = false;
                    }
                }
                if (check) {
                    str += str_raw.charAt(i);
                }
            }
            str = str.trim().replaceAll("\\s+", " ");
            str = str.replaceAll("\\s+", " ");
            return str;
    }
    
}
