package com.flagsmith.flagsmith.repo;

import com.flagsmith.flagsmith.model.Reminder;
import com.flagsmith.flagsmith.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUserAndTime(User user, LocalDateTime time);
    List<Reminder> findByTime(LocalDateTime time);

}
