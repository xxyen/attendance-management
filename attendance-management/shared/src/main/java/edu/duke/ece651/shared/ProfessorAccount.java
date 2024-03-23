package edu.duke.ece651.shared;

public class ProfessorAccount implements Account{
  private String userid;
  private String password;
  private String profid;
  
  protected ProfessorAccount(String userid, String password, String profid) { //now just a list of accounts, not allowed to create new ones
    if(password == ""){
      password = "password";
    }
    this.userid = userid;
    this.password = hashPassword(password);
    this.profid = profid;
  }

  public ProfessorAccount(String userid, String profid) {
    this(userid, "password", profid);
  }

  public String getUserid() {
    return userid;
  }

  @Override
  public String getPersonalId() {
    return profid;
  }
  
  public String hashPassword(String password) {
    return password;
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'hashPassword'");
  }
  
  @Override
  public String getAccountType() {
    return "professor";
  }

  @Override
  public boolean isCorrectPassword(String pwd) {
    if(pwd.equals(password)) {
      return true;
    }
    return false;
  }

}
