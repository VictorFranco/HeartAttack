package mx.ipn.heartattack;

/**
 * Created by vfran_000 on 2018.
 */

public class User_Data {

    static String name, user, email, password;

    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        User_Data.name=name;
    }
    public static String getUser(){
        return user;
    }
    public static void  setUser(String user){
        User_Data.user=user;
    }
    public static String getEmail(){
        return email;
    }
    public static void setEmail(String email){
        User_Data.email=email;
    }
    public static String getPassword(){
        return password;
    }
    public static void setPassword(String password){
        User_Data.password=password;
    }
    public static void clear(){
        name=null;
        user=null;
        email=null;
        password=null;
    }
}
