import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    private static final String SMTP_HOST = "smtp.example.com";
    private static final String SMTP_USERNAME = "tu_usuario";
    private static final String SMTP_PASSWORD = "tu_contraseña";
    private static final String EMAIL_FROM = "noreply@example.com";
    private static final String EMAIL_SUBJECT = "Confirmación de registro";
    private static final String EMAIL_BODY = "¡Gracias por registrarte! Tu registro ha sido exitoso.";

    public static void sendConfirmationEmail(String recipientEmail, String recipientName, String username) {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(EMAIL_SUBJECT);
            message.setText("Hola " + recipientName + ",\n\n" + EMAIL_BODY +
                    "\n\nTu nombre de usuario: " + username +
                    "\n\nPuedes iniciar sesión en tu cuenta aquí: [enlace de inicio de sesión]");

            Transport.send(message);
            System.out.println("Correo electrónico de confirmación enviado exitosamente a " + recipientEmail);
        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo electrónico de confirmación: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String recipientEmail = "usuario@example.com";
        String recipientName = "Nombre del Usuario";
        String username = "usuario123"; 

        sendConfirmationEmail(recipientEmail, recipientName, username);
    }
}