package io.graxon.core.system.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class AccountConverter implements Converter<String, AccountConverter.Account> {

    /**
     *
     */
    private static final Logger log = LoggerFactory.getLogger(AccountConverter.class);

    /**
     *
     * @param json
     * @return
     */
    @Override
    public Account convert(String json) {
        try {
            return new ObjectMapper().readValue(json, Account.class);
        } catch (JsonProcessingException e) {
            log.warn("Failed to convert JSON to Account: {}", e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param id
     * @param name
     * @param email
     */
    public record Account
        (String id, String name, String email) {
    }
}
