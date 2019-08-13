package sdlpro.bookstat;

/**
 * Created by DELL on 25-09-2018.
 */


import java.io.Serializable;

/**
     * Created by DELL on 25-09-2018.
     */

    public class User implements Serializable
    {
        public String email,password,name,uid,year,clg,picture;
        String phno;

        public User(String uid,String name,String password,String email)
        {
            this.uid=uid;
            this.name=name;
            this.email=email;
            this.password=password;
            year=null;
            clg=null;
         }

        public String getName() {
            return name;
        }

        public String getPhno() {
            return phno;
        }
        public User(){}

        public  User( String name, String email,String picture)
        {
            this.name=name;
            this.email=email;
            this.picture=picture;

        }


        public void setEmail(String email)
        {
            this.email=email;
        }

        public void setPassword(String password)
        {
            this.password=password;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setClg(String clg) {
            this.clg = clg;
        }

        public void setName(String name)
        {
            this.name=name;
        }

        public String getUid() {
            return uid;
        }

        public String getEmail() {
            return email;
        }

        public void setPhno(String phno) {
            this.phno = phno;
        }
    }


