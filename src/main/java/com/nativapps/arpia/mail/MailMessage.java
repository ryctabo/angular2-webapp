package com.nativapps.arpia.mail;

import com.nativapps.arpia.mail.exception.DontReadTemplateException;
import com.nativapps.arpia.mail.exception.InvalidTemplateException;
import com.nativapps.arpia.model.BuilderPattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MailMessage {

    /**
     * The mail message.
     */
    private final String message;

    /**
     * Constant used to know the type of message being compiled.
     * <p>
     * The type of message to compile with this class is: {@code text/html}.
     */
    public static final String TYPE = "text/html";

    /**
     * This constructor can only be called from the builder.
     *
     * @param builder mail message builder.
     */
    private MailMessage(Builder builder) {
        this.message = builder.template;
    }

    /**
     * Get a message.
     *
     * @return string value.
     */
    public String getMessage() {
        return message;
    }

    /**
     * The <strong>Mail Message Builder</strong> is a builder class that contains
     * all methods to construct an instance of the <strong>MailMessage</strong> class.
     *
     * @see MailMessage
     */
    public static class Builder implements BuilderPattern<MailMessage> {

        private static final Logger LOG = Logger.getLogger(Builder.class.getName());

        /**
         * File name format.
         */
        private static final String FILE_NAME_FORMAT = "%s.html";

        /**
         * Text inner in the HTML template.
         */
        String template;

        /**
         * List of the sections in the body param.
         */
        private final List<Section> sections;

        /**
         * Construct a mail message builder with default template for mail.
         */
        public Builder() {
            this.sections = new ArrayList<>();
            try {
                this.uploadTemplate(String.format(FILE_NAME_FORMAT, "template"));
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, "Error loading HTML template.", ex);
                throw new DontReadTemplateException("The default template don't exists.");
            }
        }

        /**
         * Construct a mail message builder with template name without extension
         * file.
         * <p>
         * If the file name is <strong>template.html</strong> then
         * the {@code templateName} param is <strong>template</strong>.
         *
         * @param templateName template name
         */
        public Builder(String templateName) {
            this.sections = new ArrayList<>();
            try {
                this.uploadTemplate(String.format(FILE_NAME_FORMAT, templateName));
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                final String FORMAT = "Error: HTML template with name %s don't exists.";
                throw new DontReadTemplateException(String.format(FORMAT, templateName));
            }
        }

        /**
         * Load text of the HTML template in template attribute of this class.
         *
         * @param filename file name of the HTML template.
         */
        private void uploadTemplate(String filename) throws IOException {
            BufferedReader reader = null;
            try {
                InputStream in = getClass().getResourceAsStream(filename);
                reader = new BufferedReader(new InputStreamReader(in));

                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null)
                    builder.append(line);

                this.template = builder.toString();

                if (!this.template.contains("{{body}}"))
                    throw new InvalidTemplateException("The template don't "
                            + "contain param body, you need add the following "
                            + "text in the template: {{body}}");
            } finally {
                if (reader != null)
                    reader.close();
            }
        }

        /**
         * Replace a parameter in the HTML template with the given value.
         *
         * @param key   key param.
         * @param value value.
         * @return instance of mail message builder.
         */
        public Builder replace(String key, Object value) {
            LOG.log(Level.INFO, "replace: {0} -> {1}", new Object[]{key, value});
            String mKey = String.format("{{%s}}", key);

            if (!this.template.contains(mKey)) {
                final String FORMAT = "The param with name %s don't exists.";
                throw new IllegalArgumentException(String.format(FORMAT, key));
            }

            this.template = this.template.replace(mKey, value.toString());
            return this;
        }

        /**
         * Add a new section in the template body.
         *
         * @param content text.
         * @return instance of mail message builder.
         */
        public Builder addSection(String content) {
            this.sections.add(new Section(content));
            return this;
        }

        /**
         * Add a new section with CSS style in the template body.
         * <p>
         * For add styles use properties of the CSS, example:
         * <ul><li> {@code propertyA:value;...;propertyN:value }
         * </li></ul>
         *
         * @param content text of the body.
         * @param style   CSS style.
         * @return instance of mail message builder.
         */
        public Builder addSection(String content, String style) {
            this.sections.add(new Section(content, style));
            return this;
        }

        /**
         * Add a new section with {@link Section} instance.
         *
         * @param section instance.
         * @return instance of mail message builder.
         */
        public Builder addSection(Section section) {
            this.sections.add(section);
            return this;
        }

        /**
         * Add all sections in the list.
         *
         * @param sections sections array.
         * @return instance of mail message builder.
         */
        public Builder addAllSections(Section... sections) {
            this.sections.addAll(Arrays.asList(sections));
            return this;
        }

        /**
         * Create a new {@link MailMessage} object for send a mail.
         *
         * @return instance of {@link MailMessage} class.
         */
        @Override
        public MailMessage build() {
            StringBuilder bodyBuilder = new StringBuilder();
            for (Section section : sections)
                bodyBuilder.append(section.toString());

            this.replace("body", bodyBuilder.toString());

            return new MailMessage(this);
        }

    }

    /**
     * The <strong>Section</strong> class contains all methods to
     * create a new section in the body of HTML template.
     *
     * @see MailMessage
     * @see Builder
     */
    public static class Section {

        /**
         * Template for add section in the template body.
         */
        private static final String TEMPLATE_CONTENT = "<tr><td%s>%s</td></tr>"
                + "<tr height=\"16px\"></tr>";

        /**
         * Content of the section.
         */
        private String content;

        /**
         * CSS style properties.
         */
        private String style;

        /**
         * Create a {@code Section} instance with content.
         *
         * @param content text.
         */
        public Section(String content) {
            this.content = content;
        }

        /**
         * Create a {@code Section} instance with content and CSS style.
         * <p>
         * For add styles use properties of the CSS, example:
         * <ul>
         * <li> {@code propertyA:value;...;propertyN:value }</li>
         * </ul>
         *
         * @param content text
         * @param style   CSS style
         */
        public Section(String content, String style) {
            this.content = content;
            this.style = style;
        }

        /**
         * Return text HTML for add in the template body.
         *
         * @return text HTML.
         */
        @Override
        public String toString() {
            String styleHTML = style != null ? String.format(" style=\"%s\"", this.style) : "";
            return String.format(TEMPLATE_CONTENT, styleHTML, content);
        }
    }
}
