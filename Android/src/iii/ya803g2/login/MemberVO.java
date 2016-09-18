package iii.ya803g2.login;

public class MemberVO {
    private Integer memno;
    private String memid;
    private String mempw;
    private String fname;
    private String lname;
    private String idnum;
    private String gender;
    private Integer locno;
    private String addr;
    private String tel;
    private String email;
    private byte[] photo;
    private String mime;
    private Integer point;
    private Integer money;
    private Integer addays;
    private Integer status;
    private Integer type;
    private Integer withhold; 
     
    
    
    public MemberVO(String memid, byte[] photo) {
		super();
		this.memid = memid;
		this.photo = photo;
	}
	public Integer getMemno() {
        return memno;
    }
    public void setMemno(Integer memno) {
        this.memno = memno;
    }
    public String getMemid() {
        return memid;
    }
    public void setMemid(String memid) {
        this.memid = memid;
    }
    public String getMempw() {
        return mempw;
    }
    public void setMempw(String mempw) {
        this.mempw = mempw;
    }
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public String getIdnum() {
        return idnum;
    }
    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Integer getLocno() {
        return locno;
    }
    public void setLocno(Integer locno) {
        this.locno = locno;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
    public String getMime() {
        return mime;
    }
    public void setMime(String mime) {
        this.mime = mime;
    }
    public Integer getPoint() {
        return point;
    }
    public void setPoint(Integer point) {
        this.point = point;
    }
    public Integer getMoney() {
        return money;
    }
    public void setMoney(Integer money) {
        this.money = money;
    }
    public Integer getAddays() {
        return addays;
    }
    public void setAddays(Integer addays) {
        this.addays = addays;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getWithhold() {
        return withhold;
    }
    public void setWithhold(Integer withhold) {
        this.withhold = withhold;
    }
 
 
}