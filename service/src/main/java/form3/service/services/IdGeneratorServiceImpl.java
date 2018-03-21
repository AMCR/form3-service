package form3.service.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Id Generator Service
 */
@Service
public class IdGeneratorServiceImpl implements IdGeneratorService {
    /**
     * Generates a random and unique Id whenever it is called.
     * @return String containing an Id
     */
    @Override
    public String getNewId() {
        return UUID.randomUUID().toString();
    }
}
