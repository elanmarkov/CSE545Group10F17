public class SecureBankingSystem {
	public static void main (String[] args) {
		SendOTPByMail test = new SendOTPByMail();
		System.out.println(test.sendOTPReturnHexVal("emarkov@asu.edu"));
	}
}
