package org.ontario.goldendelicious.services;

import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.domain.Request;

public interface RequestService {

    Request save(RequestCommand request);
}
