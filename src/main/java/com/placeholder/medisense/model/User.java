package com.placeholder.medisense.model;

import com.placeholder.medisense.enums.UserRole;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("USERS")
public class User implements UserDetails {

  @Id private int id;
  private String name;
  private String email;
  private String username;
  private String password;
  private UserRole role;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
    return Collections.singleton(authority);
  }

  public UserDetails toUserDetails() {
    return this;
  }
}
