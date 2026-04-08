package com.mgr.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgr.api.dto.AccountForTokenDto;
import com.mgr.api.model.TablePrefix;
import com.mgr.api.utils.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {
    private JdbcTemplate jdbcTemplate;

    private ObjectMapper objectMapper;

    public CustomTokenEnhancer(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo;
        String grantType = authentication.getOAuth2Request().getRequestParameters().get("grant_type");
        String username = authentication.getName();
        if (SecurityConstant.GRANT_TYPE_PASSWORD.equals(grantType)) {
            additionalInfo = getAdditionalInfo(null, username, grantType, null);
        } else if (SecurityConstant.GRANT_TYPE_SELLER.equals(grantType)) {
            additionalInfo = getAdditionalInfoSeller(null, username, grantType, null);
        } else if (SecurityConstant.GRANT_TYPE_USER.equals(grantType)) {
            additionalInfo = getAdditionalInfoUser(null, username, grantType, null);
        }
        else {
            additionalInfo = getAdditionalInfoCustom(null, username, grantType, null);
        } // want other custom, define here
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

    private Map<String, Object> getAdditionalInfo(String tenantName, String username, String grantType, Long userId) {
        Map<String, Object> additionalInfo = new HashMap<>();
        AccountForTokenDto a = getAccountByUsername(username);

        if (a != null) {
            Long accountId = a.getId();
            Long storeId = -1L;
            String kind = a.getKind() + ""; //token kind
            Long deviceId = -1L; // id cua thiet bi, lưu ở table device để get firebase url..
            String permission = "<>"; //empty string
            Integer userKind = a.getKind(); // kind user là admin hay là gì
            Integer tabletKind = -1;
            Long orderId = -1L;
            Boolean isSuperAdmin = a.getIsSuperAdmin();
            String tenantId = "";
            additionalInfo.put("user_id", accountId);
            additionalInfo.put("user_kind", a.getKind());
            additionalInfo.put("grant_type", grantType == null ? SecurityConstant.GRANT_TYPE_PASSWORD : grantType);
            additionalInfo.put("tenant_info", tenantId);
            String DELIM = "|";
            String additionalInfoStr = ZipUtils.zipString(accountId + DELIM
                    + storeId + DELIM
                    + kind + DELIM
                    + permission + DELIM
                    + deviceId + DELIM
                    + userKind + DELIM
                    + username + DELIM
                    + tabletKind + DELIM
                    + orderId + DELIM
                    + isSuperAdmin + DELIM
                    + tenantId);
            additionalInfo.put("additional_info", additionalInfoStr);
        }
        return additionalInfo;
    }

    private Map<String, Object> getAdditionalInfoCustom(String tenantName, String username, String grantType, Long userId) {
        Map<String, Object> additionalInfo = new HashMap<>();
        AccountForTokenDto a = getAccountByUsername(username); // load by ...

        if (a != null) {
            Long accountId = a.getId();
            Long storeId = -1L;
            String kind = a.getKind() + ""; //token kind
            Long deviceId = -1L; // id cua thiet bi, lưu ở table device để get firebase url..
            String permission = "<>"; //empty string
            Integer userKind = a.getKind(); // kind user là admin hay là gì
            Integer tabletKind = -1;
            Long orderId = -1L;
            Boolean isSuperAdmin = a.getIsSuperAdmin();
            String tenantId = "";
            additionalInfo.put("user_id", accountId);
            additionalInfo.put("user_kind", a.getKind());
            additionalInfo.put("grant_type", grantType == null ? SecurityConstant.GRANT_TYPE_PASSWORD : grantType);
            additionalInfo.put("tenant_info", tenantId);
            String DELIM = "|";
            String additionalInfoStr = ZipUtils.zipString(accountId + DELIM
                    + storeId + DELIM
                    + kind + DELIM
                    + permission + DELIM
                    + deviceId + DELIM
                    + userKind + DELIM
                    + username + DELIM
                    + tabletKind + DELIM
                    + orderId + DELIM
                    + isSuperAdmin + DELIM
                    + tenantId);
            additionalInfo.put("additional_info", additionalInfoStr);
        }
        return additionalInfo;
    }
    private Map<String, Object> getAdditionalInfoSeller(String tenantName, String identifier, String grantType, Long userId) {
        Map<String, Object> additionalInfo = new HashMap<>();
        AccountForTokenDto a = getAccountForSeller(identifier);

        if (a != null && Integer.valueOf(2).equals(a.getKind())) {
            Long accountId = a.getId();
            Long storeId = -1L;
            String kind = a.getKind() + "";
            Long deviceId = -1L;
            String permission = "<>";
            Integer userKind = a.getKind();
            Integer tabletKind = -1;
            Long orderId = -1L;
            Boolean isSuperAdmin = a.getIsSuperAdmin();
            String tenantId = "";

            additionalInfo.put("user_id", accountId);
            additionalInfo.put("user_kind", a.getKind());
            additionalInfo.put("grant_type", grantType);

            String DELIM = "|";
            String additionalInfoStr = ZipUtils.zipString(accountId + DELIM
                    + storeId + DELIM
                    + kind + DELIM
                    + permission + DELIM
                    + deviceId + DELIM
                    + userKind + DELIM
                    + a.getUsername() + DELIM // Dùng username thật từ DB thay vì identifier
                    + tabletKind + DELIM
                    + orderId + DELIM
                    + isSuperAdmin + DELIM
                    + tenantId);
            additionalInfo.put("additional_info", additionalInfoStr);

            log.info("Seller login successful: {}", a.getUsername());
        } else {
            log.error("Seller login failed or account kind mismatch for identifier: {}", identifier);
        }
        return additionalInfo;
    }

    private Map<String, Object> getAdditionalInfoUser(String tenantName, String identifier, String grantType, Long userId) {
        Map<String, Object> additionalInfo = new HashMap<>();
        AccountForTokenDto a = getAccountForUser(identifier);

        if (a != null && Integer.valueOf(2).equals(a.getKind())) {
            Long accountId = a.getId();
            Long storeId = -1L;
            String kind = a.getKind() + "";
            Long deviceId = -1L;
            String permission = "<>";
            Integer userKind = a.getKind();
            Integer tabletKind = -1;
            Long orderId = -1L;
            Boolean isSuperAdmin = a.getIsSuperAdmin();
            String tenantId = "";

            additionalInfo.put("user_id", accountId);
            additionalInfo.put("user_kind", a.getKind());
            additionalInfo.put("grant_type", grantType);

            String DELIM = "|";
            String additionalInfoStr = ZipUtils.zipString(accountId + DELIM
                    + storeId + DELIM
                    + kind + DELIM
                    + permission + DELIM
                    + deviceId + DELIM
                    + userKind + DELIM
                    + a.getUsername() + DELIM // Dùng username thật từ DB thay vì identifier
                    + tabletKind + DELIM
                    + orderId + DELIM
                    + isSuperAdmin + DELIM
                    + tenantId);
            additionalInfo.put("additional_info", additionalInfoStr);

            log.info("Seller login successful: {}", a.getUsername());
        } else {
            log.error("Seller login failed or account kind mismatch for identifier: {}", identifier);
        }
        return additionalInfo;
    }

    public AccountForTokenDto getAccountByUsername(String username) {
        try {
            String query = "SELECT id, kind, username, email, full_name, is_super_admin " +
                    "FROM " + TablePrefix.PREFIX_TABLE + "account WHERE username = ? OR phone = ? and status = 1 limit 1";
            log.debug(query);
            List<AccountForTokenDto> dto = jdbcTemplate.query(query, new Object[]{username, username}, new BeanPropertyRowMapper<>(AccountForTokenDto.class));
            if (!dto.isEmpty()) return dto.get(0);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public AccountForTokenDto getAccountForSeller(String identifier) {
        try {
            String query = "SELECT id, kind, username, email, full_name, is_super_admin " +
                    "FROM " + TablePrefix.PREFIX_TABLE + "account " +
                    "WHERE (username = ? OR email = ? OR phone = ?) " +
                    "AND kind = 2 AND status = 1 LIMIT 1";

            log.debug("Querying seller info: {}", query);

            List<AccountForTokenDto> dto = jdbcTemplate.query(
                    query,
                    new Object[]{identifier, identifier, identifier},
                    new BeanPropertyRowMapper<>(AccountForTokenDto.class)
            );

            if (!dto.isEmpty()) return dto.get(0);
        } catch (Exception e) {
            log.error("Error fetching seller info: {}", e.getMessage());
        }
        return null;
    }
    public AccountForTokenDto getAccountForUser(String email) {
        try {
            String query = "SELECT id, kind, username, email, full_name, is_super_admin " +
                    "FROM " + TablePrefix.PREFIX_TABLE + "account WHERE email = ? and status = 1 limit 1";
            log.debug(query);
            List<AccountForTokenDto> dto = jdbcTemplate.query(query, new Object[]{email}, new BeanPropertyRowMapper<>(AccountForTokenDto.class));
            if (!dto.isEmpty()) return dto.get(0);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
