package iii.ya803g2.casesearchpage;



import java.io.Serializable;
import java.sql.Timestamp;

public class CasesVO implements Serializable{
    public static final int STATUS_CREATED = 0;
    public static final int STATUS_PUBLIC = 1;
    public static final int STATUS_PRIVATE = 2;
    public static final int STATUS_OVER = 3;
    public static final int STATUS_COMPLETED = 4;
    public static final int STATUS_DELETED = 5;
    private static final long serialVersionUID = -178159716542933727L;
    private Integer caseno;
    private String title;
    private Integer memno;
    private Integer cpno;
    private Integer spno;
    private Integer locno;
    private Integer discount;
    private Timestamp stime;
    private Timestamp etime;
    private Integer minqty;
    private Integer maxqty;
    private Integer status;
    private Integer hits;
    private String casedesc;
    private String ship1;
    private Integer shipcost1;
    private String ship2;
    private Integer shipcost2;
    private Integer threshold;
     
    public CasesVO(){
        super();
    }
     
    public CasesVO(Integer caseno, String title, Integer memno, Integer cpno,
            Integer spno, Integer locno, Integer discount, Timestamp stime,
            Timestamp etime, Integer minqty, Integer maxqty, Integer status,
            Integer hits, String casedesc, String ship1, Integer shipcost1,
            String ship2, Integer shipcost2, Integer threshold) {
        super();
        this.caseno = caseno;
        this.title = title;
        this.memno = memno;
        this.cpno = cpno;
        this.spno = spno;
        this.locno = locno;
        this.discount = discount;
        this.stime = stime;
        this.etime = etime;
        this.minqty = minqty;
        this.maxqty = maxqty;
        this.status = status;
        this.hits = hits;
        this.casedesc = casedesc;
        this.ship1 = ship1;
        this.shipcost1 = shipcost1;
        this.ship2 = ship2;
        this.shipcost2 = shipcost2;
        this.threshold = threshold;
    }
 
    public Integer getCaseno() {
        return caseno;
    }
 
    public void setCaseno(Integer caseno) {
        this.caseno = caseno;
    }
 
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
 
    public Integer getMemno() {
        return memno;
    }
 
    public void setMemno(Integer memno) {
        this.memno = memno;
    }
 
    public Integer getCpno() {
        return cpno;
    }
 
    public void setCpno(Integer cpno) {
        this.cpno = cpno;
    }
 
    public Integer getSpno() {
        return spno;
    }
 
    public void setSpno(Integer spno) {
        this.spno = spno;
    }
 
    public Integer getLocno() {
        return locno;
    }
 
    public void setLocno(Integer locno) {
        this.locno = locno;
    }
 
    public Integer getDiscount() {
        return discount;
    }
 
    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
 
    public Timestamp getStime() {
        return stime;
    }
 
    public void setStime(Timestamp stime) {
        this.stime = stime;
    }
 
    public Timestamp getEtime() {
        return etime;
    }
 
    public void setEtime(Timestamp etime) {
        this.etime = etime;
    }
 
    public Integer getMinqty() {
        return minqty;
    }
 
    public void setMinqty(Integer minqty) {
        this.minqty = minqty;
    }
 
    public Integer getMaxqty() {
        return maxqty;
    }
 
    public void setMaxqty(Integer maxqty) {
        this.maxqty = maxqty;
    }
 
    public Integer getStatus() {
        return status;
    }
 
    public void setStatus(Integer status) {
        this.status = status;
    }
 
    public Integer getHits() {
        return hits;
    }
 
    public void setHits(Integer hits) {
        this.hits = hits;
    }
 
    public String getCasedesc() {
        return casedesc;
    }
 
    public void setCasedesc(String casedesc) {
        this.casedesc = casedesc;
    }
 
    public String getShip1() {
        return ship1;
    }
 
    public void setShip1(String ship1) {
        this.ship1 = ship1;
    }
 
    public Integer getShipcost1() {
        return shipcost1;
    }
 
    public void setShipcost1(Integer shipcost1) {
        this.shipcost1 = shipcost1;
    }
 
    public String getShip2() {
        return ship2;
    }
 
    public void setShip2(String ship2) {
        this.ship2 = ship2;
    }
 
    public Integer getShipcost2() {
        return shipcost2;
    }
 
    public void setShipcost2(Integer shipcost2) {
        this.shipcost2 = shipcost2;
    }
 
    public Integer getThreshold() {
        return threshold;
    }
 
    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }
     
    public String getFormatedStime(){
        return TimestampFormater.format(stime);
    }
    public String getFormatedEtime(){
        return TimestampFormater.format(etime);
    }
     
}