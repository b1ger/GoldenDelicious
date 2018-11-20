package org.ontario.goldendelicious.services;

import org.ontario.goldendelicious.domain.Authority;

import java.util.List;

public interface AuthorityService {
    List<Authority> getAuthorityList();
    Authority getAuthorityByName();
}
