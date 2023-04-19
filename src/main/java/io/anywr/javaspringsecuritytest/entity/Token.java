package io.anywr.javaspringsecuritytest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "token")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Token extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean expired;
    private boolean revoked;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private  TokenType tokenType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
