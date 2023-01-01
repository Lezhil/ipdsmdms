package com.bcits.mdas.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Component;

@Component
public class StaticProperties {
	
	/*public static String mail_smtp_smtp_host = "smtp.gmail.com";
	public static String mail_smtp_socketFactory_port1 = "465";
	public static String mail_smtp_socketFactory_class1 ="javax.net.ssl.SSLSocketFactory";
	public static String mail_smtp_auth1 ="true";
	public static String mail_smtp_port1 ="465";
	public static String Email_gateWay_username1 ="jvvnlbsmart@gmail.com";
	public static String Email_gateWay_password1 ="bcits@123";
	public static String Email_gateWay_trackComplaintConsumer1 ="www.jvvnlrms.com:8383";
	public static String Email_gateWay_trackComplaintOfficial1 ="www.jvvnlrms.com:8383/jvvnlpgrs/";
	public static String SMS_gateWay_tollFreeNumber1 ="18001806507";*/
	
	public static String mail_smtp_smtp_host = "email-smtp.us-west-2.amazonaws.com";
    public static String mail_smtp_socketFactory_port1 = "587";
    public static String mail_smtp_socketFactory_class1 ="javax.net.ssl.SSLSocketFactory";
    public static String mail_smtp_auth1 ="true";
    public static String mail_smtp_port1 ="587";
    public static String Email_gateWay_username1 ="AKIAJIEYQXDQMY6QHSIA";
    public static String Email_gateWay_password1 ="AkWyEUbO+OC754k8fo97CRCsupo0zGxiROGrQ3s08aJY";
    public static String Email_gateWay_trackComplaintConsumer1 ="www.jvvnlrms.com:8383";
    public static String Email_gateWay_trackComplaintOfficial1 ="www.jvvnlrms.com:8383/jvvnlpgrs/";
    public static String SMS_gateWay_tollFreeNumber1 ="18001806507";
    //public static String domainNameBijliPrabandh="http://172.31.20.25:7070/";
    public static String domainNameBijliPrabandh="https://bijliprabandh.com/";

	public static String hashGenerator(String userName, String senderId, String content, String secureKey) {


        StringBuffer finalString=new StringBuffer();

        finalString.append(userName.trim()).append(senderId.trim()).append(content.trim()).append(secureKey.trim());

        String hashGen=finalString.toString();

        StringBuffer sb = null;

        MessageDigest md;

        try {

            md = MessageDigest.getInstance("SHA-512");

            md.update(hashGen.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1

            sb = new StringBuffer();

            for (int i = 0; i < byteData.length; i++) {

                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

            }

 

        } catch (NoSuchAlgorithmException e) {


            e.printStackTrace();

        }

        System.out.println(sb.toString()+"===========sb.toString()");
        return sb.toString();

    }
	
	public static String MD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException 

    {

        MessageDigest md;

        md = MessageDigest.getInstance("SHA-1");

        byte[] md5 = new byte[64];

        md.update(text.getBytes("iso-8859-1"), 0, text.length());

        md5 = md.digest();

        return convertedToHex(md5);

    }

	
	public static String convertedToHex(byte[] data)

	{

		StringBuffer buf = new StringBuffer();



		for (int i = 0; i < data.length; i++)

		{

			int halfOfByte = (data[i] >>> 4) & 0x0F;

			int twoHalfBytes = 0;



			do

			{

				if ((0 <= halfOfByte) && (halfOfByte <= 9))

				{

					buf.append( (char) ('0' + halfOfByte) );

				}



				else

				{

					buf.append( (char) ('a' + (halfOfByte - 10)) );

				}



				halfOfByte = data[i] & 0x0F;



			} while(twoHalfBytes++ < 1);

		}

		return buf.toString();

	}
	
	
	
		
		
		
		
		
		
		

	
	
	
	
	
}
