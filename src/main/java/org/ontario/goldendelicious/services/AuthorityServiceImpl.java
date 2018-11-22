package org.ontario.goldendelicious.services;

import org.ontario.goldendelicious.domain.Authority;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.ontario.goldendelicious.repositories.AuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Authority getAuthorityById(Long id) {
        Optional<Authority> authorityOptional = authorityRepository.findById(id);

        if (!authorityOptional.isPresent()) {
            throw new NotFoundException("Authority Not Found. For ID value: " + id);
        }

        return authorityOptional.get();
    }

    @Override
    public Authority getAuthorityByName(String name) {
        Optional<Authority> authorityOptional = authorityRepository.findByName(name);

        if (!authorityOptional.isPresent()) {
            throw new NotFoundException("Authority Not Found. For name value: " + name);
        }

        return authorityOptional.get();
    }

    @Override
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }
}
