package ar.lamansys.messages.infrastructure.output.repository;

import ar.lamansys.messages.domain.message.MessageStoredBo;
import ar.lamansys.messages.infrastructure.output.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT NEW ar.lamansys.messages.domain.message.MessageStoredBo(m.ownerUserId, m.targetUserId, m.text) " +
            "FROM Message m " +
            "WHERE (m.ownerUserId = :firstContact AND m.targetUserId = :secondContact) " +
            "OR (m.ownerUserId = :secondContact AND m.targetUserId = :firstContact)")
    Stream<MessageStoredBo> findAllBetweenTwoContacts(@Param("firstContact") String firstContact,
                                                      @Param("secondContact") String secondContact);

    @Transactional(readOnly = true)
    @Query("SELECT NEW ar.lamansys.messages.domain.message.MessageStoredBo(m.ownerUserId, m.targetUserId, m.text) " +
            "FROM Message m " +
            "WHERE m.ownerUserId = :userId " +
            "OR m.targetUserId = :userId")
    Stream<MessageStoredBo> findAllByUserId(@Param("userId") String userId);

}
