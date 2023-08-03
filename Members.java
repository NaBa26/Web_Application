package com.java.webapplication;

public class Members {
                    private String uname,password,email,rpassword;

					public Members(String uname, String password, String rpassword, String email) {
						super();
						this.uname = uname;
						this.password = password;
						this.email = email;
						this.rpassword = rpassword;
					}

					public Members() {
						super();
						// TODO Auto-generated constructor stub
					}

					public String getUname() {
						// TODO Auto-generated method stub
						return uname;
					}

					public String getPassword() {
						return password;
					}

					public String getEmail() {
						return email;
					}
					
					public String getrPassword() {
						return rpassword;
					}
                       
}
