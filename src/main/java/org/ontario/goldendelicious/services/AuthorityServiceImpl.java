package org.ontario.goldendelicious.services;

import org.ontario.goldendelicious.domain.Authority;
import org.ontario.goldendelicious.repositories.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<Authority> getAuthorityList() {
        return authorityRepository.findAllByOrderByName();
    }

    @Override
    public Authority getAuthorityByName() {
        return null;
    }
}
