package iii.ya803g2.moneycenter;

public class Money_pay_list { // VO- Value Object
	private boolean isSelected = false;
	private String money;

	public Money_pay_list() {
		super();
	}

	public Money_pay_list(String money) {
		super();
		this.money = money;
	}
	
	public String getMoney() {
		return money;
	}

	public void setName(String money) {
		this.money = money;
	}

}
