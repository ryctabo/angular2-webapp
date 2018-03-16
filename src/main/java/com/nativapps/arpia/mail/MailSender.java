package com.nativapps.arpia.mail;

import com.nativapps.arpia.mail.exception.DontReadPropertiesException;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The <strong>Mail Sender</strong> class is for sending emails from the
 * <strong>Mail Message</strong> class.
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 * @see MailMessage
 */
public class MailSender implements Runnable {

    private static final Logger LOG = Logger.getLogger(MailSender.class.getName());

    /**
     * Name of the properties file that contains the properties necessary
     * to make the connection with the electronic mail.
     */
    private static final String PROPERTIES_FILE = "prop.properties";

    /**
     * Property file instance.
     */
    private final Properties props;

    /**
     * Transport instance.
     */
    private Transport transport;

    /**
     * Message instance.
     */
    private Message message;

    /**
     * Private constructor for a single instance of the <strong>MailSender</strong> class.
     */
    private MailSender() {
        this.props = new Properties();
        uploadPropertyFile();
    }

    /**
     * <strong>Only instance</strong> of the class.
     */
    private static MailSender instance;

    /**
     * Get the only instance of the <strong>Mail Sender</strong> class.
     * <p>
     * If the class is not instantiated then it will be instantiated, also
     * automatically set up the class to send mails.
     *
     * @return instance of the <strong>MailSender</strong> class.
     * @throws MessagingException if exists an error in the set up process.
     */
    public static synchronized MailSender getInstance() throws MessagingException {
        if (instance == null)
            instance = new MailSender();
        instance.setUp();
        return instance;
    }

    /**
     * Load the properties file for your previous use.
     * <p>
     * If the props variable is null then throws the {@code NullPointerException}.
     */
    private void uploadPropertyFile() {
        try {
            this.props.load(getClass().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error loading property file.", ex);
            throw new DontReadPropertiesException();
        }
    }

    /**
     * Configure the <strong>Mail Sender</strong> class to be able to send emails.
     *
     * @throws MessagingException If there is a configuration problem.
     */
    private void setUp() throws MessagingException {
        String email = props.getProperty("mail.smtp.user");
        String password = props.getProperty("mail.smtp.password");
        final PasswordAuthentication auth = new PasswordAuthentication(email, password);

        Session session = Session.getDefaultInstance(this.props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return auth;
            }
        });

        message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(email, "Plataforma Arpia", "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            LOG.log(Level.SEVERE, null, ex);
            String msg = "Unsupported encoding UTF-8, " + ex.getCause().getMessage();
            throw new MessagingException(msg, ex);
        }
        transport = session.getTransport();
    }

    /**
     * Set the email to which the message is to be sent.
     *
     * @param email email address.
     * @return instance of the MailSender class.
     * @throws MessagingException If there is a configuration problem.
     */
    public MailSender to(String email) throws MessagingException {
        message.setRecipient(RecipientType.TO, new InternetAddress(email));
        return instance;
    }

    /**
     * Set the subject of the message to be sent.
     *
     * @param subject value to show in the subject message.
     * @return instance of the MailSender class.
     * @throws MessagingException If there is a configuration problem.
     */
    public MailSender subject(String subject) throws MessagingException {
        message.setSubject(subject);
        return instance;
    }

    /**
     * To set the message to send.
     * <p>the {@code body} parameter can be any, but the {@code type} parameter
     * must contain certain value that depends mainly on the body parameter.
     * E.g. {@code text/html}.
     * <p>
     * Possible values for the {@code type} param:
     * <ul>
     * <li>text/html</li>
     * <li>text/plane</li>
     * </ul>
     *
     * @param body message body to send.
     * @param type type of message.
     * @return instance of the MailSender class.
     * @throws MessagingException If there is a configuration problem.
     */
    public MailSender body(String body, String type) throws MessagingException {
        message.setContent(body, type);
        return instance;
    }

    /**
     * To set the message to send from the mail message instance provided.
     *
     * @param mailMsg the mail message instance.
     * @return instance of the MailSender class
     * @throws MessagingException If there is a configuration problem.
     */
    public MailSender body(MailMessage mailMsg) throws MessagingException {
        this.message.setContent(mailMsg.getMessage(), MailMessage.TYPE);
        return instance;
    }

    /**
     * Send an mail.
     *
     * @throws MessagingException If there is a configuration problem.
     */
    public void send() throws MessagingException {
        String allRecipients = Arrays.toString(this.message.getAllRecipients());
        new Thread(this, "Sent to: " + allRecipients).start();
        LOG.log(Level.INFO, "Send mail to: {0}", allRecipients);
    }

    @Override
    public void run() {
        try {
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException ex) {
            LOG.log(Level.SEVERE, "Error sending email.", ex);
        } finally {
            try {
                if (transport != null)
                    transport.close();
            } catch (MessagingException ex) {
                LOG.log(Level.SEVERE, "Error closing transport instance.", ex);
            }
        }
    }

}
