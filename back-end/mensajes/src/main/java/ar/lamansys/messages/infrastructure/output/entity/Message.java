package ar.lamansys.messages.infrastructure.output.entity;

import ar.lamansys.messages.domain.MessageStoredBo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "message")
@Entity
public class Message {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "owner_user_id", nullable = false)
    private String ownerUserId;

    @Column(name = "target_user_id", nullable = false)
    private String targetUserId;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    public Message(MessageStoredBo message) {
        ownerUserId = message.getOwnerId();
        targetUserId = message.getTargetId();
        text = message.getText();
    }

}
