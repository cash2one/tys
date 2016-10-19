package com.tys.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MEmailUtil {

	private static final String senderUser = MPropertiesUtil.getProperty("senderUser");
	private static final String senderPwd = MPropertiesUtil.getProperty("senderPwd");
	private static final String emailHost = MPropertiesUtil.getProperty("emailHost");

	/**
	 * 发送邮件
	 * @param recipient	收件人邮箱
	 * @param subject	标题
	 * @param text		文本内容
	 * @param affix		附件路径,无附件时填null
	 */
	public static void sendEx(String recipient, String subject, String text, String affix) {
		List<String> recipients = new ArrayList<String>();
		recipients.add(recipient);
		send(recipients, subject, text, affix);
	}

	/**
	 * 发送邮件，多个收件人
	 * @param recipients	收件人邮箱
	 * @param subject		标题
	 * @param text			文本内容
	 * @param affix			附件路径,无附件时填null
	 */
	public static void send(List<String> recipients, String subject, String text, String affix) {
		if (recipients == null || recipients.size() == 0) {
			return;
		}

		Properties props = new Properties();

		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", "smtp.qq.com");
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证
		props.put("mail.smtp.auth", "true");
		// google使用465或587端口
		// props.put("mail.smtp.port", "25");

		// 用刚刚设置好的props对象构建一个session
		// Session session = Session.getDefaultInstance(props);
		Session session = Session.getInstance(props, new MyAuthenticator(senderUser, senderPwd));

		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		// session.setDebug(true);

		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 加载发件人地址
			message.setFrom(new InternetAddress(senderUser));
			// 加载收件人地址
			for (String tmp : recipients) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(tmp));
			}

			// 加载标题
			message.setSubject(subject, "UTF-8");

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(text);
			multipart.addBodyPart(contentPart);

			if (affix != null) {
				// 添加附件
				BodyPart messageBodyPart = new MimeBodyPart();
				FileDataSource source = new FileDataSource(affix);

				// 添加附件的内容
				messageBodyPart.setDataHandler(new DataHandler(source));
				// 添加附件的标题
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				// messageBodyPart.setFileName("=?GBK?B?" +
				// enc.encode(source.getName().getBytes()) + "?=");
				messageBodyPart.setFileName(source.getName());
				multipart.addBodyPart(messageBodyPart);
			}

			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(emailHost, senderUser, senderPwd);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class MyAuthenticator extends Authenticator {
		String userName = "";
		String password = "";

		public MyAuthenticator() {

		}

		public MyAuthenticator(String userName, String password) {
			this.userName = userName;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(userName, password);
		}
	}

	public static void main(String[] args) {
		List<String> recipients = new ArrayList<String>();
		recipients.add("296482692@qq.com");
		MEmailUtil.send(recipients, "宝时龙服务器统计日报", "请勿回复本邮件!", "C:\\report.txt");
	}

}