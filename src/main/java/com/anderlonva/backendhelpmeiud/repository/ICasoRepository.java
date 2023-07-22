package com.anderlonva.backendhelpmeiud.repository;

import com.anderlonva.backendhelpmeiud.model.Caso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ICasoRepository extends JpaRepository<Caso, Long> {

    @Query("UPDATE Caso c SET c.esVisible=?1 WHERE id=?2")
    Boolean setVisible(Boolean visible, Long id);

}
