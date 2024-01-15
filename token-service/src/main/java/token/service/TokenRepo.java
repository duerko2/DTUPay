package token.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenRepo {
    Map<String,String> tokenIdToAccountIdMap = new ConcurrentHashMap<>();

    public synchronized void addToken(Token token, String accountId){
        tokenIdToAccountIdMap.put(token.getRfid(),accountId);
    }
    public String getAccountId(Token token){
        return tokenIdToAccountIdMap.get(token.getRfid());
    }

    public void deleteToken(Token token) {
        tokenIdToAccountIdMap.remove(token.getRfid());

    }
}
