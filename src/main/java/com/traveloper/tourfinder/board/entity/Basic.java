package com.traveloper.tourfinder.board.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter

public class Basic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
