package org.ontario.goldendelicious.security;

import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class StaffDetailsService implements UserDetailsService {

    private StaffRepository staffRepository;

    public StaffDetailsService(@Autowired StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        final String lowercaseName = userName.toLowerCase(Locale.ENGLISH);
        final Optional<Staff> staffOptional = staffRepository.findByUserName(lowercaseName);

        return staffOptional.map(user -> {
            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(lowercaseName,
                    user.getPassword(),
                    grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseName + " was not found in the " +
                "database"));
    }
}
