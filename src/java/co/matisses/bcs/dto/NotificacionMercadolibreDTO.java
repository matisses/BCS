package co.matisses.bcs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class NotificacionMercadolibreDTO {

    private String resource;
    private Long userId;
    private String topic;
    private Long applicationId;
    private Integer attempts;
    private String sent;
    private String received;
    private List<Message> messages;

    public NotificacionMercadolibreDTO() {
        messages = new ArrayList<>();
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @JsonProperty("user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @JsonProperty("application_id")
    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "NotificacionMercadolibreDTO{" + "resource=" + resource + ", userId=" + userId + ", topic=" + topic + ", applicationId=" + applicationId + ", attempts=" + attempts + ", sent=" + sent + ", received=" + received + ", messages=" + messages + '}';
    }

    public static class Message {

        private String id;
        private String applicationId;
        private String userId;
        private String resource;
        private String topic;
        private String received;
        private String attempts;
        private String createdAt;

        @JsonProperty("_id")
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @JsonProperty("application_id")
        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }

        @JsonProperty("user_id")
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getReceived() {
            return received;
        }

        public void setReceived(String received) {
            this.received = received;
        }

        public String getAttempts() {
            return attempts;
        }

        public void setAttempts(String attempts) {
            this.attempts = attempts;
        }

        @JsonProperty("created_at")
        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        @Override
        public String toString() {
            return "Message{" + "id=" + id + ", applicationId=" + applicationId + ", userId=" + userId + ", resource=" + resource + ", topic=" + topic + ", received=" + received + ", attempts=" + attempts + ", createdAt=" + createdAt + '}';
        }

    }
}
