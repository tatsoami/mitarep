package model;

public class Html {
    public static String getMessage(String message,boolean error){
        String param= ((error==true) ? "danger":"success");
        String attribute=((error==true) ? "Erreur":"Succes");
        return "<div class=\"alert alert-"+param+" alert-dismissible fade show\" role=\"alert\">\n" +
                "                    <strong>"+attribute+"</strong> "+message+"\n" +
                "            \n" +
                "                  </div>";
    }
}
