package com.stella.rememberall.user.domain;

import com.stella.rememberall.domain.*;
import com.stella.rememberall.tripLog.TripLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tbl")
@Entity
public class User extends BaseTimeEntity implements UserDetails {

    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    @Column(length = 100, nullable = false, unique = true, name="unique_value")
    private String uniqueValue;

    @Column(length = 300, nullable = false)
    private String password;

    @Column(length = 300, nullable = false)
    private String name;

    @Column(name="term_agree")
    private Boolean termAgree;

    @Column(name="alarm_agree")
    private Boolean alarmAgree;

    @Column(name="register_date")
    private LocalDateTime registerDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    //@JoinColumn(name="dongdong_id")
    private Dongdong dongdong;

    @OneToMany(mappedBy = "user")
    private List<TripLog> tripLogList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ItemPurchasedByUser> itemPurchasedByUserList = new ArrayList<>();

    @Builder
    public User(AuthType authType, String uniqueValue, String password, String name){
        this.authType = authType;
        this.uniqueValue = uniqueValue;
        this.password = password;
        this.name = name;
    }

    // UserDetails //
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return String.valueOf(this.id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
