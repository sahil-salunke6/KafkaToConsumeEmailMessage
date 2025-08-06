package org.example.kafka;

import java.util.List;
import java.util.Map;

public class EmailNotification {
    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String body;
    private String templateName;
    private List<Attachment> attachment;
    private String priority;
    private String templateId;
    private Map<String, String> placeholders;

    public List<String> getTo() { return to; }
    public void setTo(List<String> to) { this.to = to; }

    public List<String> getCc() { return cc; }
    public void setCc(List<String> cc) { this.cc = cc; }

    public List<String> getBcc() { return bcc; }
    public void setBcc(List<String> bcc) { this.bcc = bcc; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }

    public List<Attachment> getAttachment() { return attachment; }
    public void setAttachment(List<Attachment> attachment) { this.attachment = attachment; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getTemplateId() { return templateId; }
    public void setTemplateId(String templateId) { this.templateId = templateId; }

    public Map<String, String> getPlaceholders() { return placeholders; }
    public void setPlaceholders(Map<String, String> placeholders) { this.placeholders = placeholders; }

    public static class Attachment {
        private String fileName;
        private String fileType;
        private String fileContent;

        public String getFileName() { return fileName; }
        public void setFileName(String fileName) { this.fileName = fileName; }

        public String getFileType() { return fileType; }
        public void setFileType(String fileType) { this.fileType = fileType; }

        public String getFileContent() { return fileContent; }
        public void setFileContent(String fileContent) { this.fileContent = fileContent; }
    }
}