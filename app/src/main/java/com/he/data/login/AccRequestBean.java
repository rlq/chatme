package com.he.data.login;

public class AccRequestBean {

	private String version; //版本号 String Y
	private String lang ;//语言类型 String Y 请参阅语言附录
	private String userName ;//用户名称 String Y
	private String password; //密码 String Y RSA 加密 请参阅密码传输加密规则
	private String phone ;//手机号码 String
	private String email ;//邮箱 String
	private String ua ;//手机型号 String
	private String imei ;//移动设备号 String
	private String mac ;// 地址 String
	private String openudid; //设备识别号 String
	private String os ;//系统类型 String 格式 ios/android/wp/win
	private String appId ;//应用 ID String Y
	private int timestamp;
	private String sign ;
	private String newPassword ;
	private String channel ;

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getOpenudid() {
		return openudid;
	}
	public void setOpenudid(String openudid) {
		this.openudid = openudid;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public int getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String pa) {
		this.newPassword = pa;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
