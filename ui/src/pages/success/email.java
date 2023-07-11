import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    public static void main(String[] args) {
        final String username = "tu_correo@dominio.com";
        final String password = "tu_contraseña";
        final String recipientEmail = "correo_destino@dominio.com";
        final String subject = "¡Valida tu cuenta para comenzar a vivir la aventura con Digital Booking!";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.dominio.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            String htmlContent = "<html>"
                + "<body>"
                + "<h1>¡Validemos la cuenta!</h1>"
                + "<p>Aprovecha al máximo tus próximas vacaciones.</p>"
                + "<p>Explora nuestros emocionantes planes turísticos y reserva ahora mismo.</p>"
                + "<a href=\"https://localhost:8000/register-success\">Haz clic aquí para validar tu cuenta</a>"
                + "<p>Si el enlace no funciona, copia y pega la siguiente URL en tu navegador:</p>"
                + "<p>https://localhost:8000/register-success</p>"
                + "<p>¡Prepárate para vivir la aventura y descubrir nuevos horizontes!</p>"
                + "<p>Saludos aventureros,</p>"
                + "<p>El equipo de Digital Booking - Vive la Aventura</p>"
                + "</body>"
                + "</html>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("El correo electrónico ha sido enviado con éxito.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}